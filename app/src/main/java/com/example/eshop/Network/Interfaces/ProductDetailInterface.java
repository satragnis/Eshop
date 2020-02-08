package com.example.eshop.Network.Interfaces;

import com.example.eshop.Model.ProductDetail.ProductDetail;

public interface ProductDetailInterface {
    void onProductResponseSuccess(ProductDetail mainItems);

    void onProductResponseError(String message);
}
