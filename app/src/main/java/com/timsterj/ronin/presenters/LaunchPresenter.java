package com.timsterj.ronin.presenters;

import android.content.SharedPreferences;

import com.timsterj.ronin.App;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.LaunchContract;
import com.timsterj.ronin.data.local.DAO.FavoriteDao;
import com.timsterj.ronin.data.local.DAO.OrderDao;
import com.timsterj.ronin.data.local.DAO.OrderDoneDao;
import com.timsterj.ronin.data.local.DAO.ProductDao;
import com.timsterj.ronin.data.local.DAO.UserDao;
import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.domain.interfaces.IRepositoryFrontPad;
import com.timsterj.ronin.helpers.BindHelper;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.listeners.ICallbackFinishedListener;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class LaunchPresenter extends MvpPresenter<LaunchContract.View> implements LaunchContract.Presenter, ICallbackFinishedListener<Product> {

    private CompositeDisposable disposableBag = new CompositeDisposable();

    private ProductDao mProductDao;
    private UserDao mUserDao;
    private FavoriteDao mFavoriteDao;
    private OrderDao mOrderDao;
    private OrderDoneDao mOrderDoneDao;

    private boolean userPrepared = false;
    private boolean orderDoneListPrepared = false;
    private boolean orderListPrepared = false;
    private boolean favoritePrepared = false;
    private boolean productsPrepared = false;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    IRepositoryFrontPad repositoryFrontPad;

    public void init() {
        InjectHelper.plusLaunchComponent().inject(this);
        mProductDao = App.getINSTANCE()
                .getAppDatabase()
                .getProductDao();

        mUserDao = App.getINSTANCE()
                .getAppDatabase()
                .getUserDao();

        mFavoriteDao = App.getINSTANCE()
                .getAppDatabase()
                .getFavoriteDao();

        mOrderDao = App.getINSTANCE()
                .getAppDatabase()
                .getOrderDao();

        mOrderDoneDao = App.getINSTANCE()
                .getAppDatabase()
                .getOrderDoneDao();

        repositoryFrontPad.init(this);

    }

    @Override
    public void startPrepareSession() {
        repositoryFrontPad.getProducts();

        getFavorite();
        getOrderList();
        getOrderDoneList();

        if (sharedPreferences
                .getString(Contracts.PreferencesConstant.REGISTRATION_TYPE, Contracts.AuthorizationConstant.REGISTRATION_TYPE_NONE)
                .equals(Contracts.AuthorizationConstant.REGISTRATION_TYPE_NONE)) {
            userPrepared = true;
        } else {
            prepareUser();
        }

    }

    @Override
    public void prepareUser() {
        disposableBag.add(
                mUserDao.getUser()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(user -> {
                            Session.getINSTANCE().getUser()
                                    .onNext(user);
                            userPrepared = true;
                            endPrepareSession();
                        }, t -> {
                            t.printStackTrace();
                        })
        );
    }

    @Override
    public void getOrderDoneList() {
        disposableBag.add(
                mOrderDoneDao.getAllOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value->{
                    Session.getINSTANCE().getOrderDoneList()
                            .onNext(value);
                    orderDoneListPrepared = true;
                    endPrepareSession();
                })
        );

    }

    public void getProducts() {

        disposableBag.add(
                mProductDao.getProducts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            Session.getINSTANCE().getProductBase().onNext(value);
                            BindHelper.bindProductsBase(value);
                            productsPrepared = true;
                            endPrepareSession();

                        })
        );
    }


    private void getFavorite() {

        disposableBag.add(
                mFavoriteDao.getFavoriteProducts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            Session.getINSTANCE().getFavoriteList().onNext(value);
                            favoritePrepared = true;
                            endPrepareSession();

                        })
        );


    }

    private void getOrderList() {
        disposableBag.add(
                mOrderDao.getAllOrders()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {

                            Session.getINSTANCE().getOrderList().onNext(value);
                            orderListPrepared = true;
                            endPrepareSession();
                        })

        );
    }

    @Override
    public void endPrepareSession() {
        if (checkOutAllPrepared()) {
            onDestroy();
            nextActivity();
        }
    }


    @Override
    public boolean checkOutAllPrepared() {
        boolean allPrepared = true;

        if (!productsPrepared) {
            allPrepared = false;
        }

        if (!userPrepared) {
            allPrepared = false;
        }

        if (!favoritePrepared) {
            allPrepared = false;
        }

        if (!orderListPrepared) {
            allPrepared = false;
        }
        if (!orderDoneListPrepared) {
            allPrepared = false;
        }

        return allPrepared;

    }

    @Override
    public void onError() {
        if (!sharedPreferences.getBoolean(Contracts.PreferencesConstant.FIRST_RUN, true)) {
            getProducts();
        } else {
            getViewState().showErrorInternet();
        }
    }

    @Override
    public void onSuccess(List<Product> data) {
        Collections.sort(data);
        disposableBag.add(
                mProductDao.deleteAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            setFirstRunSetting();
                            BindHelper.bindProductsBase(data);
                            insertAll(data);
                        })
        );

    }

    public void insertAll(List<Product> data) {
        disposableBag.add(
                mProductDao.insertAll(data)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            productsPrepared = true;
                            Session.getINSTANCE().getProductBase()
                                    .onNext(data);
                            endPrepareSession();
                        }, t -> {
                            t.printStackTrace();
                        })
        );
    }

    private void setFirstRunSetting() {
        sharedPreferences.edit()
                .putBoolean(Contracts.PreferencesConstant.FIRST_RUN, false)
                .apply();
    }

    public void clear() {
        disposableBag.clear();
    }

    public void nextActivity() {
        getViewState().nextActivity();
    }

    @Override
    public void onDestroy() {
        clear();
        super.onDestroy();
    }
}
