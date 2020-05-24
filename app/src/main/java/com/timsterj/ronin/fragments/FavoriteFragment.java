package com.timsterj.ronin.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.timsterj.ronin.R;
import com.timsterj.ronin.adapters.ProductAdapter;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.ProductFavoriteContract;
import com.timsterj.ronin.data.model.Favorite;
import com.timsterj.ronin.data.model.Order;
import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.databinding.FragmentProductFavoriteBinding;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.listeners.IClickProductListener;
import com.timsterj.ronin.listeners.NotificationListener;
import com.timsterj.ronin.listeners.OnBackPressed;
import com.timsterj.ronin.model.ProductItem;
import com.timsterj.ronin.navigation.LocalCiceroneHolder;
import com.timsterj.ronin.navigation.Screens;
import com.timsterj.ronin.presenters.ProductFavoritePresenter;
import com.timsterj.ronin.utils.ItemButtonAction;
import com.timsterj.ronin.utils.ItemSwipeCallback;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

public class FavoriteFragment extends MvpAppCompatFragment implements ProductFavoriteContract.View,
        IClickProductListener<Product>,
        OnBackPressed {

    private static final String EXTRA_NAME = "extra_name";

    private CompositeDisposable disposableBag = new CompositeDisposable();
    private FragmentProductFavoriteBinding binding;

    private LinearLayoutManager manager;
    private ItemTouchHelper.SimpleCallback itemTouchHelperUtil;

    private List<ProductItem> favoritesProducts;

    @Inject
    ProductAdapter adapter;
    @Inject
    LocalCiceroneHolder ciceroneHolder;
    @InjectPresenter
    ProductFavoritePresenter presenter;

    public static FavoriteFragment getNewInstance(String name) {
        FavoriteFragment fragment = new FavoriteFragment();

        Bundle arguments = new Bundle();
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
        binding = FragmentProductFavoriteBinding.inflate(getLayoutInflater());
        presenter.init();

        manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        if (savedInstanceState != null) {
            Parcelable parcelable = savedInstanceState.getParcelable("rv_favorite_products");

            manager.onRestoreInstanceState(parcelable);
        }

        init();

    }

    private void init() {
        initRvProducts();
        initSubscribers();
    }

    private void initSubscribers() {
        disposableBag.add(
                Session.getINSTANCE().getFavoriteList()
                        .subscribe(favorites -> {
                            if (!favorites.isEmpty()) {
                                favoritesProducts = favorites.get(favorites.size() - 1).getProductList();

                                if (favoritesProducts.isEmpty()) {
                                    showErrorEmptyOrderlist();
                                } else {
                                    hideErrorEmptyOrderlist();
                                }


                                adapter.setProductList(favoritesProducts);
                            }

                        })
        );

    }

    private void initRvProducts() {
        adapter.setListener(this);

        itemTouchHelperUtil = new ItemSwipeCallback(getContext(), binding.rvFavoriteProducts, 250) {
            @Override
            public void instantiateActionButton(RecyclerView.ViewHolder viewHolder, List<ItemButtonAction> buffer) {
                buffer.add(new ItemButtonAction(
                        Objects.requireNonNull(getContext()),
                        R.drawable.ic_delete_white_24dp,
                        Color.RED,
                        pos -> deleteProductFromFavorite(pos)
                ));

                buffer.add(new ItemButtonAction(
                        getContext(),
                        R.drawable.ic_shopping_basket_white_24dp,
                        Color.GREEN,
                        pos -> addProductFromFavoriteToOrderlist(pos)
                ));

            }
        };


        binding.rvFavoriteProducts.setAdapter(adapter);

        binding.rvFavoriteProducts.setLayoutManager(manager);

    }

    @Override
    public void deleteProductFromFavorite(int position) {
        List<Favorite> favorites = Session.getINSTANCE().getFavoriteList().getValue();
        Favorite favorite = null;

        if (favorites.isEmpty()) {
            favorite = new Favorite();
            favorites.add(favorite);
        } else {
            favorite = favorites.get(favorites.size() - 1);
        }

        favorite.getProductList().remove(position);
        presenter.updateFavorite(favorite);

        Snackbar.make(binding.rvFavoriteProducts, R.string.message_removed, Snackbar.LENGTH_SHORT).show();

        Session.getINSTANCE().getFavoriteList().onNext(favorites);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void addProductFromFavoriteToOrderlist(int position) {
        List<Order> orderList = Session.getINSTANCE().getOrderList().getValue();
        Order order = null;
        if (orderList.isEmpty()) {
            order = new Order();
            orderList.add(order);
        } else {
            order = orderList.get(orderList.size() - 1);
        }

        if (order.getProductItems().contains(favoritesProducts.get(position).getProduct())) {
            Snackbar.make(binding.txtErrorEmpty, R.string.has_been_added_to_orderlist_yet, Snackbar.LENGTH_SHORT).show();
        } else {
            order.getProductItems().add(favoritesProducts.get(position).getProduct());
            presenter.addOrderList(order);
            ((NotificationListener) Objects.requireNonNull(getActivity())).incTabNotification(1);
            ((NotificationListener) getActivity()).updateTabNotification();
        }

        Session.getINSTANCE().getOrderList().onNext(orderList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onProductClick(Product product) {
        Bundle arguments = new Bundle();

        arguments.putString("root_tab", Contracts.NavigationConstant.TAB_FAVORITE);
        arguments.putString("product_id", product.getId());
        arguments.putString("product_url", product.getImgUrl());
        arguments.putString("product_name", product.getName());
        arguments.putInt("product_price", product.getPrice());

        getRouter().navigateTo(new Screens.ProductInfoScreen(Contracts.NavigationConstant.PRODUCT_INFO, arguments));

    }

    @Override
    public void showErrorEmptyOrderlist() {
        binding.txtErrorEmpty.setVisibility(View.VISIBLE);
        binding.rvFavoriteProducts.setVisibility(View.GONE);

    }

    @Override
    public void hideErrorEmptyOrderlist() {
        binding.txtErrorEmpty.setVisibility(View.GONE);
        binding.rvFavoriteProducts.setVisibility(View.VISIBLE);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("rv_favorite_product", manager.onSaveInstanceState());
    }


    public Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(Contracts.NavigationConstant.TAB_FAVORITE);
    }

    public Router getRouter() {
        return getCicerone().getRouter();
    }

    @Override
    public void onBackPressed() {
        getRouter().exit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
