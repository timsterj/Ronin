package com.timsterj.ronin.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.timsterj.ronin.data.local.converters.OrderDoneTypeConverter;
import com.timsterj.ronin.data.local.converters.OrderTypeConverter;
import com.timsterj.ronin.data.local.converters.ProductItemTypeConverter;
import com.timsterj.ronin.model.ProductItem;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Favorite {

    @PrimaryKey(autoGenerate = true)
    private int favorite_id;

    @TypeConverters(ProductItemTypeConverter .class)
    private List<ProductItem> productList = new ArrayList<>();

    @TypeConverters(OrderDoneTypeConverter.class)
    private List<OrderDone> orderList = new ArrayList<>();


    public void setFavorite_id(int favorite_id) {
        this.favorite_id = favorite_id;
    }

    public int getFavorite_id() {
        return favorite_id;
    }

    public List<ProductItem> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductItem> productList) {
        this.productList = productList;
    }

    public List<OrderDone> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderDone> orderList) {
        this.orderList = orderList;
    }
}
