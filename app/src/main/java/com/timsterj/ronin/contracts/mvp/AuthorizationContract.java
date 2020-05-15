package com.timsterj.ronin.contracts.mvp;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface AuthorizationContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {
        void onError(int errorCode);
        void onSuccess();
    }

    interface Presenter {

        void goSignIn(String phoneNumber);

    }

}
