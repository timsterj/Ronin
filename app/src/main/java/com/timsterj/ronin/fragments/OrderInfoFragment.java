package com.timsterj.ronin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.snackbar.Snackbar;
import com.timsterj.ronin.R;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.OrderInfoContract;
import com.timsterj.ronin.data.model.Favorite;
import com.timsterj.ronin.data.model.Order;
import com.timsterj.ronin.data.model.OrderDone;
import com.timsterj.ronin.databinding.FragmentOrderInfoBinding;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.listeners.NotificationListener;
import com.timsterj.ronin.listeners.OnBackPressed;
import com.timsterj.ronin.model.ProductItem;
import com.timsterj.ronin.navigation.LocalCiceroneHolder;
import com.timsterj.ronin.navigation.Screens;
import com.timsterj.ronin.presenters.OrderInfoPresenter;
import com.timsterj.ronin.services.LastOrderStatusWorker;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

public class OrderInfoFragment extends MvpAppCompatFragment implements OrderInfoContract.View, OnBackPressed {

    private static final String EXTRA_NAME = "extra_name";

    private FragmentOrderInfoBinding binding;
    private CompositeDisposable disposableBag = new CompositeDisposable();
    private String ROOT_TAB = "";
    int index;

    @Inject
    LocalCiceroneHolder ciceroneHolder;
    @InjectPresenter
    OrderInfoPresenter presenter;

    public static OrderInfoFragment getNewInstance(String name, int index, String rootTab) {
        OrderInfoFragment fragment = new OrderInfoFragment();

        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        arguments.putInt("index", index);
        arguments.putString("root_tab", rootTab);
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
        binding = FragmentOrderInfoBinding.inflate(getLayoutInflater());

        if (savedInstanceState != null) {

        }

        init();
    }

    private void init() {
        index = getArguments().getInt("index");
        ROOT_TAB = getArguments().getString("root_tab");

        disposableBag.add(
                Session.getINSTANCE().getOrderDoneList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            presenter.initOrderDone(index);
                        })
        );

    }

    @Override
    public void showOrderDoneInfo(String status, String orderID, String clientInfo, String date, String location, String orderlist, int price) {
        if (!status.equals("")) {
            binding.txtOrderStatus.setText("Статус заказа: " + status);
        }
        binding.toolbarOrderInfo.setTitle(status);
        binding.txtOrderId.setText("ID заказа: " + orderID);
        binding.txtDate.setText("Время: " + date);
        binding.txtClientInfo.setText("Клиент: " + clientInfo);
        binding.txtLocation.setText("Адрес доставки: " + location);
        binding.txtOrderlist.setText(orderlist);

        if (!status.equals("Новый")) {
            binding.txtHelper.setVisibility(View.GONE);
        }

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
    public void onBackPressed() {
        getRouter().backTo(new Screens.BottomNavigationFlow.BasketScreen(Contracts.NavigationConstant.TAB_BASKET));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableBag.clear();
        presenter.onDestroy();
    }

}
