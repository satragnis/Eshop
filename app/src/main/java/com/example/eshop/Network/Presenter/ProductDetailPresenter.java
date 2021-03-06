package com.example.eshop.Network.Presenter;

import com.example.eshop.Model.ProductDetail.ProductDetail;
import com.example.eshop.Network.ApiManager;
import com.example.eshop.Network.Interfaces.ProductDetailInterface;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProductDetailPresenter {
    private ApiManager mApiManager;
    private ProductDetailInterface mProductDetailInterface;

    public ProductDetailPresenter(ProductDetailInterface productDetailInterface) {
        mApiManager = new ApiManager();
        this.mProductDetailInterface = productDetailInterface;
    }

    public void getProductDetail(Map<String, String> headerMap, Map<String,String> body) {
        mApiManager.getProductDetail(headerMap, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProductDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mProductDetailInterface.onProductResponseError(e.getMessage());
                    }

                    @Override
                    public void onNext(ProductDetail productDetail) {
                        mProductDetailInterface.onProductResponseSuccess(productDetail);
                    }
                });
    }
}
