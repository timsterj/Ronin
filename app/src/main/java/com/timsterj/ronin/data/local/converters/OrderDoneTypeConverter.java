package com.timsterj.ronin.data.local.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timsterj.ronin.data.model.Order;
import com.timsterj.ronin.data.model.OrderDone;

import java.lang.reflect.Type;
import java.util.List;

public class OrderDoneTypeConverter {


    @TypeConverter
    public String fromOrderDoneList(List<OrderDone> orderDoneList) {
        if (orderDoneList == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<OrderDone>>(){}.getType();
        String json = gson.toJson(orderDoneList, type);

        return json;
    }

    @TypeConverter
    public List<OrderDone> toOrderDoneList(String orderDonelistString) {
        if (orderDonelistString == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<OrderDone>>(){}.getType();

        List<OrderDone> orderDoneList = gson.fromJson(orderDonelistString, type);

        return orderDoneList;
    }


}
