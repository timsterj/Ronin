package com.timsterj.ronin.fragments;

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

import com.timsterj.ronin.contracts.mvp.OrderFavoriteContract;
import com.timsterj.ronin.databinding.FragmentOrderFavoriteBinding;
import com.timsterj.ronin.presenters.OrderFavoritePresenter;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class OrderFavoriteFragment extends MvpAppCompatFragment implements OrderFavoriteContract.View {

    private static final String EXTRA_NAME = "extra_name";

    private FragmentOrderFavoriteBinding binding;

    private LinearLayoutManager manager;
    private ItemTouchHelper.SimpleCallback itemTouchHelperUtil;

    @InjectPresenter
    OrderFavoritePresenter presenter;

    public static OrderFavoriteFragment getNewInstance(String name) {
        OrderFavoriteFragment fragment = new OrderFavoriteFragment();

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
        binding = FragmentOrderFavoriteBinding.inflate(getLayoutInflater());
        presenter.init();

        manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        if (savedInstanceState != null) {
            Parcelable parcelable = savedInstanceState.getParcelable("rv_favorite_orders");

            manager.onRestoreInstanceState(parcelable);
        }

        init();
    }


    private void init() {
        initSubscribers();
        initRvFavoriteOrders();
    }

    private void initSubscribers() {

    }

    private void initRvFavoriteOrders() {

    }

}
