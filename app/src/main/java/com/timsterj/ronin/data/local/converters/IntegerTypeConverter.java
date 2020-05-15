package com.timsterj.ronin.data.local.converters;

import androidx.room.TypeConverter;

import com.timsterj.ronin.data.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class IntegerTypeConverter {


    @TypeConverter
    public String fromCountList(List<Integer> productCount) {
        if (productCount == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>(){}.getType();
        String json = gson.toJson(productCount, type);

        return json;
    }

    @TypeConverter
    public List<Integer> toCountList(String productCountString) {
        if (productCountString == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>(){}.getType();

        List<Integer> productCount = gson.fromJson(productCountString, type);

        return productCount;
    }

}
