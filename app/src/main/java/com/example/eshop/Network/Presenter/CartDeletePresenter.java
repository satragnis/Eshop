package com.example.eshop.Network.Presenter;

import com.example.eshop.Model.CartDetail.CartDetailResponse;
import com.example.eshop.Model.RemoveCartModel.RemoveCartResponse;
import com.example.eshop.Network.ApiManager;
import com.example.eshop.Network.Interfaces.CartListInterface;
import com.example.eshop.Network.Interfaces.RemoveCartInterface;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CartDeletePresenter {
    private ApiManager mApiManager;
    private RemoveCartInterface mRemoveCartInterface;

    public CartDeletePresenter(RemoveCartInterface removeCartInterface) {
        mApiManager = new ApiManager();
        this.mRemoveCartInterface = removeCartInterface;
    }

    public void cartDelete(Map<String, String> headerMap,Map<String, String> request) {
        mApiManager.removeCart(headerMap,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RemoveCartResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRemoveCartInterface.onCartRemoveResponseError(e.getMessage());
                    }

                    @Override
                    public void onNext(RemoveCartResponse result) {
                        mRemoveCartInterface.onCartRemoveResponseSuccess(result);
                    }
                });
    }
}
