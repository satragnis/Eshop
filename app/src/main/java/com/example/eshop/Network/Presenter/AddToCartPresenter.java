package com.example.eshop.Network.Presenter;

import com.example.eshop.Model.CartDetail.Result;
import com.example.eshop.Model.ProductDetail.ProductDetail;
import com.example.eshop.Network.ApiManager;
import com.example.eshop.Network.Interfaces.AddToCartInterface;
import com.example.eshop.Network.Interfaces.ProductDetailInterface;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddToCartPresenter {
    private ApiManager mApiManager;
    private AddToCartInterface mAddToCartInterface;

    public AddToCartPresenter(AddToCartInterface addToCartInterface) {
        mApiManager = new ApiManager();
        this.mAddToCartInterface = addToCartInterface;
    }

    public void fetchCartListData(Map<String, String> headerMap) {
        mApiManager.getCartList(headerMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mAddToCartInterface.onCartResponseError(e.getMessage());
                    }

                    @Override
                    public void onNext(Result result) {
                        mAddToCartInterface.onCartResponseSuccess(result);
                    }
                });
    }
}
