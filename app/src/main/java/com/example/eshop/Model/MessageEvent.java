package com.example.eshop.Model;

import com.example.eshop.Model.CategoryModel.Result;

import java.util.List;

public class MessageEvent {
    private String eventType;
    private int integerValue;
    private Result category;
//    private List<Product> products;
//    private List<Ranking> rankings;


    public MessageEvent(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(int integerValue) {
        this.integerValue = integerValue;
    }

    public Result getCategories() {
        return category;
    }

    public void setCategories(Result category) {
        this.category = category;
    }

//    public List<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(List<Product> products) {
//        this.products = products;
//    }

//    public List<Ranking> getRankings() {
//        return rankings;
//    }
//
//    public void setRankings(List<Ranking> rankings) {
//        this.rankings = rankings;
//    }
}
