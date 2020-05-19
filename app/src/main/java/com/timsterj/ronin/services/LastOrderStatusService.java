package com.timsterj.ronin.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.ServiceCompat;

import com.timsterj.ronin.App;
import com.timsterj.ronin.R;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.data.local.DAO.OrderDoneDao;
import com.timsterj.ronin.data.local.DAO.UserDao;
import com.timsterj.ronin.data.model.Order;
import com.timsterj.ronin.data.model.OrderDone;
import com.timsterj.ronin.data.model.User;
import com.timsterj.ronin.domain.api.FrontPadApi;

import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LastOrderStatusService extends Service {

    private CompositeDisposable disposableBag = new CompositeDisposable();
    private OrderDoneDao orderDoneDao;
    private UserDao userDao;
    private User mUser;
    private NotificationManagerCompat notificationManager;

    @Inject
    Retrofit retrofit;

    public LastOrderStatusService() {
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

        disposableBag.add(
                userDao.getUser()
                        .subscribeOn(Schedulers.io())
                        .subscribe(user -> {
                            mUser = user;
                        })
        );

        notificationManager = NotificationManagerCompat.from(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        disposableBag.add(
                Observable.interval(20, TimeUnit.SECONDS)
                        .subscribe(value -> {
                            startCheckStatus();

                        })

        );

        return START_NOT_STICKY;
    }

    private void startCheckStatus() {

        disposableBag.add(
                orderDoneDao.getAllOrders()
                        .subscribeOn(Schedulers.io())
                        .subscribe(value -> {
                            if (checkLastOrderPrepared()) {
                                stopSelf();
                            } else {
                                getStatus(value);
                            }

                            Session.getINSTANCE().getOrderDoneList().onNext(value);

                        })
        );


    }

    private void getStatus(List<OrderDone> orderDones) {
        disposableBag.add(
                Observable.fromIterable(orderDones)
                        .take(orderDones.size())
                        .subscribe(value -> {
                            doGetStatusResponse(value);

                        })

        );

    }


    private void doGetStatusResponse(OrderDone orderDone) {

        disposableBag.add(
                getFrontPadApi().getStatus(
                        Contracts.RetrofitConstant.FRONT_PAD_SECRET,
                        orderDone.getOrder_id(),
                        mUser.getPhoneNumber()
                )
                        .subscribe(value -> {
                            updateOrderDaoDB(value.getStatus(), orderDone.getStatus(), orderDone.getOrder_id(),orderDone.getPrice(), orderDone.isNotified());

                        }, t -> {
                            Log.d(Contracts.TAG, "error: " + t.getMessage());
                        })
        );


    }

    private void updateOrderDaoDB(String status, String preStatus, String id, int price, boolean isNotified) {

        if (preStatus.equals(status)) {
            if (!isNotified) {
                disposableBag.add(
                        orderDoneDao.updateOrderStatus(status, id, price,true)
                                .subscribe(() -> {
                                    sendOnOrderStatusChannel(id, status);

                                }, t -> {
                                    Log.d(Contracts.TAG, "error: " + t.getMessage());
                                })
                );
            }

        } else {
            disposableBag.add(
                    orderDoneDao.updateOrderStatus(status, id, price, false)
                            .subscribe(() -> {

                            }, t -> {
                                Log.d(Contracts.TAG, "error: " + t.getMessage());
                            })
            );

        }


    }

    private boolean checkLastOrderPrepared() {
        List<OrderDone> orderDones = Session.getINSTANCE().getOrderDoneList().getValue();
        OrderDone orderDone = null;

        if (!orderDones.isEmpty()) {
            orderDone = orderDones.get(orderDones.size() - 1);
        }

        if (orderDone == null) {
            return true;
        }

        if (!orderDone.getStatus().equals("Выполнен") || !orderDone.getStatus().equals("Списан")) {
            return false;
        }

        return true;
    }

    private void sendOnOrderStatusChannel(String id, String status) {
        Notification notification = new NotificationCompat.Builder(this, Contracts.ORDER_STATUS_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_favorite_red_24dp)
                .setContentTitle("Последний заказ: №" + id)
                .setContentText("Статус: " + status)
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
