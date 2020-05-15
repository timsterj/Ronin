package com.timsterj.ronin.presenters;

import android.util.Log;

import com.timsterj.ronin.App;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.OrderInfoContract;
import com.timsterj.ronin.data.local.DAO.OrderDoneDao;
import com.timsterj.ronin.data.model.OrderDone;
import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.data.model.User;
import com.timsterj.ronin.domain.api.FrontPadApi;
import com.timsterj.ronin.helpers.InjectHelper;

import java.util.ArrayList;
import java.util.List;

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

    @Inject
    Retrofit retrofit;

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

    }

    private FrontPadApi getFrontPadApi() {
        return retrofit.create(FrontPadApi.class);
    }

    @Override
    public void initOrderDone(int index) {
        StringBuilder orderlist = new StringBuilder();
        OrderDone orderDone = orderDones.get(index);

        String status = orderDone.getStatus();
        String date = orderDone.getDate();
        String orderId = orderDone.getOrder_id();

        String location = mUser.getStreet() + mUser.getHome() + mUser.getApart() + mUser.getEt() + mUser.getPod();

        int price = 0;
        for (Product product : orderDone.getOrderList()) {
            price = price + (product.getCount() * product.getPrice());

            orderlist.append(product.getName())
                    .append(" ( " + product.getPrice() + " руб )")
                    .append(" x " + product.getCount() + " шт.")
                    .append("\n");
        }

        getViewState().showOrderDoneInfo(
                status,
                orderId,
                mUser.getName() + " " + mUser.getPhoneNumber(),
                date,
                location,
                orderlist.toString()
        );


        int finalPrice = price;

        disposableBag.add(
                getFrontPadApi().getStatus(
                        Contracts.RetrofitConstant.FRONT_PAD_SECRET,
                        orderDone.getOrder_id(),
                        mUser.getPhoneNumber()
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            orderDone.setPrice(String.valueOf(finalPrice));
                            orderDone.setStatus(value.getStatus());

                            getViewState().showOrderDoneInfo(
                                    orderDone.getStatus(),
                                    orderId,
                                    mUser.getName() + " " + mUser.getPhoneNumber(),
                                    date,
                                    location,
                                    orderlist.toString()
                            );

                            updateOrder(value.getStatus(), orderDone.getOrder_id());
                            getViewState().startOrderStatusService();
                        })
        );

    }

    private void updateOrder(String status, String id) {
        disposableBag.add(
                orderDoneDao.updateOrderStatus(status, id)
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(() -> {

                        }, t -> {
                            Log.d(Contracts.TAG, "error: " + t.getMessage());
                        })
        );

    }

}
