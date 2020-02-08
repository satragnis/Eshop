package com.example.eshop.Network.Interfaces;

import com.example.eshop.Model.CategoryModel.CategoriesResponseModel;

public interface CategoryListItemInterface {
    void onCategoryResponseSuccess(CategoriesResponseModel mainItems);
    void onCategoryResponseError(String message);
}
