package com.timsterj.ronin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.timsterj.ronin.R;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.mvp.ProductInfoContract;
import com.timsterj.ronin.data.model.Favorite;
import com.timsterj.ronin.data.model.Order;
import com.timsterj.ronin.databinding.FragmentProductInfoBinding;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.listeners.NotificationListener;
import com.timsterj.ronin.listeners.OnBackPressed;
import com.timsterj.ronin.model.ProductItem;
import com.timsterj.ronin.navigation.LocalCiceroneHolder;
import com.timsterj.ronin.presenters.ProductInfoPresenter;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

public class ProductInfoFragment extends MvpAppCompatFragment implements ProductInfoContract.View, OnBackPressed {

    private static final String EXTRA_NAME = "extra_name";

    private FragmentProductInfoBinding binding;
    private String ROOT_TAB = "";

    @InjectPresenter
    ProductInfoPresenter presenter;

    @Inject
    LocalCiceroneHolder ciceroneHolder;

    public static ProductInfoFragment getNewInstance(String name, Bundle arguments) {
        ProductInfoFragment fragment = new ProductInfoFragment();

        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectHelper.plusHomeComponent().inject(this);
        presenter.init();
        binding = FragmentProductInfoBinding.inflate(getLayoutInflater());

        if (savedInstanceState != null) {

        }

        init();
    }

    private void init() {
        ROOT_TAB = getArguments().getString("root_tab");

        initButtons();
        initInfo();
    }

    private void initButtons(){
        binding.btnAddFavorite.setOnClickListener(view->{
            addFavorite();
        });

        binding.btnAddOrderlist.setOnClickListener(view->{
            addProductInOrderList();
        });

    }

    private void initInfo(){
        String name = getArguments().getString("product_name");
        String recept = getArguments().getString("product_recept");
        String weight = getArguments().getString("product_weight");
        String imgUrl = getArguments().getString("product_url");
        int price = getArguments().getInt("product_price");

        if (!imgUrl.equals("http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_noimage1.gif")) {
            Picasso.get()
                    .load(imgUrl)
                    .into(binding.imgViewFood);
        }

        binding.txtFoodName.setText(name);
        binding.txtPrice.setText(price + " руб");
        binding.txtRecept.setText(recept);
        binding.txtWeight.setText(weight);

    }

    public void addFavorite() {
        List<ProductItem> products = Session.getINSTANCE().getProducts().getValue();
        List<Favorite> favorites = Session.getINSTANCE().getFavoriteList().getValue();
        String id = getArguments().getString("product_id");
        ProductItem productItem = null;
        Favorite favorite = null;

        if (favorites.isEmpty()) {
            favorite = new Favorite();
            favorites.add(favorite);
        } else {
            favorite = favorites.get(favorites.size() - 1);
        }

        Iterator<ProductItem> productItemIterator = products.iterator();

        while (productItemIterator.hasNext()) {
            ProductItem element = productItemIterator.next();
            if (element.getProduct() != null) {
                if (id.equals(element.getProduct().getId())) {
                    productItem = element;
                }

            }

        }

        if (favorite.getProductList().contains(productItem)) {
            Snackbar.make(binding.scrollView, R.string.has_been_added_to_favorites_yet, Snackbar.LENGTH_SHORT)
                    .show();

        } else {
            favorite.getProductList().add(productItem);
            presenter.addFavorite(favorite);
            ((NotificationListener) getActivity()).incTabNotification(2);
            ((NotificationListener) getActivity()).updateTabNotification();
        }

        Session.getINSTANCE().getFavoriteList().onNext(favorites);

    }

    private void addProductInOrderList() {
        List<ProductItem> products = Session.getINSTANCE().getProducts().getValue();
        List<Order> orderList = Session.getINSTANCE().getOrderList().getValue();
        String id = getArguments().getString("product_id");
        ProductItem productItem = null;
        Order order = null;

        if (orderList.isEmpty()) {
            order = new Order();
            orderList.add(order);
        } else {
            order = orderList.get(orderList.size() - 1);
        }

        Iterator<ProductItem> productItemIterator = products.iterator();

        while (productItemIterator.hasNext()) {
            ProductItem element = productItemIterator.next();
            if (element.getProduct() != null) {
                if (id.equals(element.getProduct().getId())) {
                    productItem = element;
                }

            }

        }

        if (order.getProductItems().contains(productItem.getProduct())) {
            Snackbar.make(binding.scrollView, R.string.has_been_added_to_orderlist_yet, Snackbar.LENGTH_SHORT).show();
        } else {
            order.getProductItems().add(productItem.getProduct());
            presenter.addOrderList(order);
            ((NotificationListener) getActivity()).incTabNotification(1);
            ((NotificationListener) getActivity()).updateTabNotification();
        }

        Session.getINSTANCE().getOrderList().onNext(orderList);

    }


    @Override
    public void onBackPressed() {

        getRouter().exit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(ROOT_TAB);
    }

    public Router getRouter() {
        return getCicerone().getRouter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
