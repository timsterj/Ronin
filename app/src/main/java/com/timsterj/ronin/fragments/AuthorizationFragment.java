package com.timsterj.ronin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.timsterj.ronin.App;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.AuthorizationContract;
import com.timsterj.ronin.databinding.FragmentAuthorizationBinding;
import com.timsterj.ronin.listeners.OnBackPressed;
import com.timsterj.ronin.navigation.Screens;
import com.timsterj.ronin.presenters.AuthorizationPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;

import javax.inject.Inject;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Router;

public class AuthorizationFragment extends MvpAppCompatFragment implements AuthorizationContract.View, OnBackPressed {

    private static final String EXTRA_NAME = "extra_name";

    private FragmentAuthorizationBinding binding;
    private String phoneNumber;

    @Inject
    Router router;

    @InjectPresenter
    AuthorizationPresenter presenter;

    public static AuthorizationFragment getNewInstance(String name) {
        AuthorizationFragment fragment = new AuthorizationFragment();

        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getINSTANCE().appComponent.inject(this);
        binding = FragmentAuthorizationBinding.inflate(getLayoutInflater());
        presenter.init();

        if (savedInstanceState != null) {
            String phoneNumber = savedInstanceState.getString("phone_number");

            binding.edtPhoneNumber.setText(phoneNumber);
        }

        init();
    }

    private void init() {
        initMET();
        initButtonSignIn();

    }

    private void initMET() {
        binding.edtPhoneNumber.setFloatingLabelText("Номер телефона");
        binding.edtPhoneNumber.setFloatingLabel(MaterialEditText.FLOATING_LABEL_NORMAL);

    }


    @Override
    public void onError(int errorCode) {
        if (errorCode == Contracts.ErrorConstant.ERROR_NULL) {
            Toast.makeText(getContext(), Contracts.ErrorConstant.ERROR_NULL, Toast.LENGTH_SHORT).show();
        } else if (errorCode == Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR) {
            binding.edtPhoneNumber.setError(Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR_MESSAGE);
        } else if (errorCode == Contracts.ErrorAuthorization.PHONE_INVALID_VALIDATION) {
            binding.edtPhoneNumber.setError(Contracts.ErrorAuthorization.PHONE_INVALID_ERROR_MESSAGE);
        }

    }

    @Override
    public void onSuccess() {
        router.navigateTo(new Screens.AuthorizationFlow.SignUpScreen(Contracts.NavigationConstant.SIGN_UP_AUTHORIZATION, phoneNumber));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }


    private void initButtonSignIn() {
        binding.btnSignIn.setOnClickListener(view -> {
            phoneNumber = binding.edtPhoneNumber.getText().toString();

            presenter.goSignIn(phoneNumber);
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("phone_number", binding.edtPhoneNumber.getText().toString());
    }

    @Override
    public void onBackPressed() {
        router.exit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
