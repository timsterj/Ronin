package com.timsterj.ronin.listeners;

import com.timsterj.ronin.model.ProductItem;

import java.util.List;

public interface ISwipedItemSearchListener {

    void addFavorite(ProductItem favoriteProduct);
    void addProductInOrderList(ProductItem productItem);

    List<ProductItem> getCurrentAdapterData();

}
