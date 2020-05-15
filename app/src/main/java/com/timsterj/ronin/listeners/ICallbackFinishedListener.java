package com.timsterj.ronin.listeners;

import java.util.List;

public interface ICallbackFinishedListener<T> {

    void onError();

    void onSuccess(List<T> data);


}
