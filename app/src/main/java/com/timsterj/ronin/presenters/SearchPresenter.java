package com.timsterj.ronin.presenters;

import android.util.Log;

import com.timsterj.ronin.App;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.SearchContract;
import com.timsterj.ronin.data.local.DAO.FavoriteDao;
import com.timsterj.ronin.data.local.DAO.OrderDao;
import com.timsterj.ronin.data.model.Favorite;
import com.timsterj.ronin.data.model.Order;
import com.timsterj.ronin.model.ProductItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class SearchPresenter extends MvpPresenter<SearchContract.View> implements SearchContract.Presenter {

    private List<ProductItem> products = new ArrayList<>();

    private CompositeDisposable disposableBag = new CompositeDisposable();

    private OrderDao mOrderDao;
    private FavoriteDao mFavoriteDao;

    public void init() {

        mOrderDao = App.getINSTANCE()
                .getAppDatabase()
                .getOrderDao();
        mFavoriteDao = App.getINSTANCE()
                .getAppDatabase()
                .getFavoriteDao();
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
    public void addFavorite(Favorite favorite) {
        disposableBag.add(
                mFavoriteDao.getFavoriteProducts()
                        .subscribeOn(Schedulers.io())
                        .subscribe(favoriteProducts -> {
                            if (favoriteProducts.isEmpty()) {
                                insertFavorite(favorite);
                            } else {
                                updateFavorite(favorite);
                            }
                        })
        );

    }

    private void insertFavorite(Favorite favorite) {
        disposableBag.add(
                mFavoriteDao.insertFavorite(favorite)
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                            Log.d(Contracts.TAG, "Order inserted " + favorite.getProductList().size());
                        })
        );

    }

    private void updateFavorite(Favorite favorite) {
        disposableBag.add(
                mFavoriteDao.updateFavorite(favorite)
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                            Log.d(Contracts.TAG, "Order updated " + favorite.getProductList().size());
                        })
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableBag.clear();
    }

}
