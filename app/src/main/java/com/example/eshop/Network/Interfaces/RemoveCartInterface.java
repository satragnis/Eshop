package com.example.eshop.Network.Interfaces;

import com.example.eshop.Model.RemoveCartModel.RemoveCartResponse;

public interface RemoveCartInterface {
    void onCartRemoveResponseSuccess(RemoveCartResponse result);

    void onCartRemoveResponseError(String message);
}
