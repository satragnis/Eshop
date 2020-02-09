package com.example.eshop.Network.Interfaces;

import com.example.eshop.Model.AddToCartModel.AddToCartResponse;

public interface AddToCartInterface {
    void onAddCartResponseSuccess(AddToCartResponse result);

    void onAddCartResponseError(String message);
}
