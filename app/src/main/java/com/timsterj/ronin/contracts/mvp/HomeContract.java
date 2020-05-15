package com.timsterj.ronin.contracts.mvp;

import androidx.fragment.app.FragmentManager;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface HomeContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {

        void onError(int errorCode);

    }

    interface Presenter {


       void selectTab(String tab, int nextPosition, FragmentManager mg);
    }

}
