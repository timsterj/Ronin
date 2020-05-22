package com.timsterj.ronin.contracts.mvp;

import com.timsterj.ronin.model.AboutUsItem;
import com.timsterj.ronin.model.ProductItem;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface HomeFragmentContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {

        void updateMightLikeList(List<ProductItem> mightLikeList);
        void updateAboutUsList(List<AboutUsItem> aboutUsList);

        void showTutorial();
    }

    interface Presenter {

        void prepareMightLikeList();

        void prepareAboutUsList();

    }

}
