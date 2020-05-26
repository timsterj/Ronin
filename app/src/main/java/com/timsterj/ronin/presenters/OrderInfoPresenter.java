package com.timsterj.ronin.presenters;

import android.content.SharedPreferences;
import android.util.Log;

import com.timsterj.ronin.App;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.OrderInfoContract;
import com.timsterj.ronin.data.local.DAO.FavoriteDao;
import com.timsterj.ronin.data.local.DAO.OrderDao;
import com.timsterj.ronin.data.local.DAO.OrderDoneDao;
import com.timsterj.ronin.data.model.Favorite;
import com.timsterj.ronin.data.model.Order;
import com.timsterj.ronin.data.model.OrderDone;
import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.data.model.User;
import com.timsterj.ronin.domain.api.FrontPadApi;
import com.timsterj.ronin.helpers.InjectHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.MvpPresenter;
import retrofit2.Retrofit;

public class OrderInfoPresenter extends MvpPresenter<OrderInfoContract.View> implements OrderInfoContract.Presenter {

    private CompositeDisposable disposableBag = new CompositeDisposable();
    private List<OrderDone> orderDones = new ArrayList<>();
    private User mUser;
    private OrderDoneDao orderDoneDao;
    private OrderDao mOrderDao;
    private FavoriteDao mFavoriteDao;

    @Inject
    Retrofit retrofit;
    @Inject
    SharedPreferences sharedPreferences;
    private int index = 0;

    public void init() {
        InjectHelper.plusHomeComponent().inject(this);

        disposableBag.add(
                Session.getINSTANCE().getOrderDoneList()
                        .subscribe(value -> {
                            orderDones = value;
                        })
        );
        mUser = Session.getINSTANCE().getUser().getValue();

        orderDoneDao = App.getINSTANCE()
                .getAppDatabase()
                .getOrderDoneDao();
        mFavoriteDao = App.getINSTANCE()
                .getAppDatabase()
                .getFavoriteDao();
        mOrderDao = App.getINSTANCE()
                .getAppDatabase()
                .getOrderDao();

    }

    private FrontPadApi getFrontPadApi() {
        return retrofit.create(FrontPadApi.class);
    }

    @Override
    public void initOrderDone(int index) {
        this.index = index;

        StringBuilder orderlist = new StringBuilder();
        OrderDone orderDone = orderDones.get(index);

        int price = 0;
        for (Product product : orderDone.getOrderList()) {
            price = price + (product.getCount() * product.getPrice());

            orderlist.append(product.getName())
                    .append(" ( " + product.getPrice() + " руб )")
                    .append(" x " + product.getCount() + " шт.")
                    .append("\n");
        }

        Log.d(Contracts.TAG, "index: " + index + " size: " + orderDones.size());
        if (index == orderDones.size()-1) {
            getStatus(orderDone, orderlist, price, orderDone.getLocation());

        } else {
            updateOrder(orderDone, orderlist, price, orderDone.getLocation());

        }

    }

    private void updateOrder(OrderDone orderDone, StringBuilder orderList, int price, String location) {
        List<OrderDone> orderDones = Session.getINSTANCE().getOrderDoneList().getValue();

        orderDones.get(index).setStatus(orderDone.getStatus());


        disposableBag.add(
                orderDoneDao.updateOrderStatus(orderDone.getStatus(), orderDone.getOrder_id(), price, location, false)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            getViewState().showOrderDoneInfo(
                                    orderDone.getStatus(),
                                    orderDone.getOrder_id(),
                                    mUser.getName() + " " + mUser.getPhoneNumber(),
                                    orderDone.getDate(),
                                    location,
                                    orderList.toString(),
                                    price
                            );

                            Session.getINSTANCE().getOrderDoneList().onNext(orderDones);
                        }, t -> {
                            Log.d(Contracts.TAG, "error: " + t.getMessage());
                        })
        );

    }

    private void getStatus(OrderDone orderDone, StringBuilder orderList, int price, String location) {
        disposableBag.add(
                getFrontPadApi().getStatus(
                        Contracts.RetrofitConstant.FRONT_PAD_SECRET,
                        orderDone.getOrder_id(),
                        mUser.getPhoneNumber()
                )
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            Log.d(Contracts.TAG, "Статус: " + value.getStatus());

                            orderDone.setStatus(value.getStatus());

                            updateOrder(orderDone, orderList, price, location);
                        })
        );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableBag.clear();
    }
}
