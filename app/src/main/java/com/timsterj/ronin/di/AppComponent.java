package com.timsterj.ronin.di;


import android.content.Context;
import android.content.SharedPreferences;

import com.timsterj.ronin.activities.AuthorizationActivity;
import com.timsterj.ronin.di.modules.AdapterModule;
import com.timsterj.ronin.di.modules.AppModule;
import com.timsterj.ronin.di.modules.LocalNavigationModule;
import com.timsterj.ronin.di.modules.NavigationModule;
import com.timsterj.ronin.di.modules.RepositoryModule;
import com.timsterj.ronin.di.modules.RetrofitModule;
import com.timsterj.ronin.fragments.AuthorizationFragment;
import com.timsterj.ronin.fragments.SignUpFragment;
import com.timsterj.ronin.presenters.AuthorizationPresenter;
import com.timsterj.ronin.services.OrderStatusService;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class,
                NavigationModule.class,
                AdapterModule.class,
                RetrofitModule.class,
                RepositoryModule.class
        }
)
public interface AppComponent {

    //Service
    void inject(OrderStatusService orderStatusService);

    //Activity
    void inject(AuthorizationActivity authorizationActivity);

    //Fragment
    void inject(AuthorizationFragment authorizationFragment);
    void inject(SignUpFragment signUpFragment);

    //Presenter
    void inject(AuthorizationPresenter authorizationPresenter);

    // Activity scope
    LaunchComponent plusLaunchComponent();

    HomeComponent plusHomeComponent(LocalNavigationModule localNavigationModule);

    // Fragment scope

    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder sharedPreferences(SharedPreferences sharedPreferences);

        @BindsInstance
        Builder context(Context context);
    }
}
