package com.example.eshop.Network.Interfaces;

import com.example.eshop.Model.CartDetail.Result;
import com.example.eshop.Model.CategoryModel.CategoriesResponseModel;

public interface AddToCartInterface {
    void onCartResponseSuccess(Result result);

    void onCartResponseError(String message);
}
