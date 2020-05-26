package com.timsterj.ronin.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.timsterj.ronin.adapters.AdditionalFoodAdapter;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.OrderContract;
import com.timsterj.ronin.databinding.FragmentOrderBinding;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.listeners.ICallbackAdditionalProductListener;
import com.timsterj.ronin.listeners.OnBackPressed;
import com.timsterj.ronin.model.ProductItem;
import com.timsterj.ronin.navigation.LocalCiceroneHolder;
import com.timsterj.ronin.navigation.Screens;
import com.timsterj.ronin.presenters.OrderPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.timsterj.ronin.services.LastOrderStatusWorker;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

public class OrderFragment extends MvpAppCompatFragment implements OrderContract.View, OnBackPressed, ICallbackAdditionalProductListener {

    private static final String EXTRA_NAME = "extra_name";

    private CompositeDisposable disposableBag = new CompositeDisposable();

    private FragmentOrderBinding binding;

    private LinearLayoutManager manager;

    @Inject
    LocalCiceroneHolder ciceroneHolder;
    @InjectPresenter
    OrderPresenter presenter;
    @Inject
    AdditionalFoodAdapter adapter;
    @Inject
    SharedPreferences sharedPreferences;

    public static OrderFragment getNewInstance(String name) {
        OrderFragment fragment = new OrderFragment();

        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectHelper.plusHomeComponent().inject(this);
        binding = FragmentOrderBinding.inflate(getLayoutInflater());
        presenter.init();

        manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);


        if (savedInstanceState != null) {
            String price = savedInstanceState.getString("price");
            String orderlist = savedInstanceState.getString("orderlist");

            binding.txtOrderlist.setText(orderlist);
            binding.toolbarOrder.setTitle(price + " руб");
        }

        init();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @Override
    public void updatePrice() {
        presenter.getCurrentPrice();

    }

    @Override
    public void updateOrderlist(String orderlist) {
        binding.progressOrderlist.setVisibility(View.GONE);
        binding.txtOrderlist.setVisibility(View.VISIBLE);
        binding.txtOrderlist.setText(orderlist);
    }

    @Override
    public void updatePrice(int price) {

        binding.toolbarOrder.setTitle(price + " руб");
    }

    @Override
    public void showLoading() {
        binding.btnOrderIt.setVisibility(View.GONE);
        binding.progressOrder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        binding.progressOrder.setVisibility(View.VISIBLE);
        binding.lnError.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        binding.progressOrder.setVisibility(View.GONE);
        binding.lnError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(int index) {
        int pOrderSize = sharedPreferences.getInt(Contracts.PreferencesConstant.DAILY_ORDER_SIZE_LIMIT, 0);
        sharedPreferences.edit().putInt(Contracts.PreferencesConstant.DAILY_ORDER_SIZE_LIMIT, pOrderSize+1).apply();

        startLastOrderStatusService();
        getRouter().navigateTo(new Screens.OrderInfoScreen(Contracts.NavigationConstant.ORDER_INFO, index, Contracts.NavigationConstant.TAB_BASKET));

    }

    @Override
    public void startLastOrderStatusService() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest lastOrderStatusRequest = new PeriodicWorkRequest.Builder(LastOrderStatusWorker.class, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(getActivity()).enqueue(lastOrderStatusRequest);
    }

    private void init() {
        initSubscribers();
        initMET();
        initRvAdditional();
        initBtnOrderIt();
        initBtnError();

        presenter.initOrder();
    }

    private void initSubscribers() {
        disposableBag.add(
                Session.getINSTANCE().getAdditionalProducts()
                        .subscribe(this::updateRvAdditionalList)
        );
    }

    private void initMET() {
        binding.edtDescription.setFloatingLabelText("Примечание к заказу");
        binding.edtDescription.setFloatingLabel(MaterialEditText.FLOATING_LABEL_NORMAL);
    }

    private void initRvAdditional() {
        adapter.setListener(this);
        binding.rvAdditional.setAdapter(adapter);

        binding.rvAdditional.setNestedScrollingEnabled(false);
        binding.rvAdditional.setLayoutManager(manager);
    }

    private void initBtnOrderIt() {
        binding.btnOrderIt.setOnClickListener(view -> {
            presenter.doOrder(binding.edtDescription.getText().toString());
        });

    }

    private void initBtnError() {
        binding.btnError.setOnClickListener(view -> {
            presenter.doOrder(binding.edtDescription.getText().toString());
        });
    }

    @Override
    public void updateRvAdditionalList(List<ProductItem> additionalProdcuts) {
        binding.progressAdditional.setVisibility(View.GONE);
        binding.rvAdditional.setVisibility(View.VISIBLE);

        binding.btnOrderIt.setClickable(true);

        adapter.setProductItemList(additionalProdcuts);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("price", presenter.getPrice());
        outState.putString("orderlist", binding.txtOrderlist.getText().toString());

    }

    public Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(Contracts.NavigationConstant.TAB_BASKET);
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
        presenter.onDestroy();
        disposableBag.clear();
    }
}
