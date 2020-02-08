package com.example.eshop.Network.Interfaces;

import com.example.eshop.Model.ProductDashboardModel.ProductsDashboardResponseModel;

public interface ProductDashboardInterface {
    void onProductResponseSuccess(ProductsDashboardResponseModel mainItems);
    void onProductResponseError(String message);
}
