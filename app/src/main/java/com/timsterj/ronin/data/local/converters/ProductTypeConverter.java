package com.timsterj.ronin.data.local.converters;

import androidx.room.TypeConverter;

import com.timsterj.ronin.data.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ProductTypeConverter {


    @TypeConverter
    public String fromProductList(List<Product> productList) {
        if (productList == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>(){}.getType();
        String json = gson.toJson(productList, type);

        return json;
    }

    @TypeConverter
    public List<Product> toProductList(String productListString) {
        if (productListString == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>(){}.getType();

        List<Product> productList = gson.fromJson(productListString, type);

        return productList;
    }


}
