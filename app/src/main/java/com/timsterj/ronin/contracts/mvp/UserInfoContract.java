package com.timsterj.ronin.contracts.mvp;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface UserInfoContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {
        void userInfoPrepared(String phoneNumber, String name, String street, String home, String pod, String et, String apart);
        void showLoading();
        void hideLoading();
        void onError(int errorCode);
        void onSuccess();

    }

    interface Presenter {

        void saveChanges(String phoneNumber, String name, String street, String home, String pod, String et, String apart);
        void updateChanges(String phoneNumber, String name, String street, String home, String pod, String et, String apart);


    }

}
