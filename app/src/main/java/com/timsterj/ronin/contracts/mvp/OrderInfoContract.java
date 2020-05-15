package com.timsterj.ronin.contracts.mvp;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface OrderInfoContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {
        void showOrderDoneInfo(String status, String orderID, String clientInfo, String date, String location, String orderlist);
        void startOrderStatusService();

    }

    interface Presenter {
        void initOrderDone(int index);

    }

}
