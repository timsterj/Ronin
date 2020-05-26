package com.timsterj.ronin.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.timsterj.ronin.R;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.listeners.OnBackPressed;
import com.timsterj.ronin.navigation.LocalCiceroneHolder;

import javax.inject.Inject;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

public class TutorialFragment extends Fragment implements OnBackPressed {

    private static final String EXTRA_NAME = "extra_name";
    private String ROOT_TAB = "";

    private Toolbar toolbar;
    private Button btnOk;

    String title;


    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    LocalCiceroneHolder ciceroneHolder;

    public static TutorialFragment getNewInstance(String name, String rootTab) {
        TutorialFragment fragment = new TutorialFragment();

        Bundle arguments = new Bundle();
        arguments.putString("root_tab", rootTab);
        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectHelper.plusHomeComponent().inject(this);

        ROOT_TAB = getArguments().getString("root_tab");

        if (savedInstanceState != null) {

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;

        switch (ROOT_TAB) {
            case Contracts.NavigationConstant.TAB_HOME:
                view = inflater.inflate(R.layout.fragment_home_tutorial, container, false);
                title = getString(R.string.tab_home_name);
                break;
            case Contracts.NavigationConstant.TAB_BASKET:
                view = inflater.inflate(R.layout.fragment_basket_tutorial, container, false);
                title = getString(R.string.tab_basket_name);
                break;
            case Contracts.NavigationConstant.TAB_FAVORITE:
                view = inflater.inflate(R.layout.fragment_favorite_tutorial, container, false);
                title = getString(R.string.tab_favorite_name);
                break;
            case Contracts.NavigationConstant.TAB_SEARCH:
                view = inflater.inflate(R.layout.fragment_search_tutorial, container, false);
                title = getString(R.string.tab_search_name);
                break;
            default:
                new IllegalArgumentException();
        }

        init(view);

        return view;
    }

    private void init(View view) {

        toolbar = view.findViewById(R.id.toolbar_tutorial);
        btnOk = view.findViewById(R.id.btn_ok);

        initBtnClose();
        initBtnOk();
        initTitle();
    }

    private void initBtnClose() {
        Menu menu = toolbar.getMenu();
        MenuItem closeItem = menu.getItem(0);

        closeItem.setOnMenuItemClickListener(item -> {
            onBackPressed();
            return false;
        });

    }

    private void initTitle() {
        toolbar.setTitle(title);

    }

    private void initBtnOk() {
        btnOk.setOnClickListener(view->{
            onBackPressed();
        });
    }

    public Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(ROOT_TAB);
    }

    public Router getRouter() {
        return getCicerone().getRouter();
    }

    @Override
    public void onBackPressed() {
        switch (ROOT_TAB) {
            case Contracts.NavigationConstant.TAB_HOME:
                sharedPreferences.edit().putBoolean(Contracts.PreferencesConstant.HOME_TAB_FIRST_RUN, false).apply();

                break;
            case Contracts.NavigationConstant.TAB_BASKET:
                sharedPreferences.edit().putBoolean(Contracts.PreferencesConstant.BASKET_TAB_FIRST_RUN, false).apply();

                break;
            case Contracts.NavigationConstant.TAB_FAVORITE:
                sharedPreferences.edit().putBoolean(Contracts.PreferencesConstant.FAVORITE_TAB_FIRST_RUN, false).apply();

                break;
            case Contracts.NavigationConstant.TAB_SEARCH:
                sharedPreferences.edit().putBoolean(Contracts.PreferencesConstant.SEARCH_TAB_FIRST_RUN, false).apply();
                break;
            default:
                new IllegalArgumentException();
        }
        getRouter().exit();
    }
}
