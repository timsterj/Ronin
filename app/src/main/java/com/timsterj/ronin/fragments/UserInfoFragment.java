package com.timsterj.ronin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.UserInfoContract;
import com.timsterj.ronin.databinding.FragmentUserInfoBinding;
import com.timsterj.ronin.helpers.InjectHelper;
import com.timsterj.ronin.listeners.OnBackPressed;
import com.timsterj.ronin.navigation.LocalCiceroneHolder;
import com.timsterj.ronin.presenters.UserInfoPresenter;
import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

public class UserInfoFragment extends MvpAppCompatFragment implements UserInfoContract.View, OnBackPressed {

    private static final String EXTRA_NAME = "extra_name";

    private FragmentUserInfoBinding binding;

    private CompositeDisposable disposableBag = new CompositeDisposable();

    @Inject
    LocalCiceroneHolder ciceroneHolder;
    @InjectPresenter
    UserInfoPresenter presenter;

    public static UserInfoFragment getNewInstance(String name) {
        UserInfoFragment fragment = new UserInfoFragment();

        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectHelper.plusHomeComponent().inject(this);
        binding = FragmentUserInfoBinding.inflate(getLayoutInflater());
        presenter.init();

        if (savedInstanceState != null) {

        }

        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    private void init() {
        initSubscribes();
        initBtnSaveChanges();
        initMET();
    }

    private void initSubscribes() {
        disposableBag.add(
                Session.getINSTANCE().getUser()
                        .subscribe(user -> {
                            userInfoPrepared(
                                    user.getPhoneNumber(),
                                    user.getName(),
                                    user.getStreet(),
                                    user.getHome(),
                                    user.getPod(),
                                    user.getEt(),
                                    user.getApart()
                            );
                        })
        );

    }

    private void initMET() {
        binding.edtName.setFloatingLabelText("Имя");
        binding.edtName.setFloatingLabel(MaterialEditText.FLOATING_LABEL_NORMAL);

        binding.edtPhoneNumber.setFloatingLabelText("Номер телефона");
        binding.edtPhoneNumber.setFloatingLabel(MaterialEditText.FLOATING_LABEL_NORMAL);

        binding.edtLocationStreet.setFloatingLabelText("Улица");
        binding.edtLocationStreet.setFloatingLabel(MaterialEditText.FLOATING_LABEL_NORMAL);

        binding.edtLocationHome.setFloatingLabelText("Дом");
        binding.edtLocationHome.setFloatingLabel(MaterialEditText.FLOATING_LABEL_NORMAL);

        binding.edtLocationPod.setFloatingLabelText("Подъезд");
        binding.edtLocationPod.setFloatingLabel(MaterialEditText.FLOATING_LABEL_NORMAL);

        binding.edtLocationEt.setFloatingLabelText("Этаж");
        binding.edtLocationEt.setFloatingLabel(MaterialEditText.FLOATING_LABEL_NORMAL);

        binding.edtLocationApart.setFloatingLabelText("Квартира");
        binding.edtLocationApart.setFloatingLabel(MaterialEditText.FLOATING_LABEL_NORMAL);

    }

    private void initBtnSaveChanges() {
        binding.btnSaveChanges.setOnClickListener(view -> {
            presenter.saveChanges(
                    binding.edtPhoneNumber.getText().toString(),
                    binding.edtName.getText().toString(),
                    binding.edtLocationStreet.getText().toString(),
                    binding.edtLocationHome.getText().toString(),
                    binding.edtLocationPod.getText().toString(),
                    binding.edtLocationEt.getText().toString(),
                    binding.edtLocationApart.getText().toString()
            );

        });

    }

    @Override
    public void userInfoPrepared(String phoneNumber, String name, String street, String home, String pod, String et, String apart) {
        binding.lnInfoFields.setVisibility(View.VISIBLE);

        binding.edtPhoneNumber.setText(phoneNumber);
        binding.edtName.setText(name);
        binding.edtLocationStreet.setText(street);
        binding.edtLocationHome.setText(home);
        binding.edtLocationPod.setText(pod);
        binding.edtLocationEt.setText(et);
        binding.edtLocationApart.setText(apart);
        binding.progressUserInfo.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        binding.btnSaveChanges.setVisibility(View.GONE);
        binding.progressSaveChanges.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.btnSaveChanges.setVisibility(View.VISIBLE);
        binding.progressSaveChanges.setVisibility(View.GONE);
    }

    @Override
    public void onError(int errorCode) {
        if (errorCode == Contracts.ErrorConstant.ERROR_NULL) {
            Toast.makeText(getContext(), Contracts.ErrorConstant.ERROR_NULL, Toast.LENGTH_SHORT).show();
        } else if (errorCode == Contracts.ErrorAuthorization.NAME_EMPTY_ERROR) {
            binding.edtName.setError(Contracts.ErrorAuthorization.NAME_EMPTY_ERROR_MESSAGE);
        } else if (errorCode == Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR) {
            binding.edtPhoneNumber.setError(Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR_MESSAGE);
        } else if (errorCode == Contracts.ErrorAuthorization.PHONE_INVALID_VALIDATION) {
            binding.edtPhoneNumber.setError(Contracts.ErrorAuthorization.PHONE_INVALID_ERROR_MESSAGE);
        } else if (errorCode == Contracts.ErrorAuthorization.LOCATION_STREET_EMPTY_ERROR) {
            binding.edtLocationStreet.setError(Contracts.ErrorAuthorization.LOCATION_STREET_EMPTY_ERROR_MESSAGE);
        } else if (errorCode == Contracts.ErrorAuthorization.LOCATION_HOME_EMPTY_ERROR) {
            binding.edtLocationHome.setError(Contracts.ErrorAuthorization.LOCATION_HOME_EMPTY_ERROR_MESSAGE);
        } else if (errorCode == Contracts.ErrorAuthorization.LOCATION_POD_EMPTY_ERROR) {
            binding.edtLocationPod.setError(Contracts.ErrorAuthorization.LOCATION_POD_EMPTY_ERROR_MESSAGE);
        } else if (errorCode == Contracts.ErrorAuthorization.LOCATION_ET_EMPTY_ERROR) {
            binding.edtLocationEt.setError(Contracts.ErrorAuthorization.LOCATION_ET_EMPTY_ERROR_MESSAGE);
        } else if (errorCode == Contracts.ErrorAuthorization.LOCATION_APART_EMPTY_ERROR) {
            binding.edtLocationApart.setError(Contracts.ErrorAuthorization.LOCATION_APART_EMPTY_ERROR_MESSAGE);
        }
    }

    @Override
    public void onSuccess() {
        hideLoading();

        Snackbar.make(binding.getRoot(), "Изменения сохранены :)", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(Contracts.NavigationConstant.TAB_HOME);
    }

    public Router getRouter() {
        return getCicerone().getRouter();
    }

    @Override
    public void onBackPressed() {
        getRouter().exit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }


}
