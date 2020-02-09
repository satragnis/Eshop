package com.example.eshop.Model.CartDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cartdetail {
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("product")
    @Expose
    private List<Product> product = null;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
