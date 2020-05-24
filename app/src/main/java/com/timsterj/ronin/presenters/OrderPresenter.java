package com.timsterj.ronin.presenters;

import android.util.Log;

import com.timsterj.ronin.App;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.OrderContract;
import com.timsterj.ronin.data.local.DAO.OrderDoneDao;
import com.timsterj.ronin.data.model.Order;
import com.timsterj.ronin.data.model.OrderDone;
import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.data.model.User;
import com.timsterj.ronin.domain.api.FrontPadApi;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.model.ProductItem;
import com.timsterj.ronin.utils.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.MvpPresenter;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class OrderPresenter extends MvpPresenter<OrderContract.View> implements OrderContract.Presenter {

    private CompositeDisposable disposableBag = new CompositeDisposable();
    private List<Product> orderedProduct = new ArrayList<>();
    private List<ProductItem> additionalList = new ArrayList<>();
    private Order mOrder = null;
    private User mUser = null;
    private OrderDoneDao orderDoneDao;

    private int price = 0;

    @Inject
    Retrofit retrofit;

    public void init() {
        InjectHelper.plusHomeComponent().inject(this);
        orderedProduct.clear();
        additionalList.clear();

        disposableBag.add(
                Session.getINSTANCE().getUser().subscribe(
                        value -> {
                            mUser = value;
                        }
                )
        );

        orderDoneDao = App.getINSTANCE()
                .getAppDatabase()
                .getOrderDoneDao();
    }

    FrontPadApi getFrontPadApi() {
        return retrofit.create(FrontPadApi.class);
    }

    @Override
    public void initOrder() {

        List<Order> orders = Session.getINSTANCE().getOrderList().getValue();
        mOrder = orders.get(orders.size() - 1);

        orderedProduct.addAll(mOrder.getProductItems());
        additionalList.addAll(Session.getINSTANCE().getAdditionalProducts().getValue());

        initAdditionalList();
        getCurrentPrice();
    }

    @Override
    public void initAdditionalList() {
        List<Product> productsRemove = new ArrayList<>();

        for (int i = 0; i < orderedProduct.size(); i++) {
            Product product = orderedProduct.get(i);

            for (int j = 0; j < additionalList.size(); j++) {
                Product additionalProduct = additionalList.get(j).getProduct();

                if (product.getId().equals(additionalProduct.getId())) {
                    additionalList.get(j).getProduct().setCount(product.getCount());
                    productsRemove.add(additionalProduct);

                }

            }

        }

        for (Product product : productsRemove) {
            orderedProduct.remove(product);
        }

    }

    @Override
    public void getCurrentPrice() {
        StringBuilder orderlist = new StringBuilder();
        price = 0;

        for (int i = 0; i < orderedProduct.size(); i++) {
            Product product = orderedProduct.get(i);

            int productPrice = product.getPrice() * product.getCount();

            price += productPrice;

            orderlist.append(product.getName())
                    .append(" ( " + product.getPrice() + " руб )")
                    .append(" x " + product.getCount() + " шт.")
                    .append("\n");
        }

        for (int i = 0; i < additionalList.size(); i++) {
            Product product = additionalList.get(i).getProduct();

            if (product.getCount() > 0) {
                int productPrice = product.getPrice() * product.getCount();

                price += productPrice;

                orderlist.append(product.getName())
                        .append(" ( " + product.getPrice() + " руб )")
                        .append(" x " + product.getCount() + " шт.")
                        .append("\n");
            }


        }

        orderlist.append("Итого: " + price + " руб");

        getViewState().updateOrderlist(orderlist.toString());
        getViewState().updatePrice(price);
    }


    public int getPrice() {
        return price;
    }

    @Override
    public void doOrder(String descr) {
        getViewState().showLoading();
        List<Product> orderList = new ArrayList<>();
        int index = 0;

        RequestBody rb = null;
        HashMap<String, RequestBody> mp = new HashMap<>();

        for (int i = 0; i < orderedProduct.size(); i++) {
            Product product = orderedProduct.get(i);
            rb = RequestBody.create(MediaType.parse("text/plain"), product.getId());
            mp.put("product[" + index + "]", rb);
            rb = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(product.getCount()));
            mp.put("product_kol[" + index + "]", rb);

            orderList.add(product);
            index++;
        }

        for (int i = 0; i < additionalList.size(); i++) {
            Product product = additionalList.get(i).getProduct();
            if (product.getCount() > 0) {
                rb = RequestBody.create(MediaType.parse("text/plain"), product.getId());
                mp.put("product[" + index + "]", rb);
                rb = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(product.getCount()));
                mp.put("product_kol[" + index + "]", rb);

                orderList.add(product);
                index++;
            }

        }

        String location = "ул. " + mUser.getStreet() + ", " +
                "д. " + mUser.getHome() + ", " +
                "кв. " + mUser.getApart() + ", " +
                "этаж " + mUser.getEt() + ", " +
                "под. " + mUser.getPod();

        Log.d(Contracts.TAG, "doOrder: name: " + mUser.getName());

        disposableBag.add(
                getFrontPadApi().doOrder(
                        Contracts.RetrofitConstant.FRONT_PAD_SECRET,
                        mp,
                        mUser.getStreet(),
                        mUser.getHome(),
                        mUser.getPod(),
                        mUser.getEt(),
                        mUser.getApart(),
                        mUser.getPhoneNumber(),
                        descr + "_" + mUser.getName() + "_"
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            value.setStatus("Новый");
                            value.setOrderList(orderList);

                            value.setLocation(location);

                            addToDB(value);
                        }, t -> {
                            getViewState().showError();
                        })
        );
    }

    @Override
    public void addToDB(OrderDone orderDone) {

        orderDone.setDate(DateUtil.getCurrentDate());
        orderDone.setPrice(getPrice());

        List<OrderDone> orderDones = Session.getINSTANCE().getOrderDoneList().getValue();

        orderDones.add(orderDone);

        int index = orderDones.indexOf(orderDone);

        Session.getINSTANCE().getOrderDoneList().onNext(orderDones);

        disposableBag.add(
                orderDoneDao.insertOrder(orderDone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            getViewState().onSuccess(index);

                        })
        );


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableBag.clear();
    }
}
