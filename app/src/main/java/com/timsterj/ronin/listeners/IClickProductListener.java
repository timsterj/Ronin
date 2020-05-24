package com.timsterj.ronin.listeners;

import com.timsterj.ronin.data.model.Product;

public interface IClickProductListener<T> {

    void onProductClick(T data);

}
