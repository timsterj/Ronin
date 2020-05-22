package com.timsterj.ronin;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.multidex.MultiDex;

import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.data.local.AppDatabase;
import com.timsterj.ronin.di.AppComponent;
import com.timsterj.ronin.di.DaggerAppComponent;

public class App extends Application {

    // TODO Применить WorkManager вместо Foreground Service.

    private static App INSTANCE;

    public static final App getINSTANCE() {
        return INSTANCE;
    }

    public AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        appComponent = DaggerAppComponent
                .builder()
                .context(getApplicationContext())
                .sharedPreferences(getSharedPreferences(Contracts.APP_PREFERENCES, Context.MODE_PRIVATE))
                .build();

        createNotificationChannel();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "order_status_channel";

            NotificationChannel channel = new NotificationChannel(
                    Contracts.ORDER_STATUS_CHANNEL_ID,
                    name,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            channel.setImportance(NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }

    }


    public AppComponent getAppComponent() {
        return appComponent;
    }

    public AppDatabase getAppDatabase() {
        return AppDatabase.getInstance(getApplicationContext());
    }

    public void setComponentForTest(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

}
