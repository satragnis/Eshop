package com.example.eshop.Network.Interfaces;

import com.example.eshop.Model.CartDetail.CartDetailResponse;

public interface CartListInterface {
    void onCartResponseSuccess(CartDetailResponse result);

    void onCartResponseError(String message);

    void deleteCartItem(int position);
}
