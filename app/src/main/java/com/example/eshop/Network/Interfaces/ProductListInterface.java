package com.example.eshop.Network.Interfaces;

import com.example.eshop.Model.ProductDetail.ProductDetail;
import com.example.eshop.Model.ProductListModel.ProductListResponseModel;

public interface ProductListInterface {
    void onProductListResponseSuccess(ProductListResponseModel mainItems);

    void onProductListResponseError(String message);
}
