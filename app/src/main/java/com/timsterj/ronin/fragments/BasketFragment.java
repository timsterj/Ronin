package com.timsterj.ronin.fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.timsterj.ronin.R;
import com.timsterj.ronin.adapters.OrderListAdapter;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.BasketContract;
import com.timsterj.ronin.data.model.Order;
import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.databinding.FragmentBasketBinding;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.listeners.ICallbackBasketProductCountListener;
import com.timsterj.ronin.listeners.ISwipedItemBasketListener;
import com.timsterj.ronin.model.ProductItem;
import com.timsterj.ronin.navigation.LocalCiceroneHolder;
import com.timsterj.ronin.navigation.Screens;
import com.timsterj.ronin.presenters.BasketPresenter;
import com.timsterj.ronin.utils.ItemButtonAction;
import com.timsterj.ronin.utils.ItemSwipeCallback;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

public class BasketFragment extends MvpAppCompatFragment implements BasketContract.View
        , ICallbackBasketProductCountListener
        , ISwipedItemBasketListener {

    private static final String EXTRA_NAME = "extra_name";

    private CompositeDisposable disposableBag = new CompositeDisposable();

    private FragmentBasketBinding binding;

    private LinearLayoutManager manager;
    private ItemTouchHelper.SimpleCallback itemTouchHelperUtil;

    private List<Product> products;
    private int price = 0;

    @InjectPresenter
    BasketPresenter presenter;

    @Inject
    OrderListAdapter adapter;
    @Inject
    LocalCiceroneHolder ciceroneHolder;
    @Inject
    SharedPreferences sharedPreferences;

    public static BasketFragment getNewInstance(String name) {
        BasketFragment fragment = new BasketFragment();

        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectHelper.plusHomeComponent().inject(this);
        binding = FragmentBasketBinding.inflate(getLayoutInflater());
        presenter.init();

        manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        if (savedInstanceState != null) {

            Parcelable parcelable = savedInstanceState.getParcelable("rv_orderlist");
            manager.onRestoreInstanceState(parcelable);
        }


        init();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();

    }

    @Override
    public void showErrorEmptyOrderlist() {
        binding.rvOrderlist.setVisibility(View.GONE);
        binding.btnOrderIt.setVisibility(View.GONE);
        binding.txtErrorEmpty.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideErrorEmptyOrderlist() {
        binding.rvOrderlist.setVisibility(View.VISIBLE);
        binding.btnOrderIt.setVisibility(View.VISIBLE);
        binding.txtErrorEmpty.setVisibility(View.GONE);

    }

    private boolean checkOrderLimit() {
        long pTime = sharedPreferences.getLong(Contracts.PreferencesConstant.DAILY_ORDER_LIMIT, new Date().getTime());
        int pOrderSize = sharedPreferences.getInt(Contracts.PreferencesConstant.DAILY_ORDER_SIZE_LIMIT, 0);

        Calendar cCalendar = new GregorianCalendar(Locale.getDefault());
        cCalendar.setTime(new Date());

        Calendar pCalendar = new GregorianCalendar(Locale.getDefault());
        pCalendar.setTimeInMillis(pTime);

        Log.d(Contracts.TAG, "curDay: " + cCalendar.get(Calendar.DAY_OF_MONTH));
        Log.d(Contracts.TAG, "pDay: " + cCalendar.get(Calendar.DAY_OF_MONTH));
        Log.d(Contracts.TAG, "orderSize: " + pOrderSize);

        if (pOrderSize >= 10) {
            if (cCalendar.get(Calendar.DAY_OF_MONTH) < pCalendar.get(Calendar.DAY_OF_MONTH)) {
                if (cCalendar.get(Calendar.MONTH) > pCalendar.get(Calendar.MONTH)) {
                    sharedPreferences.edit().putLong(Contracts.PreferencesConstant.DAILY_ORDER_LIMIT, new Date().getTime()).apply();
                    sharedPreferences.edit().putInt(Contracts.PreferencesConstant.DAILY_ORDER_SIZE_LIMIT, 0).apply();
                    return true;
                } else if (cCalendar.get(Calendar.MONTH) == pCalendar.get(Calendar.MONTH)) {
                    Snackbar.make(binding.getRoot(), "В день по 10 заказов (0_0)", Snackbar.LENGTH_SHORT).show();

                    return false;
                }

            } else if (cCalendar.get(Calendar.DAY_OF_MONTH) == pCalendar.get(Calendar.DAY_OF_MONTH)) {
                Snackbar.make(binding.getRoot(), "В день по 10 заказов (0_0)", Snackbar.LENGTH_SHORT).show();

                return false;
            } else if (cCalendar.get(Calendar.DAY_OF_MONTH) > pCalendar.get(Calendar.DAY_OF_MONTH)) {
                sharedPreferences.edit().putLong(Contracts.PreferencesConstant.DAILY_ORDER_LIMIT, new Date().getTime()).apply();
                sharedPreferences.edit().putInt(Contracts.PreferencesConstant.DAILY_ORDER_SIZE_LIMIT, 0).apply();

                return true;
            }
        } else if (pOrderSize == 9) {
            sharedPreferences.edit().putLong(Contracts.PreferencesConstant.DAILY_ORDER_LIMIT, new Date().getTime()).apply();

            return true;
        } else {
            return true;
        }

        return false;
    }

    private void init() {

        initSubscribers();
        initRvOrderList();
        initBtnHistory();
        initBtnOrder();

    }

    private void initBtnHistory() {
        Menu menu = binding.toolbarBasket.getMenu();

        MenuItem orderHistoryItem = menu.getItem(0);

        orderHistoryItem.setOnMenuItemClickListener(item -> {
            getRouter().navigateTo(new Screens.OrderHistoryScreen(Contracts.NavigationConstant.ORDER_HISTORY));

            return false;
        });
    }

    private void initSubscribers() {
        disposableBag.add(
                Session.getINSTANCE().getOrderList()
                        .subscribe(orders -> {
                            if (!orders.isEmpty()) {
                                products = orders.get(orders.size() - 1).getProductItems();

                                if (products.isEmpty()) {
                                    showErrorEmptyOrderlist();
                                } else {
                                    hideErrorEmptyOrderlist();

                                }

                                adapter.setProductList(products);
                                updatePrice();

                            } else {
                                binding.toolbarBasket.setTitle(0 + " руб");
                                showErrorEmptyOrderlist();
                            }

                        })
        );

    }

    private void initRvOrderList() {
        adapter.setListener(this);

        itemTouchHelperUtil = new ItemSwipeCallback(getContext(), binding.rvOrderlist, 300) {
            @Override
            public void instantiateActionButton(RecyclerView.ViewHolder viewHolder, List<ItemButtonAction> buffer) {
                buffer.add(new ItemButtonAction(
                        getContext(),
                        R.drawable.ic_delete_white_24dp,
                        Color.RED,
                        pos -> {
                            binding.btnOrderIt.setVisibility(View.GONE);
                            deleteProductFromOrderlist(pos);
                        }
                ));

            }
        };

        binding.rvOrderlist.setAdapter(adapter);

        binding.rvOrderlist.setLayoutManager(manager);

        binding.rvOrderlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int lastPosition = manager.findLastCompletelyVisibleItemPosition();
                int firstPosition = manager.findFirstCompletelyVisibleItemPosition();

                if (firstPosition == 0) {
                    binding.btnOrderIt.animate()
                            .withStartAction(() -> {
                                binding.btnOrderIt.setVisibility(View.VISIBLE);
                                binding.btnOrderIt.setClickable(true);
                                binding.btnOrderIt.setAlpha(0);
                            })
                            .alpha(1)
                            .withEndAction(() -> binding.btnOrderIt.setAlpha(1))
                            .start();

                } else if (lastPosition == (products.size() - 1)) {
                    binding.btnOrderIt.animate()
                            .alpha(0)
                            .withEndAction(() -> {
                                binding.btnOrderIt.setClickable(false);
                                binding.btnOrderIt.setVisibility(View.GONE);
                            })
                            .start();

                }

            }
        });
    }

    private void initBtnOrder() {

    }

    @Override
    public void addOrderProductCount(int count, int position) {
        List<Order> orderList = Session.getINSTANCE().getOrderList().getValue();
        Order order = null;

        order = orderList.get(orderList.size() - 1);

        order.getProductItems().get(position).setCount(count);

        presenter.updateOrder(order);

        Session.getINSTANCE().getOrderList().onNext(orderList);
    }

    @Override
    public void deleteProductFromOrderlist(int position) {
        List<Order> orderList = Session.getINSTANCE().getOrderList().getValue();
        Order order = null;

        order = orderList.get(orderList.size() - 1);
        order.getProductItems().remove(position);

        presenter.updateOrder(order);

        Session.getINSTANCE().getOrderList().onNext(orderList);

        Snackbar.make(binding.rvOrderlist, R.string.message_removed, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void updatePrice() {
        List<Order> orderList = Session.getINSTANCE().getOrderList().getValue();
        Order order = null;

        order = orderList.get(orderList.size() - 1);

        int currentPrice = 0;
        for (Product product : products) {
            int productPrice = 0;
            if (product.getCount() != 0) {
                productPrice = product.getCount() * product.getPrice();
            } else {
                product.setCount(1);
                productPrice = product.getCount() * product.getPrice();
            }

            currentPrice += productPrice;
        }

        price = currentPrice;

        order.setScore(price);

        binding.btnOrderIt.setClickable(true);
        binding.btnOrderIt.setOnClickListener(view -> {
            if (checkOrderLimit()) {
                List<ProductItem> additionalList = Session.getINSTANCE().getAdditionalProducts().getValue();

                for (ProductItem product : additionalList) {
                    product.getProduct().setCount(0);
                }

                Session.getINSTANCE().getAdditionalProducts().onNext(additionalList);

                getRouter().navigateTo(new Screens.OrderScreen(Contracts.NavigationConstant.ORDER));
            }
        });

        binding.toolbarBasket.setTitle(price + " руб");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("rv_orderlist", binding.rvOrderlist.getLayoutManager().onSaveInstanceState());
    }

    public Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(Contracts.NavigationConstant.TAB_BASKET);
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
