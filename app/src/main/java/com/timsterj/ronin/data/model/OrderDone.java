package com.timsterj.ronin.data.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.timsterj.ronin.data.local.converters.ProductTypeConverter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderDone {

    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "order_id")
    private String order_id;

    @ColumnInfo(name = "order_status")
    private String status;

    private String price;
    private String date;
    private String result;
    private String order_number;
    private boolean notified = false;

    @TypeConverters(ProductTypeConverter.class)
    public List<Product> orderList = new ArrayList<>();


    public OrderDone(String result, String order_id, String order_number) {
        this.result = result;
        this.order_id = order_id;
        this.order_number = order_number;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public List<Product> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Product> orderList) {
        this.orderList = orderList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }
}
