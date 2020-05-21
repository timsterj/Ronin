package com.timsterj.ronin.fragments;

import android.os.Bundle;
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
import com.timsterj.ronin.databinding.FragmentOrderHistoryBinding;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.listeners.OnBackPressed;
import com.timsterj.ronin.navigation.LocalCiceroneHolder;
import com.timsterj.ronin.presenters.OrderHistoryPresenter;
import com.timsterj.ronin.services.LastOrderStatusWorker;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

public class OrderHistoryFragment extends MvpAppCompatFragment implements OrderHistoryContract.View, OnBackPressed {

    private static final String EXTRA_NAME = "extra_name";

    private CompositeDisposable disposableBag = new CompositeDisposable();

    private FragmentOrderHistoryBinding binding;

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
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, true);

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
                            } else {
                                showError();
                            }

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
