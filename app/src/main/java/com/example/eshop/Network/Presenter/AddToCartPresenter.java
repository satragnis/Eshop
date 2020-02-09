package com.example.eshop.Network.Presenter;

import com.example.eshop.Model.AddToCartModel.AddToCartResponse;
import com.example.eshop.Network.ApiManager;
import com.example.eshop.Network.Interfaces.AddToCartInterface;

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

    public void addCartApi(Map<String, String> headerMap, Map<String, String> body) {
        mApiManager.addToCart(headerMap, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddToCartResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mAddToCartInterface.onAddCartResponseError(e.getMessage());
                    }

                    @Override
                    public void onNext(AddToCartResponse cartResponse) {
                        mAddToCartInterface.onAddCartResponseSuccess(cartResponse);
                    }
                });
    }
}
