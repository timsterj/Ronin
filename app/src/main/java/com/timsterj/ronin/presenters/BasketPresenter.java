package com.timsterj.ronin.presenters;

import com.timsterj.ronin.App;
import com.timsterj.ronin.contracts.mvp.BasketContract;
import com.timsterj.ronin.data.local.DAO.OrderDao;
import com.timsterj.ronin.data.model.Order;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class BasketPresenter extends MvpPresenter<BasketContract.View> implements BasketContract.Presenter {

    private CompositeDisposable disposableBag = new CompositeDisposable();

    OrderDao mOrderDao;

    public void init() {
        mOrderDao = App.getINSTANCE()
                .getAppDatabase()
                .getOrderDao();

    }

    @Override
    public void updateOrder(Order order) {
        disposableBag.add(
                mOrderDao.updateOrder(order)
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {

                        })
        );

    }


}
