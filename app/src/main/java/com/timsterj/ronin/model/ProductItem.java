package com.timsterj.ronin.model;

import androidx.annotation.Nullable;

import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.data.model.Product;

import java.util.Comparator;

public class ProductItem implements Comparable<ProductItem> {

    private Product product;
    private String title;
    int viewType;

    public ProductItem(Product product) {
        this.product = product;
        viewType = Contracts.AdapterConstant.PRODUCT_VIEW_TYPE;
    }

    public ProductItem(String title) {
        this.title = title;
        viewType = Contracts.AdapterConstant.TITLE_VIEW_TYPE;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return product.getId().equals(((ProductItem)obj).product.getId());
    }

    @Override
    public int compareTo(ProductItem productItem) {
        return Comparators.CATEGORY.compare(this, productItem);
    }

    public static class Comparators {
        public static Comparator<ProductItem> CATEGORY = new Comparator<ProductItem>() {
            @Override
            public int compare(ProductItem t1, ProductItem t2) {
                if (t1.getProduct() != null && t2.getProduct() != null) {
                    String id1 = t1.getProduct().getId().substring(0,2);
                    String id2 = t2.getProduct().getId().substring(0,2);
                    return id1.compareTo(id2);
                }
                throw new  RuntimeException();
            }
        };


    }

}
