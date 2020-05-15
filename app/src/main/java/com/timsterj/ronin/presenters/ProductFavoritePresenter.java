package com.timsterj.ronin.presenters;

import android.util.Log;

import com.timsterj.ronin.App;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.ProductFavoriteContract;
import com.timsterj.ronin.data.local.DAO.FavoriteDao;
import com.timsterj.ronin.data.local.DAO.OrderDao;
import com.timsterj.ronin.data.model.Favorite;
import com.timsterj.ronin.data.model.Order;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class ProductFavoritePresenter extends MvpPresenter<ProductFavoriteContract.View> implements ProductFavoriteContract.Presenter {

    private CompositeDisposable disposableBag = new CompositeDisposable();

    private FavoriteDao mFavoriteDao;
    private OrderDao mOrderDao;

    public void init(){
        mFavoriteDao = App.getINSTANCE()
                .getAppDatabase()
                .getFavoriteDao();

        mOrderDao = App.getINSTANCE()
                .getAppDatabase()
                .getOrderDao();

    }

    @Override
    public void updateFavorite(Favorite favorite) {
        disposableBag.add(
                mFavoriteDao.updateFavorite(favorite)
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                            Log.d(Contracts.TAG, "Favorite updated " + favorite.getProductList().size());
                        })
        );
    }

    @Override
    public void addOrderList(Order order) {
        disposableBag.add(
                mOrderDao.getAllOrders()
                        .subscribeOn(Schedulers.io())
                        .subscribe(value -> {
                            if (value.isEmpty()) {
                                insertOrder(order);
                            } else {
                                updateOrder(order);
                            }
                        })
        );

    }

    private void insertOrder(Order order) {
        disposableBag.add(
                mOrderDao.insertOrder(order)
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                            Log.d(Contracts.TAG, "Order inserted " + order.getProductItems().size());
                        })
        );

    }

    private void updateOrder(Order order) {
        disposableBag.add(
                mOrderDao.updateOrder(order)
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                            Log.d(Contracts.TAG, "Order updated " + order.getProductItems().size());
                        })
        );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableBag.clear();
    }
}
