package com.timsterj.ronin.data.local.converters;

import androidx.room.TypeConverter;

import com.timsterj.ronin.model.ProductItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ProductItemTypeConverter {


    @TypeConverter
    public String fromProductItemList(List<ProductItem> productItemList) {
        if (productItemList == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductItem>>(){}.getType();
        String json = gson.toJson(productItemList, type);

        return json;
    }

    @TypeConverter
    public List<ProductItem> toProductItemList(String productItemListString) {
        if (productItemListString == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductItem>>(){}.getType();

        List<ProductItem> productItemList = gson.fromJson(productItemListString, type);

        return productItemList;
    }


}
