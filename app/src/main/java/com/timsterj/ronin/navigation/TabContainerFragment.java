package com.timsterj.ronin.navigation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.timsterj.ronin.R;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.helpers.InjectHelper;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.Screen;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;

public class TabContainerFragment extends Fragment {

    private static final String EXTRA_NAME = "tcf_extra_name";

    Navigator navigator;

    @Inject
    LocalCiceroneHolder localCiceroneHolder;
    @Inject
    SharedPreferences sharedPreferences;

    public static TabContainerFragment getNewInstance(String name) {
        TabContainerFragment fragment = new TabContainerFragment();

        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectHelper.plusHomeComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_container, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getContainerName().equals(Contracts.NavigationConstant.TAB_HOME)) {
            boolean firstRun = sharedPreferences.getBoolean(Contracts.PreferencesConstant.HOME_TAB_FIRST_RUN, true);

            if (firstRun) {
                getRouter().newRootChain(getCurrentTabFragmentScreen(),
                        new Screens.TurorialScreen(Contracts.NavigationConstant.TUTORIAL, getContainerName()));

                sharedPreferences.edit().putBoolean(Contracts.PreferencesConstant.HOME_TAB_FIRST_RUN, false).apply();
            } else {
                getRouter().newRootScreen(getCurrentTabFragmentScreen());
            }
        } else if (getContainerName().equals(Contracts.NavigationConstant.TAB_BASKET)) {
            boolean firstRun = sharedPreferences.getBoolean(Contracts.PreferencesConstant.BASKET_TAB_FIRST_RUN, true);

            if (firstRun) {
                getRouter().newRootChain(getCurrentTabFragmentScreen(),
                        new Screens.TurorialScreen(Contracts.NavigationConstant.TUTORIAL, getContainerName()));

                sharedPreferences.edit().putBoolean(Contracts.PreferencesConstant.BASKET_TAB_FIRST_RUN, false).apply();
            } else {
                getRouter().newRootScreen(getCurrentTabFragmentScreen());
            }
        } else if (getContainerName().equals(Contracts.NavigationConstant.TAB_FAVORITE)) {
            boolean firstRun = sharedPreferences.getBoolean(Contracts.PreferencesConstant.FAVORITE_TAB_FIRST_RUN, true);

            if (firstRun) {
                getRouter().newRootChain(getCurrentTabFragmentScreen(),
                        new Screens.TurorialScreen(Contracts.NavigationConstant.TUTORIAL, getContainerName()));
                sharedPreferences.edit().putBoolean(Contracts.PreferencesConstant.FAVORITE_TAB_FIRST_RUN, false).apply();
            } else {
                getRouter().newRootScreen(getCurrentTabFragmentScreen());
            }
        } else if (getContainerName().equals(Contracts.NavigationConstant.TAB_SEARCH)) {
            boolean firstRun = sharedPreferences.getBoolean(Contracts.PreferencesConstant.SEARCH_TAB_FIRST_RUN, true);

            if (firstRun) {
                getRouter().newRootChain(getCurrentTabFragmentScreen(),
                        new Screens.TurorialScreen(Contracts.NavigationConstant.TUTORIAL, getContainerName()));
                sharedPreferences.edit().putBoolean(Contracts.PreferencesConstant.SEARCH_TAB_FIRST_RUN, false).apply();
            } else {
                getRouter().newRootScreen(getCurrentTabFragmentScreen());
            }
        }

    }

    private Screen getCurrentTabFragmentScreen() {
        Screen screen = null;

        switch (getContainerName()) {

            case Contracts.NavigationConstant.TAB_HOME:
                screen = new Screens.BottomNavigationFlow.HomeScreen(getContainerName());
                break;
            case Contracts.NavigationConstant.TAB_BASKET:
                screen = new Screens.BottomNavigationFlow.BasketScreen(getContainerName());
                break;
            case Contracts.NavigationConstant.TAB_FAVORITE:
                screen = new Screens.BottomNavigationFlow.FavoriteScreen(getContainerName());
                break;
            case Contracts.NavigationConstant.TAB_SEARCH:
                screen = new Screens.BottomNavigationFlow.SearchScreen(getContainerName());
                break;
        }

        return screen;
    }

    @Override
    public void onResume() {
        super.onResume();
        getCicerone().getNavigatorHolder().setNavigator(getNavigator());
    }

    @Override
    public void onPause() {
        super.onPause();
        getCicerone().getNavigatorHolder().removeNavigator();
    }

    private String getContainerName() {
        return getArguments().getString(EXTRA_NAME);
    }

    private Cicerone<Router> getCicerone() {
        return localCiceroneHolder.getCicerone(getContainerName());
    }

    private Navigator getNavigator() {

        if (navigator == null) {
            navigator = new SupportAppNavigator(getActivity(), getChildFragmentManager(), R.id.ftb_container);
        }
        return navigator;
    }

    public Router getRouter() {
        return getCicerone().getRouter();
    }


}
