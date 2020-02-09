package com.example.eshop.Model.CartDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("cartdetails")
    @Expose
    private List<Cartdetail> cartdetails = null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Cartdetail> getCartdetails() {
        return cartdetails;
    }

    public void setCartdetails(List<Cartdetail> cartdetails) {
        this.cartdetails = cartdetails;
    }
}
