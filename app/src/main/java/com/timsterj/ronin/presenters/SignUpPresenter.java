package com.timsterj.ronin.presenters;

import com.timsterj.ronin.App;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.SignUpContract;
import com.timsterj.ronin.data.local.DAO.UserDao;
import com.timsterj.ronin.data.model.User;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class SignUpPresenter extends MvpPresenter<SignUpContract.View> implements SignUpContract.Presenter {

    private CompositeDisposable disposableBag = new CompositeDisposable();
    private UserDao userDao;

    public void init() {
        userDao = App.getINSTANCE()
                .getAppDatabase()
                .getUserDao();

    }

    @Override
    public void signUp(String phoneNumber, String name, String street, String home, String pod, String et, String apart) {
        getViewState().showLoading();

        int nameCode = 0;
        int streetCode = 0;
        int homeCode = 0;
        int podCode = 0;
        int etCode = 0;
        int apartCode = 0;

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

        if (nameCode == Contracts.NORMAL &&
                streetCode == Contracts.NORMAL &&
                homeCode == Contracts.NORMAL &&
                podCode == Contracts.NORMAL &&
                etCode == Contracts.NORMAL &&
                apartCode == Contracts.NORMAL
        ) {
            insertUserToDB(phoneNumber, name, street, home, pod, et, apart);
        }

    }

    void insertUserToDB(String phoneNumber, String name, String street, String home, String pod, String et, String apart) {
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        user.setName(name);
        user.setStreet(street);
        user.setHome(home);
        user.setPod(pod);
        user.setEt(et);
        user.setApart(apart);

        disposableBag.add(
                userDao.insertUser(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            Session.getINSTANCE().getUser()
                                    .onNext(user);

                            getViewState().onSuccess();
                        })
        );

    }


}
