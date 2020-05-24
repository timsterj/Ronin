package com.timsterj.ronin.fragments;

import android.os.Bundle;
import android.os.Handler;
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

import com.timsterj.ronin.adapters.OrderHistoryAdapter;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.OrderHistoryContract;
import com.timsterj.ronin.data.model.OrderDone;
import com.timsterj.ronin.databinding.FragmentOrderHistoryBinding;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.listeners.IClickProductListener;
import com.timsterj.ronin.listeners.OnBackPressed;
import com.timsterj.ronin.navigation.LocalCiceroneHolder;
import com.timsterj.ronin.navigation.Screens;
import com.timsterj.ronin.presenters.OrderHistoryPresenter;
import com.timsterj.ronin.services.LastOrderStatusWorker;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

public class OrderHistoryFragment extends MvpAppCompatFragment implements OrderHistoryContract.View, IClickProductListener<OrderDone>, OnBackPressed {

    private static final String EXTRA_NAME = "extra_name";

    private CompositeDisposable disposableBag = new CompositeDisposable();

    private FragmentOrderHistoryBinding binding;

    private LinearLayoutManager manager;

    @Inject
    LocalCiceroneHolder ciceroneHolder;
    @Inject
    OrderHistoryAdapter adapter;

    @InjectPresenter
    OrderHistoryPresenter presenter;

    public static OrderHistoryFragment getNewInstance(String name) {
        OrderHistoryFragment fragment = new OrderHistoryFragment();

        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectHelper.plusHomeComponent().inject(this);
        binding = FragmentOrderHistoryBinding.inflate(getLayoutInflater());
        presenter.init();

        if (savedInstanceState != null) {

        }

        init();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }


    private void init() {
        initRvOrderHistory();
        initSubscribers();
    }

    private void initRvOrderHistory() {
        manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, true);

        adapter.setListener(this);

        binding.rvOrderHistory.setAdapter(adapter);

        binding.rvOrderHistory.setLayoutManager(manager);
    }

    private void initSubscribers() {
        disposableBag.add(
                Session.getINSTANCE().getOrderDoneList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            if (!value.isEmpty()) {
                                adapter.setOrderDoneList(value);
                                hideError();
                                manager.scrollToPositionWithOffset(value.size()-1, 0);

                            } else {
                                showError();
                            }

                        },  t->{

                        }, ()->{

                        })
        );

    }

    @Override
    public void showError() {
        binding.rvOrderHistory.setVisibility(View.GONE);
        binding.txtErrorEmptyOrderHistory.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        binding.rvOrderHistory.setVisibility(View.VISIBLE);
        binding.txtErrorEmptyOrderHistory.setVisibility(View.GONE);
    }

    @Override
    public void onProductClick(OrderDone data) {
        List<OrderDone> orderDones = Session.getINSTANCE().getOrderDoneList().getValue();

        int index = orderDones.indexOf(data);

        getRouter().navigateTo(new Screens.OrderInfoScreen(Contracts.NavigationConstant.ORDER_INFO, index, Contracts.NavigationConstant.TAB_BASKET));
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
        disposableBag.clear();
        presenter.onDestroy();
    }
}
