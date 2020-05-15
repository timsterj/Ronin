package com.timsterj.ronin.contracts.mvp;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface SignUpContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {

        void showLoading();
        void onError(int errorCode);
        void onSuccess();
    }

    interface Presenter {
        void signUp(String phoneNumber, String name, String street, String home, String pod, String et, String apart);
    }

}
