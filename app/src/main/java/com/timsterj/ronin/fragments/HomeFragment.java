package com.timsterj.ronin.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.timsterj.ronin.adapters.AboutUsAdapter;
import com.timsterj.ronin.adapters.ProductAdapter;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.HomeFragmentContract;
import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.databinding.FragmentHomeBinding;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.listeners.IClickProductListener;
import com.timsterj.ronin.model.AboutUsItem;
import com.timsterj.ronin.model.ProductItem;
import com.timsterj.ronin.navigation.LocalCiceroneHolder;
import com.timsterj.ronin.navigation.Screens;
import com.timsterj.ronin.presenters.HomeFragmentPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

public class HomeFragment extends MvpAppCompatFragment implements HomeFragmentContract.View, IClickProductListener {

    private static final String EXTRA_NAME = "extra_name";

    private FragmentHomeBinding binding;

    private CompositeDisposable disposableBag = new CompositeDisposable();

    private LinearLayoutManager managerAboutUs;
    private LinearLayoutManager managerMightLike;

    @InjectPresenter
    HomeFragmentPresenter presenter;


    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    AboutUsAdapter aboutUsAdapter;
    @Inject
    ProductAdapter productAdapter;
    @Inject
    LocalCiceroneHolder ciceroneHolder;

    public static HomeFragment getNewInstance(String name) {
        HomeFragment fragment = new HomeFragment();

        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        InjectHelper.plusHomeComponent().inject(this);
        presenter.init();

        managerAboutUs = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        managerMightLike = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        if (savedInstanceState != null) {

            Parcelable parcelableAboutUs = savedInstanceState.getParcelable("rv_aboutUs");
            Parcelable parcelableMightLike = savedInstanceState.getParcelable("rv_mightLike");

            managerAboutUs.onRestoreInstanceState(parcelableAboutUs);
            managerMightLike.onRestoreInstanceState(parcelableMightLike);
        }

        init();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    public void init() {
        initActions();
        initRvAboutUs();
        initRvMightLike();
        initBtnUserInfo();

    }

    @Override
    public void showTutorial() {
        boolean firstRun = sharedPreferences.getBoolean(Contracts.PreferencesConstant.HOME_TAB_FIRST_RUN, true);

        if (firstRun) {
            getRouter().navigateTo(new Screens.TurorialScreen(Contracts.NavigationConstant.TUTORIAL, Contracts.NavigationConstant.TAB_HOME));

            sharedPreferences.edit().putBoolean(Contracts.PreferencesConstant.HOME_TAB_FIRST_RUN, false).apply();
        }

    }

    public void initRvMightLike() {

        presenter.prepareMightLikeList();

        binding.rvAboutUs.setHasFixedSize(true);
        binding.rvMightlike.setLayoutManager(managerMightLike);

        disposableBag.add(Session.getINSTANCE().getProductsMightLike()
                .subscribe(value -> {
                    updateMightLikeList(value);
                    binding.actionCommunication.setVisibility(View.VISIBLE);
                })
        );

    }

    private void initRvAboutUs() {

        presenter.prepareAboutUsList();
        binding.rvAboutUs.setHasFixedSize(true);
        binding.rvAboutUs.setLayoutManager(managerAboutUs);

        disposableBag.add(Session.getINSTANCE().getProductsAboutUs()
                .subscribe(value -> {
                    updateAboutUsList(value);
                })
        );

    }

    private void initBtnUserInfo() {
        MenuItem btnUserInfo = binding.toolbarHome.getMenu().getItem(0);

        btnUserInfo.setOnMenuItemClickListener(view -> {
            getRouter().navigateTo(new Screens.UserInfoScreen(Contracts.NavigationConstant.USER_INFO));

            return false;
        });
    }

    private void initActions() {

        binding.actionCall.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Contracts.ContactsConstant.SUPPORT_PHONENUMBER));
            startActivity(intent);
        });

        binding.actionVk.setOnClickListener(view -> {
            String group = Contracts.ContactsConstant.VK_GROUP_LINK;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(group));
            startActivity(intent);
        });

        binding.actionInsta.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(Contracts.ContactsConstant.INSTA_GROUP_LINK));
            startActivity(intent);
        });

        binding.actionMail.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + Contracts.ContactsConstant.SUPPORT_EMAIL));
            startActivity(intent);
        });
    }

    @Override
    public void updateMightLikeList(List<ProductItem> mightLikeLis) {
        productAdapter.setListener(this);
        productAdapter.setProductList(mightLikeLis);
        binding.rvMightlike.setAdapter(productAdapter);
    }

    @Override
    public void updateAboutUsList(List<AboutUsItem> aboutUsList) {
        aboutUsAdapter.setAboutUsList(aboutUsList);
        binding.rvAboutUs.setAdapter(aboutUsAdapter);
    }

    @Override
    public void onProductClick(Product product) {
        Bundle arguments = new Bundle();

        arguments.putString("root_tab", Contracts.NavigationConstant.TAB_HOME);
        arguments.putString("product_id", product.getId());
        arguments.putString("product_url", product.getImgUrl());
        arguments.putString("product_name", product.getName());
        arguments.putInt("product_price", product.getPrice());

        getRouter().navigateTo(new Screens.ProductInfoScreen(Contracts.NavigationConstant.PRODUCT_INFO, arguments));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("rv_mightLike", binding.rvMightlike.getLayoutManager().onSaveInstanceState());
        outState.putParcelable("rv_aboutUs", binding.rvAboutUs.getLayoutManager().onSaveInstanceState());
    }

    public Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(Contracts.NavigationConstant.TAB_HOME);
    }

    public Router getRouter() {
        return getCicerone().getRouter();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableBag.clear();
    }


}
