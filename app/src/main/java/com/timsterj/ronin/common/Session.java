package com.timsterj.ronin.common;

import com.timsterj.ronin.data.model.Favorite;
import com.timsterj.ronin.data.model.Order;
import com.timsterj.ronin.data.model.OrderDone;
import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.data.model.User;
import com.timsterj.ronin.model.AboutUsItem;
import com.timsterj.ronin.model.Category;
import com.timsterj.ronin.model.ProductItem;

import java.util.List;

import io.reactivex.subjects.BehaviorSubject;

public class Session {

    private static Session INSTANCE;

    private BehaviorSubject<User> user = BehaviorSubject.create();

    private BehaviorSubject<List<Category>> categories = BehaviorSubject.create();
    private BehaviorSubject<List<Product>> productBase = BehaviorSubject.create();
    private BehaviorSubject<List<ProductItem>> additionalProducts = BehaviorSubject.create();
    private BehaviorSubject<List<ProductItem>> productsMightLike = BehaviorSubject.create();
    private BehaviorSubject<List<ProductItem>> products = BehaviorSubject.create();
    private BehaviorSubject<List<AboutUsItem>> productsAboutUs = BehaviorSubject.create();
    private BehaviorSubject<List<OrderDone>> orderDoneList = BehaviorSubject.create();

    private BehaviorSubject<List<Favorite>> favoriteList = BehaviorSubject.create();
    private BehaviorSubject<List<Order>> orderList = BehaviorSubject.create();

    public static Session getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new Session();
            return INSTANCE;
        }
        return INSTANCE;
    }

    public BehaviorSubject<User> getUser() {
        return user;
    }

    public BehaviorSubject<List<Category>> getCategories() {
        return categories;
    }

    public BehaviorSubject<List<Order>> getOrderList() {
        return orderList;
    }

    public BehaviorSubject<List<Favorite>> getFavoriteList() {
        return favoriteList;
    }

    public BehaviorSubject<List<Product>> getProductBase() {
        return productBase;
    }

    public BehaviorSubject<List<ProductItem>> getProductsMightLike() {
        return productsMightLike;
    }

    public BehaviorSubject<List<ProductItem>> getProducts() {
        return products;
    }

    public BehaviorSubject<List<AboutUsItem>> getProductsAboutUs() {
        return productsAboutUs;
    }

    public BehaviorSubject<List<ProductItem>> getAdditionalProducts() {
        return additionalProducts;
    }

    public BehaviorSubject<List<OrderDone>> getOrderDoneList() {
        return orderDoneList;
    }
}
