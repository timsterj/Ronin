package com.timsterj.ronin.domain.repositories;

import android.util.Log;

import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.domain.api.FrontPadApi;
import com.timsterj.ronin.domain.interfaces.IRepositoryFrontPad;
import com.timsterj.ronin.domain.modelDTO.ProductDTO;
import com.timsterj.ronin.helpers.BindHelper;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.listeners.ICallbackFinishedListener;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RepositoryFrontPad implements IRepositoryFrontPad {

    private ICallbackFinishedListener<Product> mCallback;

    @Inject
    Retrofit retrofit;

    private CompositeDisposable mDisposableBag = new CompositeDisposable();

    @Override
    public void init(ICallbackFinishedListener callback) {
        mCallback = callback;
        InjectHelper.plusLaunchComponent().inject(this);
    }

    @Override
    public void getProducts() {
        List<Product> productsLists = new ArrayList<>();

        mDisposableBag.add(
                getProductsResponse()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            prepareList(value, productsLists);
                            mCallback.onSuccess(productsLists);
                        }, t -> {
                            mCallback.onError();
                            Log.d(Contracts.TAG, "getProducts: Error: " + t.getMessage());
                            t.printStackTrace();
                        }, () -> {
                            clear();
                        })
        );
    }

    Observable<ProductDTO> getProductsResponse(){
        return getFrontPadApi().getProducts(Contracts.RetrofitConstant.FRONT_PAD_SECRET);
    }


    FrontPadApi getFrontPadApi(){
        return retrofit.create(FrontPadApi.class);
    }

    void prepareList(ProductDTO productDTO, List<Product> productsList) {
        mDisposableBag.add(
                Observable.zip(
                        Observable.fromArray(productDTO.getProduct_id()),
                        Observable.fromArray(productDTO.getName()),
                        Observable.fromArray(productDTO.getPrice()),
                        (Function3<String, String, String, Object>) (id, name, price) -> {
                            Product product = new Product(id, BindHelper.bindProductBaseImageUrl(id),name, Integer.valueOf(price), BindHelper.bindProductRecept(id), BindHelper.bindProductWeight(id));
                            return product;
                        }
                ).subscribe(v -> {
                    Product product = (Product) v;
                    productsList.add(product);
                }, t -> {
                    t.printStackTrace();
                })
        );
    }

    public void clear(){
        mDisposableBag.clear();
    }
}