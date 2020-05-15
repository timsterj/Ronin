package com.timsterj.ronin.contracts.mvp;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface SignInGuestContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {

        void nextActivity();

        void onError(int errorCode);

        void showLoading();
        void hideLoading();

        void onSuccess();
    }

    interface Presenter {

        void clear();

        void onSuccess(String phoneNumber, String name);

        void signInGuest(String phoneNumber, String name);
    }
}
