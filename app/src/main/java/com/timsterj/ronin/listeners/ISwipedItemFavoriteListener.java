package com.timsterj.ronin.listeners;

public interface ISwipedItemFavoriteListener {

    void deleteProductFromFavorite(int position);
    void addProductFromFavoriteToOrderlist(int position);

}
