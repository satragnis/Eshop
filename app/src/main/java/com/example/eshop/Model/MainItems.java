package com.example.eshop.Model;

//-----------------------------------com.example.Category.java-----------------------------------


//-----------------------------------com.example.MainItems.java-----------------------------------


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MainItems implements Cloneable {

    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("rankings")
    @Expose
    private List<Ranking> rankings = null;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Ranking> getRankings() {
        return rankings;
    }

    public void setRankings(List<Ranking> rankings) {
        this.rankings = rankings;
    }





//-----------------------------------com.example.Product_.java-----------------------------------


//-----------------------------------com.example.Ranking.java-----------------------------------




//-----------------------------------com.example.Tax.java-----------------------------------



    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
//-----------------------------------com.example.Product.java-----------------------------------


