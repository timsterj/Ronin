package com.timsterj.ronin.contracts.mvp;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MapContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {

    }

    interface Presenter {

    }

}
