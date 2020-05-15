package com.timsterj.ronin.data.local.converters;

import androidx.room.TypeConverter;

import com.timsterj.ronin.data.model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class OrderTypeConverter {


    @TypeConverter
    public String fromOrderList(List<Order> orderList) {
        if (orderList == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Order>>(){}.getType();
        String json = gson.toJson(orderList, type);

        return json;
    }

    @TypeConverter
    public List<Order> toOrderList(String orderlistString) {
        if (orderlistString == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Order>>(){}.getType();

        List<Order> orderList = gson.fromJson(orderlistString, type);

        return orderList;
    }


}
