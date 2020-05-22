package com.timsterj.ronin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
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

    private AppCompatImageButton btnClose;
    private TextView txtTitle;

    String title;

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
                view = inflater.inflate(R.layout.fragment_home_tutorial, container, false);
                title = getString(R.string.tab_basket_name);
                break;
            case Contracts.NavigationConstant.TAB_FAVORITE:
                view = inflater.inflate(R.layout.fragment_home_tutorial, container, false);
                title = getString(R.string.tab_favorite_name);
                break;
            case Contracts.NavigationConstant.TAB_SEARCH:
                view = inflater.inflate(R.layout.fragment_home_tutorial, container, false);
                title = getString(R.string.tab_search_name);
                break;
            default:
                new IllegalArgumentException();
        }

        init(view);

        return view;
    }

    private void init(View view) {

        btnClose = view.findViewById(R.id.img_close);
        txtTitle = view.findViewById(R.id.txt_title);

        initBtnClose();
        initTitle();
    }

    private void initBtnClose() {
        btnClose.setOnClickListener(view -> {
            onBackPressed();
        });

    }

    private void initTitle() {
        txtTitle.setText(title);

    }

    public Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(ROOT_TAB);
    }

    public Router getRouter() {
        return getCicerone().getRouter();
    }

    @Override
    public void onBackPressed() {
        getRouter().exit();

    }
}
