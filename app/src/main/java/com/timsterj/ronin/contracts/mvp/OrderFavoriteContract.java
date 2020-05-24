package com.timsterj.ronin.contracts.mvp;

import com.timsterj.ronin.data.model.Favorite;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface OrderFavoriteContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {
        void showErrorEmptyOrderlist();
        void hideErrorEmptyOrderlist();
        void deleteOrderFromFavorite(int position);

    }

    interface Presenter {
        void updateFavorite(Favorite favorite);
    }

}
