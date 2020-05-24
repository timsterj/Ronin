package com.timsterj.ronin.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.LaunchContract;
import com.timsterj.ronin.databinding.ActivityLaunchBinding;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.presenters.LaunchPresenter;

import javax.inject.Inject;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class LaunchActivity extends MvpAppCompatActivity implements LaunchContract.View {

    @Inject
    SharedPreferences sharedPreferences;

    @InjectPresenter
    LaunchPresenter presenter;

    private ActivityLaunchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLaunchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intiButtonRefresh();

        InjectHelper.plusLaunchComponent().inject(this);

        presenter.init();
        presenter.startPrepareSession();
    }

    private void intiButtonRefresh() {

        binding.imgRefresh.setOnClickListener(view -> {
            showProgressBar();
            presenter.startPrepareSession();
        });
    }

    @Override
    public void nextActivity() {
        String reg_type = sharedPreferences.getString(Contracts.PreferencesConstant.REGISTRATION_TYPE, Contracts.AuthorizationConstant.REGISTRATION_TYPE_NONE);

        if (!reg_type.equals(Contracts.AuthorizationConstant.REGISTRATION_TYPE_NONE)) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, AuthorizationActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.imgRefresh.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorInternet() {
        boolean firstRun = sharedPreferences.getBoolean(Contracts.PreferencesConstant.FIRST_RUN, true);
        if (firstRun) {

            binding.imgLogo.setVisibility(View.GONE);
            binding.imgErrorInternet.setVisibility(View.VISIBLE);
            binding.textViewTitleErrorInternet.setVisibility(View.VISIBLE);
            binding.textViewDescription.setVisibility(View.VISIBLE);
            hideProgressBar();
            binding.imgRefresh.setVisibility(View.VISIBLE);
        } else {
            nextActivity();
        }
    }

    @Override
    protected void onDestroy() {
        InjectHelper.clearLaunchComponent();
        super.onDestroy();
    }
}
