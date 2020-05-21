package com.timsterj.ronin.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.timsterj.ronin.R;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.HomeContract;
import com.timsterj.ronin.databinding.ActivityHomeBinding;
import com.timsterj.ronin.listeners.NotificationListener;
import com.timsterj.ronin.listeners.OnBackPressed;
import com.timsterj.ronin.presenters.HomePresenter;
import com.timsterj.ronin.services.LastOrderStatusWorker;

import java.util.List;
import java.util.concurrent.TimeUnit;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class HomeActivity extends MvpAppCompatActivity implements HomeContract.View, NotificationListener {

    private ActivityHomeBinding binding;

    private int currentPosition = 0;

    private int tabHomeNotificationCount = 0;
    private int tabBasketNotificationCount = 0;
    private int tabFavoriteNotificationCount = 0;
    private int tabSearchNotificationCount = 0;

    private static Context mContext;

    @InjectPresenter
    HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mContext = getApplicationContext();

        initBottomNavigationBar();
        initPresenter();

        if (savedInstanceState == null) {
            binding.bottomNavigationBar.setCurrentItem(0, true);
        }
    }

    public void initBottomNavigationBar() {
        binding.bottomNavigationBar.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        binding.bottomNavigationBar.setAccentColor(getResources().getColor(R.color.colorAccent));

        binding.bottomNavigationBar.addItem(new AHBottomNavigationItem(getResources().getString(R.string.tab_home_name), R.drawable.ic_home_red_24dp));
        binding.bottomNavigationBar.addItem(new AHBottomNavigationItem(getResources().getString(R.string.tab_basket_name), R.drawable.ic_shopping_basket_red_24dp));
        binding.bottomNavigationBar.addItem(new AHBottomNavigationItem(getResources().getString(R.string.tab_favorite_name), R.drawable.ic_favorite_red_24dp));
        binding.bottomNavigationBar.addItem(new AHBottomNavigationItem(getResources().getString(R.string.tab_search_name), R.drawable.ic_search_red_24dp));

        binding.bottomNavigationBar.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position) {
                    case 0:
                        tabHomeNotificationCount = 0;
                        updateTabNotification();
                        selectTab(Contracts.NavigationConstant.TAB_HOME, position);
                        break;
                    case 1:
                        tabBasketNotificationCount = 0;
                        updateTabNotification();
                        selectTab(Contracts.NavigationConstant.TAB_BASKET, position);
                        break;
                    case 2:
                        tabFavoriteNotificationCount = 0;
                        updateTabNotification();
                        selectTab(Contracts.NavigationConstant.TAB_FAVORITE, position);
                        break;
                    case 3:
                        tabSearchNotificationCount = 0;
                        updateTabNotification();
                        selectTab(Contracts.NavigationConstant.TAB_SEARCH, position);
                        break;
                }
                binding.bottomNavigationBar.setCurrentItem(position, false);

                return false;
            }
        });
    }

    public void initPresenter() {
        presenter.init();
    }

    private void selectTab(String tab, int nextPosition) {
        presenter.selectTab(tab, nextPosition, getSupportFragmentManager());

        currentPosition = nextPosition;
    }

    @Override
    public void onError(int errorCode) {

        switch (errorCode) {
            case Contracts.ErrorConstant.ERROR_NULL:
                Toast.makeText(this, getResources().getString(R.string.error_null), Toast.LENGTH_SHORT).show();
                break;

            case Contracts.ErrorConstant.ERROR_EMPTY:
                Toast.makeText(this, getResources().getString(R.string.error_empty), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void updateTabNotification() {
        if (tabHomeNotificationCount > 0) {
            binding.bottomNavigationBar.setNotification(String.valueOf(tabHomeNotificationCount), 0);
        } else {
            binding.bottomNavigationBar.setNotification("", 0);
        }
        if (tabBasketNotificationCount > 0) {
            binding.bottomNavigationBar.setNotification(String.valueOf(tabBasketNotificationCount), 1);
        } else {
            binding.bottomNavigationBar.setNotification("", 1);
        }
        if (tabFavoriteNotificationCount> 0) {
            binding.bottomNavigationBar.setNotification(String.valueOf(tabFavoriteNotificationCount), 2);
        } else {
            binding.bottomNavigationBar.setNotification("", 2);
        }
        if (tabSearchNotificationCount> 0) {
            binding.bottomNavigationBar.setNotification(String.valueOf(tabSearchNotificationCount), 3);
        } else {
            binding.bottomNavigationBar.setNotification("", 3);
        }

    }

    @Override
    public void incTabNotification(int position) {
        switch (position) {
            case 0:
                tabHomeNotificationCount++;
                break;
            case 1:
                tabBasketNotificationCount++;
                break;
            case 2:
                tabFavoriteNotificationCount++;
                break;
            case 3:
                tabSearchNotificationCount++;
                break;
        }
    }

    public static void startWorker(){
        OneTimeWorkRequest lastOrderStatusRequest = new OneTimeWorkRequest.Builder(LastOrderStatusWorker.class)
                .setInitialDelay(15, TimeUnit.SECONDS)
                .build();

        WorkManager.getInstance(mContext).enqueue(lastOrderStatusRequest);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentManager cFm = null;

        List<Fragment> fragments = fm.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f.isVisible()) {
                    cFm = f.getChildFragmentManager();
                    break;
                }
            }
        }

        Fragment fragment = null;
        List<Fragment> childFragments = cFm.getFragments();
        if (fragments != null) {
            for (Fragment f : childFragments) {
                if (f.isVisible()) {
                    fragment = f;
                    break;
                }
            }
        }

        if (fragment != null
                && fragment instanceof OnBackPressed) {
            ((OnBackPressed) fragment).onBackPressed();
        } else {
            super.onBackPressed();
        }

    }
}
