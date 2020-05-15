package com.timsterj.ronin.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.timsterj.ronin.App;
import com.timsterj.ronin.activities.HomeActivity;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.SignUpContract;
import com.timsterj.ronin.databinding.FragmentSignUpBinding;
import com.timsterj.ronin.listeners.OnBackPressed;
import com.timsterj.ronin.presenters.SignUpPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Router;

public class SignUpFragment extends MvpAppCompatFragment implements SignUpContract.View, OnBackPressed {

    private static final String EXTRA_NAME = "extra_name";

    private CompositeDisposable disposableBag = new CompositeDisposable();

    private String phoneNumber = "";
    private FragmentSignUpBinding binding;

    @Inject
    Router router;
    @Inject
    SharedPreferences sharedPreferences;
    @InjectPresenter
    SignUpPresenter presenter;

    public static SignUpFragment getNewInstance(String name, String phoneNumber) {
        SignUpFragment fragment = new SignUpFragment();

        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        arguments.putString("phone_number", phoneNumber);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getINSTANCE().appComponent.inject(this);
        binding = FragmentSignUpBinding.inflate(getLayoutInflater());
        presenter.init();

        if (savedInstanceState != null) {
            String name = savedInstanceState.getString("name");
            String street = savedInstanceState.getString("street");
            String home = savedInstanceState.getString("home");
            String pod = savedInstanceState.getString("pod");
            String et = savedInstanceState.getString("et");
            String apart = savedInstanceState.getString("apart");

            binding.edtName.setText(name);
            binding.edtLocationStreet.setText(street);
            binding.edtLocationHome.setText(home);
            binding.edtLocationPod.setText(pod);
            binding.edtLocationEt.setText(et);
            binding.edtLocationApart.setText(apart);
        }

        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    private void init() {
        phoneNumber = getArguments().getString("phone_number");

        initMET();
        initButtonSignUp();
    }


    private void initMET() {
        binding.edtName.setFloatingLabelText("Имя");
        binding.edtName.setFloatingLabel(MaterialEditText.FLOATING_LABEL_NORMAL);

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

    private void initButtonSignUp() {
        binding.btnSignUp.setOnClickListener(v -> {
            presenter.signUp(
                    phoneNumber,
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
    public void showLoading() {
            binding.btnSignUp.setVisibility(View.GONE);
            binding.progressSignUp.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess() {
        sharedPreferences.edit()
                .putString(Contracts.PreferencesConstant.REGISTRATION_TYPE, Contracts.AuthorizationConstant.REGISTRATION_TYPE_USER)
                .apply();

        Intent homeActivity = new Intent(getContext(), HomeActivity.class);
        startActivity(homeActivity);
        getActivity().finish();

    }

    @Override
    public void onError(int errorCode) {
        if (errorCode == Contracts.ErrorConstant.ERROR_NULL) {
            Toast.makeText(getContext(), Contracts.ErrorConstant.ERROR_NULL, Toast.LENGTH_SHORT).show();
        } else if (errorCode == Contracts.ErrorAuthorization.NAME_EMPTY_ERROR) {
            binding.edtName.setError(Contracts.ErrorAuthorization.NAME_EMPTY_ERROR_MESSAGE);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("name", binding.edtName.getText().toString());
        outState.putString("street", binding.edtLocationStreet.getText().toString());
        outState.putString("home", binding.edtLocationHome.getText().toString());
        outState.putString("pod", binding.edtLocationPod.getText().toString());
        outState.putString("et", binding.edtLocationEt.getText().toString());
        outState.putString("apart", binding.edtLocationApart.getText().toString());

    }

    @Override
    public void onBackPressed() {
        router.exit();
    }

    @Override
    public void onDestroy() {
        disposableBag.clear();
        super.onDestroy();
    }
}
