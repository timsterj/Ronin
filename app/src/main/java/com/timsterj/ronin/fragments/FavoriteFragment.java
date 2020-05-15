package com.timsterj.ronin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.timsterj.ronin.R;
import com.timsterj.ronin.adapters.FavoritesPagerAdapter;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.databinding.FragmentFavoriteBinding;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.listeners.OnBackPressed;
import com.timsterj.ronin.navigation.LocalCiceroneHolder;
import com.timsterj.ronin.navigation.Screens;

import javax.inject.Inject;

import moxy.MvpAppCompatFragment;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

public class FavoriteFragment extends MvpAppCompatFragment implements OnBackPressed {

    private static final String EXTRA_NAME = "extra_name";

    private FragmentFavoriteBinding binding;

    private FavoritesPagerAdapter adapter;

    @Inject
    LocalCiceroneHolder ciceroneHolder;

    public static FavoriteFragment getNewInstance(String name){
        FavoriteFragment fragment = new FavoriteFragment();

        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        binding = FragmentFavoriteBinding.bind(view);

        adapter = new FavoritesPagerAdapter(getChildFragmentManager(), PagerAdapter.POSITION_NONE);


        if (savedInstanceState != null) {

        }

        init();

        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectHelper.plusHomeComponent().inject(this);


    }
    private void init() {
        initTabFavorites();
        initRvFavorites();
    }

    private void initTabFavorites() {
        binding.tabLayoutFavorites.setupWithViewPager(binding.rvFavorites);

    }

    private void initRvFavorites(){
        binding.rvFavorites.setAdapter(adapter);
        binding.rvFavorites.setPagingEnabled(false);

    }

    void onProductInfoOpen(Product product){
        Bundle arguments = new Bundle();

        arguments.putString("root_tab", Contracts.NavigationConstant.TAB_FAVORITE);
        arguments.putString("product_id", product.getId());
        arguments.putString("product_url", product.getImgUrl());
        arguments.putString("product_name", product.getName());
        arguments.putInt("product_price", product.getPrice());

        getRouter().navigateTo(new Screens.ProductInfoScreen(Contracts.NavigationConstant.PRODUCT_INFO, arguments));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(Contracts.NavigationConstant.TAB_FAVORITE);
    }

    public Router getRouter() {
        return getCicerone().getRouter();
    }

    @Override
    public void onBackPressed() { // TODO Решить с cicerone проблема с removeView();
        getRouter().exit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
