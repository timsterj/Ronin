package com.timsterj.ronin.presenters;

import com.timsterj.ronin.App;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.AuthorizationContract;
import com.timsterj.ronin.data.local.DAO.UserDao;
import com.timsterj.ronin.utils.ValidationUtil;

import io.reactivex.disposables.CompositeDisposable;
import moxy.MvpPresenter;

public class AuthorizationPresenter extends MvpPresenter<AuthorizationContract.View> implements AuthorizationContract.Presenter {

    private CompositeDisposable disposableBag = new CompositeDisposable();
    private UserDao userDao;

    public void init() {
        userDao = App.getINSTANCE()
                .getAppDatabase()
                .getUserDao();
    }

    @Override
    public void goSignIn(String phoneNumber) {
        int errorCodePhone = ValidationUtil.phoneNumberValidation(phoneNumber);

        if (errorCodePhone == Contracts.ErrorConstant.ERROR_NULL) {
            getViewState().onError(errorCodePhone);
        } else if (errorCodePhone == Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR) {
            getViewState().onError(errorCodePhone);

        } else if (errorCodePhone == Contracts.ErrorAuthorization.PHONE_INVALID_VALIDATION) {
            getViewState().onError(errorCodePhone);
        }
        if (errorCodePhone == Contracts.NORMAL) {
            getViewState().onSuccess();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableBag.clear();
    }
}
