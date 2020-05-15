package com.timsterj.ronin.contracts.mvp;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface SignInContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {

        void onError(int errorCode);

        void onLoading();

        void onSuccess();
    }

    interface Presenter {

        void signIn(String login, String password);
    }
}
