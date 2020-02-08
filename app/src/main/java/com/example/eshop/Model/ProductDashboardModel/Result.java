
package com.example.eshop.Model.ProductDashboardModel;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_description")
    @Expose
    private String productDescription;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("avl_quantity")
    @Expose
    private String avlQuantity;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("base_url")
    @Expose
    private String baseUrl;
    @SerializedName("otherImages")
    @Expose
    private List<OtherImage> otherImages = null;
    private final static long serialVersionUID = 2381278005948500766L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAvlQuantity() {
        return avlQuantity;
    }

    public void setAvlQuantity(String avlQuantity) {
        this.avlQuantity = avlQuantity;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<OtherImage> getOtherImages() {
        return otherImages;
    }

    public void setOtherImages(List<OtherImage> otherImages) {
        this.otherImages = otherImages;
    }

}
