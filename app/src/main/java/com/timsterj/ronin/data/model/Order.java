package com.timsterj.ronin.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.timsterj.ronin.data.local.converters.ProductTypeConverter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {

    @PrimaryKey(autoGenerate = true)
    private int order_id;

    @TypeConverters(ProductTypeConverter.class)
    public List<Product> productItems = new ArrayList<>();

    private int score;

    public Order() {
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }


    public List<Product> getProductItems() {
        return productItems;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setProductItems(List<Product> productItems) {
        this.productItems = productItems;
    }


}
