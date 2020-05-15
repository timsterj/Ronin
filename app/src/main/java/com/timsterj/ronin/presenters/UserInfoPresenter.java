package com.timsterj.ronin.presenters;

import com.timsterj.ronin.App;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.UserInfoContract;
import com.timsterj.ronin.data.local.DAO.UserDao;
import com.timsterj.ronin.data.model.User;
import com.timsterj.ronin.utils.ValidationUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.MvpPresenter;

public class UserInfoPresenter extends MvpPresenter<UserInfoContract.View> implements UserInfoContract.Presenter {

    private CompositeDisposable disposableBag = new CompositeDisposable();

    private UserDao userDao;

    public void init() {
        userDao = App.getINSTANCE()
                .getAppDatabase()
                .getUserDao();

    }

    @Override
    public void saveChanges(String phoneNumber, String name, String street, String home, String pod, String et, String apart) {
        getViewState().showLoading();

        int nameCode = 0;
        int phoneNumberCode = ValidationUtil.phoneNumberValidation(phoneNumber);
        int streetCode = 0;
        int homeCode = 0;
        int podCode = 0;
        int etCode = 0;
        int apartCode = 0;

        // PhoneNumber
        if (phoneNumberCode == Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR) {
            getViewState().onError(Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR);
        } else if (phoneNumberCode == Contracts.ErrorAuthorization.PHONE_INVALID_VALIDATION) {
            getViewState().onError(Contracts.ErrorAuthorization.PHONE_INVALID_VALIDATION);
        }
        // Name
        if (name.isEmpty()) {
            getViewState().onError(Contracts.ErrorAuthorization.NAME_EMPTY_ERROR);
        } else {
            nameCode = Contracts.NORMAL;
        }
        // Street
        if (street.isEmpty()) {
            getViewState().onError(Contracts.ErrorAuthorization.LOCATION_STREET_EMPTY_ERROR);
        } else {
            streetCode = Contracts.NORMAL;
        }
        // Home
        if (home.isEmpty()) {
            getViewState().onError(Contracts.ErrorAuthorization.LOCATION_HOME_EMPTY_ERROR);
        } else {
            homeCode = Contracts.NORMAL;
        }
        // Pod
        if (pod.isEmpty()) {
            getViewState().onError(Contracts.ErrorAuthorization.LOCATION_POD_EMPTY_ERROR);
        } else {
            podCode = Contracts.NORMAL;
        }
        // Et
        if (et.isEmpty()) {
            getViewState().onError(Contracts.ErrorAuthorization.LOCATION_ET_EMPTY_ERROR);
        } else {
            etCode = Contracts.NORMAL;
        }
        // Apart
        if (apart.isEmpty()) {
            getViewState().onError(Contracts.ErrorAuthorization.LOCATION_APART_EMPTY_ERROR);
        } else {
            apartCode = Contracts.NORMAL;
        }

        if (phoneNumberCode == Contracts.NORMAL &&
                nameCode == Contracts.NORMAL &&
                streetCode == Contracts.NORMAL &&
                homeCode == Contracts.NORMAL &&
                podCode == Contracts.NORMAL &&
                etCode == Contracts.NORMAL &&
                apartCode == Contracts.NORMAL
        ) {
            updateChanges(phoneNumber, name, street, home, pod, et, apart);
        }

    }

    @Override
    public void updateChanges(String phoneNumber, String name, String street, String home, String pod, String et, String apart) {
        disposableBag.add(
                userDao.updateUserInfo(
                        name,
                        phoneNumber,
                        street,
                        home,
                        pod,
                        et,
                        apart
                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            User user = Session.getINSTANCE().getUser().getValue();

                            user.setName(name);
                            user.setPhoneNumber(phoneNumber);
                            user.setStreet(street);
                            user.setHome(home);
                            user.setPod(pod);
                            user.setEt(et);
                            user.setApart(apart);

                            Session.getINSTANCE().getUser().onNext(user);

                            getViewState().onSuccess();
                        })
        );

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableBag.clear();
    }
}
