package com.timsterj.ronin.contracts.mvp;

import com.timsterj.ronin.data.model.Order;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface BasketContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {
        void showErrorEmptyOrderlist();
        void hideErrorEmptyOrderlist();

        void showTutorial();
    }


    interface Presenter {
        void updateOrder(Order order);

    }


}
