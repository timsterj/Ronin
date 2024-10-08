package com.timsterj.ronin.data.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class Product implements Comparable<Product> {

    @NotNull
    @PrimaryKey
    private String id;

    private String imgUrl;
    private String name;
    private int price;

    private int count;
    private String recept;
    private String weight;

    @Ignore
    public Product() {
    }

    public Product(@NotNull String id, String imgUrl, String name, int price, String recept, String weight) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
        this.recept = recept;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRecept() {
        return recept;
    }

    public void setRecept(String recept) {
        this.recept = recept;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return getId().equals(((Product)obj).getId());
    }

    @Override
    public int compareTo(Product product) {
        return getId().substring(0, 2).compareTo(product.getId().substring(0, 2));
    }
}
