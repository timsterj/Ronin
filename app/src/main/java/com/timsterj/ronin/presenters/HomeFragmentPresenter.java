package com.timsterj.ronin.presenters;

import android.content.Context;

import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.mvp.HomeFragmentContract;
import com.timsterj.ronin.helpers.BindHelper;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.model.AboutUsItem;
import com.timsterj.ronin.model.ProductItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.MvpPresenter;

public class HomeFragmentPresenter extends MvpPresenter<HomeFragmentContract.View> implements HomeFragmentContract.Presenter {

    private CompositeDisposable disposableBag = new CompositeDisposable();

    @Inject
    Context context;

    public void init() {
        InjectHelper.plusHomeComponent().inject(this);
    }

    @Override
    public void prepareMightLikeList() {
        List<ProductItem> mightLikeList = new ArrayList<>();

        disposableBag.add(
                Observable.just(Session.getINSTANCE().getProductBase().getValue())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            int sizeList = 20;
                            for (int i = 0; i < sizeList; i++) {
                                int next = (int) ((Math.random() * (value.size() - 0)) + 0);

                                mightLikeList.add(new ProductItem(value.get(next)));
                            }
                        }, t -> {

                        }, () -> {
                            Session.getINSTANCE().getProductsMightLike().onNext(mightLikeList);
                        })
        );
    }

    @Override
    public void prepareAboutUsList() {
        List<AboutUsItem> aboutUsList = new ArrayList<>();


        disposableBag.add(
                Observable.just(aboutUsList)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            BindHelper.bindAboutUsList(value, context.getResources());
                        }, t -> {

                        }, () -> {
                            Session.getINSTANCE().getProductsAboutUs().onNext(aboutUsList);
                        })

        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableBag.clear();
    }
}
