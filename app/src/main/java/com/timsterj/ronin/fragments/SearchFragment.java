package com.timsterj.ronin.fragments;

import android.content.SharedPreferences;
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

import com.timsterj.ronin.R;
import com.timsterj.ronin.adapters.ProductAdapter;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.SearchContract;
import com.timsterj.ronin.data.model.Favorite;
import com.timsterj.ronin.data.model.Order;
import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.databinding.FragmentSearchBinding;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.listeners.IClickProductListener;
import com.timsterj.ronin.listeners.ISwipedItemSearchListener;
import com.timsterj.ronin.listeners.NotificationListener;
import com.timsterj.ronin.listeners.OnBackPressed;
import com.timsterj.ronin.model.Category;
import com.timsterj.ronin.model.ProductItem;
import com.timsterj.ronin.navigation.LocalCiceroneHolder;
import com.timsterj.ronin.navigation.Screens;
import com.timsterj.ronin.presenters.SearchPresenter;
import com.timsterj.ronin.utils.ItemButtonAction;
import com.timsterj.ronin.utils.ItemSwipeCallback;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

public class SearchFragment extends MvpAppCompatFragment implements SearchContract.View,
        OnBackPressed,
        ISwipedItemSearchListener,
        IClickProductListener {

    private static final String EXTRA_NAME = "extra_name";

    private CompositeDisposable disposableBag = new CompositeDisposable();

    private FragmentSearchBinding binding;

    private List<Category> categories = new ArrayList<>();

    private ItemTouchHelper.SimpleCallback itemTouchHelperUtil;
    private boolean isFromScroll = false;
    private boolean isScrolling = false;

    private LinearLayoutManager manager;


    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    ProductAdapter adapter;
    @Inject
    LocalCiceroneHolder ciceroneHolder;

    @InjectPresenter
    SearchPresenter presenter;

    public static SearchFragment getNewInstance(String name) {
        SearchFragment fragment = new SearchFragment();

        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectHelper.plusHomeComponent().inject(this);
        binding = FragmentSearchBinding.inflate(getLayoutInflater());
        presenter.init();

        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        if (savedInstanceState != null) {
            categories = Session.getINSTANCE().getCategories().getValue();
            addTabs();

            int position = savedInstanceState.getInt("current_position_tab");
            binding.tabLayoutCategories.selectTab(binding.tabLayoutCategories.getTabAt(position));

            Parcelable parcelable = savedInstanceState.getParcelable("rv_products");
            manager.onRestoreInstanceState(parcelable);
        }


        init();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    private void init() {
        showTutorial();

        initSubscribers();
        initTabLayout();
        initSearchView();
        initRvProducts();

    }


    private void showTutorial() {
        boolean firstRun = sharedPreferences.getBoolean(Contracts.PreferencesConstant.SEARCH_TAB_FIRST_RUN, true);

        if (firstRun) {
            getRouter().navigateTo(new Screens.TurorialScreen(Contracts.NavigationConstant.TUTORIAL, Contracts.NavigationConstant.TAB_SEARCH));
            sharedPreferences.edit().putBoolean(Contracts.PreferencesConstant.SEARCH_TAB_FIRST_RUN, false).apply();
        }

    }

    private void initSubscribers() {
        disposableBag.add(
                Session.getINSTANCE().getProducts()
                        .subscribe(value -> {
                            updateRvProductList(value);
                        })
        );

        disposableBag.add(
                Session.getINSTANCE().getCategories()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(categories -> {
                            this.categories = categories;
                            addTabs();
                        })
        );

    }

    private void initSearchView() {
        binding.searchView.setHint("Поиск");

        binding.searchView.setMenuItem(
                binding.searchToolbar.getMenu().findItem(R.id.action_search)
        );

        binding.searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }

        });

        binding.searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                binding.tabLayoutCategories.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                binding.tabLayoutCategories.setVisibility(View.VISIBLE);
            }
        });

    }

    private void initRvProducts() {
        adapter.setListener(this);

        itemTouchHelperUtil = new ItemSwipeCallback(getContext(), binding.rvProducts, 250) {
            @Override
            public void instantiateActionButton(RecyclerView.ViewHolder viewHolder, List<ItemButtonAction> buffer) {
                buffer.add(new ItemButtonAction(
                        getContext(),
                        R.drawable.ic_favorite_white_24dp,
                        Color.parseColor("#d60402"),
                        pos -> {
                            List<ProductItem> productItems = getCurrentAdapterData();

                            ProductItem product = productItems.get(pos);

                            addFavorite(product);

                        }
                ));

                buffer.add(new ItemButtonAction(
                        getContext(),
                        R.drawable.ic_shopping_basket_white_24dp,
                        Color.GREEN,
                        pos -> {
                            List<ProductItem> productItems = getCurrentAdapterData();

                            ProductItem product = productItems.get(pos);

                            addProductInOrderList(product);

                        }
                ));

            }
        };

        binding.rvProducts.setAdapter(adapter);

        binding.rvProducts.setLayoutManager(manager);

        binding.rvProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = true;

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onScrolled(recyclerView, recyclerView.getScrollX(), recyclerView.getScrollY());
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = manager.findLastVisibleItemPosition();

                for (int i = 0; i < categories.size(); i++) {
                    if (position > categories.get(i).getPosition()) {
                        isScrolling = false;
                        isFromScroll = true;
                        binding.tabLayoutCategories.getTabAt(i).select();
                    }
                }

                isScrolling = false;
                isFromScroll = false;
            }
        });
    }

    private void initTabLayout() {

        binding.tabLayoutCategories.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (!isScrolling && !isFromScroll) {
                    int position = categories.get(tab.getPosition()).getPosition();
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) binding.rvProducts.getLayoutManager();
                    linearLayoutManager.scrollToPositionWithOffset(
                            position,
                            0
                    );

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }

    private void addTabs() {
        binding.tabLayoutCategories.removeAllTabs();

        for (int i = 0; i < categories.size(); i++) {
            binding.tabLayoutCategories.addTab(
                    binding.tabLayoutCategories.newTab().setText(
                            categories.get(i).getName()
                    )
            );
        }
    }

    @Override
    public void onProductClick(Product product) {
        Bundle arguments = new Bundle();

        arguments.putString("root_tab", Contracts.NavigationConstant.TAB_SEARCH);
        arguments.putString("product_id", product.getId());
        arguments.putString("product_url", product.getImgUrl());
        arguments.putString("product_name", product.getName());
        arguments.putInt("product_price", product.getPrice());

        getRouter().navigateTo(new Screens.ProductInfoScreen(Contracts.NavigationConstant.PRODUCT_INFO, arguments));
    }

    public void addFavorite(ProductItem productItem) {
        List<Favorite> favorites = Session.getINSTANCE().getFavoriteList().getValue();
        Favorite favorite = null;

        if (favorites.isEmpty()) {
            favorite = new Favorite();
            favorites.add(favorite);
        } else {
            favorite = favorites.get(favorites.size() - 1);
        }

        if (favorite.getProductList().contains(productItem)) {
            Snackbar.make(binding.rvProducts, R.string.has_been_added_to_favorites_yet, Snackbar.LENGTH_SHORT).show();
        } else {
            favorite.getProductList().add(productItem);
            presenter.addFavorite(favorite);
            ((NotificationListener) getActivity()).incTabNotification(2); // Обновляю поля
            ((NotificationListener) getActivity()).updateTabNotification();
        }

        Session.getINSTANCE().getFavoriteList().onNext(favorites);
        adapter.notifyDataSetChanged();
    }

    public void addProductInOrderList(ProductItem productItem) {
        List<Order> orderList = Session.getINSTANCE().getOrderList().getValue();
        Order order = null;
        if (orderList.isEmpty()) {
            order = new Order();
            orderList.add(order);
        } else {
            order = orderList.get(orderList.size() - 1);
        }

        if (order.getProductItems().contains(productItem.getProduct())) {
            Snackbar.make(binding.rvProducts, R.string.has_been_added_to_orderlist_yet, Snackbar.LENGTH_SHORT).show();
        } else {
            order.getProductItems().add(productItem.getProduct());
            presenter.addOrderList(order);
            ((NotificationListener) getActivity()).incTabNotification(1);
            ((NotificationListener) getActivity()).updateTabNotification();
        }

        Session.getINSTANCE().getOrderList().onNext(orderList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public List<ProductItem> getCurrentAdapterData() {
        return adapter.getProductList();
    }

    @Override
    public void updateRvProductList(List<ProductItem> productItems) {
        adapter.setProductList(productItems);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("current_position_tab", binding.tabLayoutCategories.getSelectedTabPosition());
        outState.putParcelable("rv_products", binding.rvProducts.getLayoutManager().onSaveInstanceState());
    }

    public Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(Contracts.NavigationConstant.TAB_SEARCH);
    }

    public Router getRouter() {
        return getCicerone().getRouter();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableBag.clear();
    }

    @Override
    public void onBackPressed() {
        if (binding.searchView.isSearchOpen()) {
            binding.searchView.closeSearch();
        } else {
            getRouter().exit();
        }


    }
}
