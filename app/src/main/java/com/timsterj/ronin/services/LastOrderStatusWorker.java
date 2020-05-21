package com.timsterj.ronin.services;

import android.app.Notification;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.timsterj.ronin.App;
import com.timsterj.ronin.R;
import com.timsterj.ronin.activities.HomeActivity;
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

public class LastOrderStatusWorker extends Worker {

    private CompositeDisposable disposableBag = new CompositeDisposable();
    private OrderDoneDao orderDoneDao;
    private UserDao userDao;
    private User mUser;
    private NotificationManagerCompat notificationManager;

    @Inject
    Retrofit retrofit;

    public LastOrderStatusWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        init();

        startCheckStatus();

        return Result.success();
    }

    private void init(){
        App.getINSTANCE().getAppComponent().inject(this);
        orderDoneDao = App.getINSTANCE()
                .getAppDatabase()
                .getOrderDoneDao();
        userDao = App.getINSTANCE()
                .getAppDatabase()
                .getUserDao();

        disposableBag.add(
                userDao.getUser()
                        .subscribe(user -> {
                            mUser = user;
                        })
        );

        notificationManager = NotificationManagerCompat.from(getApplicationContext());

    }

    private void startCheckStatus() {

        disposableBag.add(
                orderDoneDao.getAllOrders()
                        .delay(200, TimeUnit.MILLISECONDS)
                        .subscribe(value -> {
                            if (checkLastOrderPrepared()) {
                                onStopped();

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
                        .lastElement()
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
                            updateOrderDaoDB(value.getStatus(), orderDone.getStatus(), orderDone.getOrder_id(), orderDone.getPrice(), orderDone.isNotified());

                        }, t -> {
                            Log.d(Contracts.TAG, "error: " + t.getMessage());
                        })
        );


    }

    private void updateOrderDaoDB(String status, String preStatus, String id, int price, boolean isNotified) {

        if (preStatus.equals(status)) {
            if (!isNotified) {
                disposableBag.add(
                        orderDoneDao.updateOrderStatus(status, id, price, true)
                                .observeOn(AndroidSchedulers.mainThread())
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
                            .observeOn(AndroidSchedulers.mainThread())
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

        if (orderDones == null) {
            return true;
        }
        if (!orderDones.isEmpty()) {
            orderDone = orderDones.get(orderDones.size() - 1);
        }
        if (orderDone == null) {
            return true;
        }

        if (orderDone.getStatus() == null) {
            return true;
        }

        return orderDone.getStatus().equals("Выполнен") && orderDone.getStatus().equals("Списан");
    }

    private void sendOnOrderStatusChannel(String id, String status) {
        Log.d(Contracts.TAG, "doWork: notification: " + status);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), Contracts.ORDER_STATUS_CHANNEL_ID)
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


    @Override
    public void onStopped() {
        super.onStopped();
        disposableBag.clear();
    }
}
