package com.timsterj.ronin.contracts.mvp;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface LaunchContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {

        void nextActivity();

        void showProgressBar();

        void hideProgressBar();

        void showErrorInternet();

    }

    interface Presenter {

        void startPrepareSession();

        void prepareUser();

        void getOrderDoneList();

        boolean checkOutAllPrepared();

        void endPrepareSession();
    }
}
