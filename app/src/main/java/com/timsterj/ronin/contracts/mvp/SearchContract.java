package com.timsterj.ronin.contracts.mvp;

import com.timsterj.ronin.data.model.Favorite;
import com.timsterj.ronin.data.model.Order;
import com.timsterj.ronin.model.ProductItem;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface SearchContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {

        void updateRvProductList(List<ProductItem> products);

    }


    interface Presenter {

        void addOrderList(Order order);
        void addFavorite(Favorite favorite);
    }


}
