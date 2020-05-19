package com.timsterj.ronin.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.timsterj.ronin.App;
import com.timsterj.ronin.R;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.listeners.OnBackPressed;
import com.timsterj.ronin.navigation.Screens;

import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

public class AuthorizationActivity extends AppCompatActivity {

    @Inject
    Router router;
    @Inject
    NavigatorHolder navigatorHolder;

    Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getINSTANCE().appComponent.inject(this);
        setContentView(R.layout.fragment_tab_container);

        if (savedInstanceState == null) {
            startDefaultAuhorizationFragment();
        }
    }

    private void startDefaultAuhorizationFragment(){
        router.replaceScreen(new Screens.AuthorizationFlow.AuthorizationScreen(Contracts.NavigationConstant.AUTHORIZATION));
    }

    @Override
    public void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(getNavigator());
    }

    @Override
    public void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    private Navigator getNavigator() {
        if (navigator == null) {
            navigator = new SupportAppNavigator(this, getSupportFragmentManager(), R.id.ftb_container);
        }
        return navigator;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment currentFragment = null;
        for (Fragment fragment : fragments) {
            if (fragment.isVisible()) {
                currentFragment = fragment;
            }
        }

        if (currentFragment != null) {
            ((OnBackPressed) currentFragment).onBackPressed();
        } else {
            onBackPressed();
        }

    }
}
