package com.timsterj.ronin.contracts.mvp;

import com.timsterj.ronin.data.model.OrderDone;
import com.timsterj.ronin.model.ProductItem;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface OrderContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {
        void updateRvAdditionalList(List<ProductItem> additionalProdcuts);

        void updateOrderlist(String orderlist);
        void updatePrice(int price);

        void showLoading();
        void showError();
        void hideError();
        void onSuccess(int index);

        void startLastOrderStatusService();
    }

    interface Presenter {

        void initOrder();
        void initAdditionalList();

        void getCurrentPrice();

        void addToDB(OrderDone orderDone);

        void doOrder(String descr);
    }

}
