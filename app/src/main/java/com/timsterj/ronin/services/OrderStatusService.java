package com.timsterj.ronin.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.timsterj.ronin.App;
import com.timsterj.ronin.R;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.data.local.DAO.OrderDoneDao;
import com.timsterj.ronin.data.local.DAO.UserDao;
import com.timsterj.ronin.data.model.OrderDone;
import com.timsterj.ronin.data.model.User;
import com.timsterj.ronin.domain.api.FrontPadApi;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class OrderStatusService extends Service {

    private CompositeDisposable disposableBag = new CompositeDisposable();
    private OrderDoneDao orderDoneDao;
    private UserDao userDao;
    private User mUser;
    private NotificationManagerCompat notificationManager;

    @Inject
    Retrofit retrofit;

    public OrderStatusService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.getINSTANCE().getAppComponent().inject(this);
        orderDoneDao = App.getINSTANCE()
                .getAppDatabase()
                .getOrderDoneDao();
        userDao = App.getINSTANCE()
                .getAppDatabase()
                .getUserDao();

        Toast.makeText(this, "OnCreate Сервиса", Toast.LENGTH_SHORT).show();

        notificationManager = NotificationManagerCompat.from(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init();

        disposableBag.add(
                Observable.interval(20, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(time -> {
                            Toast.makeText(this, "5 сек. ", Toast.LENGTH_SHORT).show();
                            startCheckStatus();
                        })
        );

        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        disposableBag.add(
                userDao.getUser()
                        .subscribeOn(Schedulers.io())
                        .subscribe(user -> {
                            mUser = user;
                        })
        );

    }

    private void startCheckStatus() {
        disposableBag.add(
                orderDoneDao.getAllOrders()
                        .subscribeOn(Schedulers.io())
                        .subscribe(value -> {
                            if (checkAllDone(value)) {
                                stopSelf();
                            } else {
                                getStatus(value);
                                Session.getINSTANCE().getOrderDoneList().onNext(value);
                            }

                        })
        );

    }

    private void getStatus(List<OrderDone> orderDones) {

        for (OrderDone orderDone : orderDones) {
            Log.d(Contracts.TAG, "clear_order_status: " + orderDone.getStatus());

            if (!orderDone.getStatus().equals("Выполнен")) {
                doGetStatusResponse(orderDone);
            }

        }
    }


    private void doGetStatusResponse(OrderDone orderDone) {

        disposableBag.add(
                getFrontPadApi().getStatus(
                        Contracts.RetrofitConstant.FRONT_PAD_SECRET,
                        orderDone.getOrder_id(),
                        mUser.getPhoneNumber()
                )
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(value -> {
                            orderDone.setStatus(value.getStatus());

                            Log.d(Contracts.TAG, "response_orderId: " + orderDone.getOrder_id() + " value: " + value.getStatus() + " order_number: " + orderDone.getOrder_number());
                            updateOrderDaoDB(orderDone.getOrder_id(), value.getStatus());
                        }, t->{
                            Log.d(Contracts.TAG, "error: " + t.getMessage());
                        })
        );



    }

    private void updateOrderDaoDB(String status, String id) {
        Log.d(Contracts.TAG, "response_orderId: " + id + " value: " + status);
        disposableBag.add(
                orderDoneDao.updateOrderStatus(status, id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            sendOnOrderStatusChannel(status, id);
                        }, t->{
                            Log.d(Contracts.TAG, "error: " + t.getMessage());
                        })
        );
    }

    private boolean checkAllDone(List<OrderDone> orderDones) {
        boolean allDone = true;

        for (OrderDone orderDone : orderDones) {
            if (!orderDone.getStatus().equals("Выполнен") || !orderDone.getStatus().equals("Списан")) {
                allDone = false;
            }
        }

        return allDone;
    }

    private void sendOnOrderStatusChannel(String id, String status){
        Notification notification = new NotificationCompat.Builder(this, Contracts.ORDER_STATUS_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_favorite_red_24dp)
                .setContentTitle("Заказ номер: " + id)
                .setContentText("Статус заказа: " + status)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(Integer.parseInt(id), notification);


    }


    private FrontPadApi getFrontPadApi() {
        return retrofit.create(FrontPadApi.class);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableBag.clear();
    }
}
