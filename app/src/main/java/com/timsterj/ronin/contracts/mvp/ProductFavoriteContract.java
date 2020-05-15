package com.timsterj.ronin.contracts.mvp;

import com.timsterj.ronin.data.model.Favorite;
import com.timsterj.ronin.data.model.Order;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface ProductFavoriteContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {
        void showErrorEmptyOrderlist();
        void hideErrorEmptyOrderlist();
        void deleteProductFromFavorite(int position);
        void addProductFromFavoriteToOrderlist(int position);

    }

    interface Presenter {

        void updateFavorite(Favorite favorite);
        void addOrderList(Order order);

    }
}
