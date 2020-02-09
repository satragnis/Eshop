package com.example.eshop.Network.Presenter;

import com.example.eshop.Model.CartDetail.CartDetailResponse;
import com.example.eshop.Network.ApiManager;
import com.example.eshop.Network.Interfaces.CartListInterface;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CartListPresenter {
    private ApiManager mApiManager;
    private CartListInterface mCartListInterface;

    public CartListPresenter(CartListInterface cartListInterface) {
        mApiManager = new ApiManager();
        this.mCartListInterface = cartListInterface;
    }

    public void fetchCartListData(Map<String, String> headerMap,Map<String, String> request) {
        mApiManager.getCartList(headerMap,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CartDetailResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mCartListInterface.onCartResponseError(e.getMessage());
                    }

                    @Override
                    public void onNext(CartDetailResponse result) {
                        mCartListInterface.onCartResponseSuccess(result);
                    }
                });
    }
}
