package com.timsterj.ronin.domain.modelDTO;

public class ProductDTO {

    private String result;
    private String[] product_id;
    private String[] name;
    private String[] price;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String[] getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String[] product_id) {
        this.product_id = product_id;
    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public String[] getPrice() {
        return price;
    }

    public void setPrice(String[] price) {
        this.price = price;
    }
}
