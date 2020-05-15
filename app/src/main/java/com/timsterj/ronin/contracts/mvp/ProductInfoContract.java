package com.timsterj.ronin.contracts.mvp;

import com.timsterj.ronin.data.model.Favorite;
import com.timsterj.ronin.data.model.Order;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface ProductInfoContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {

    }

    interface Presenter {

        void addOrderList(Order order);
        void addFavorite(Favorite favorite);
    }

}
