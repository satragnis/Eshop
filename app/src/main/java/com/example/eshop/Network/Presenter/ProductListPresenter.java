package com.example.eshop.Network.Presenter;

import com.example.eshop.Model.ProductDetail.ProductDetail;
import com.example.eshop.Model.ProductListModel.ProductListResponseModel;
import com.example.eshop.Network.ApiManager;
import com.example.eshop.Network.Interfaces.ProductDetailInterface;
import com.example.eshop.Network.Interfaces.ProductListInterface;

import org.json.JSONObject;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProductListPresenter {
    private ApiManager mApiManager;
    private ProductListInterface listInterface;

    public ProductListPresenter(ProductListInterface listInterface) {
        mApiManager = new ApiManager();
        this.listInterface = listInterface;
    }

    public void getProductListByCatId(Map<String, String> headerMap, JSONObject jsonObject) {
        mApiManager.getProductListByCatId(headerMap, jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProductListResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listInterface.onProductListResponseError(e.getMessage());
                    }

                    @Override
                    public void onNext(ProductListResponseModel productDetail) {
                        listInterface.onProductListResponseSuccess(productDetail);
                    }
                });
    }
}
