package com.example.eshop.Network.Presenter;

import com.example.eshop.Network.ApiManager;
import com.example.eshop.Network.Interfaces.ForgotInterface;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ForgotPresenter {
    private ApiManager mApiManager;
    private ForgotInterface mForgotInterface;

    public ForgotPresenter(ForgotInterface forgotInterface) {
        mApiManager = new ApiManager();
        this.mForgotInterface = forgotInterface;
    }

    public void callForgotPassword(Map<String, String> headerMap, Map<String, String> body) {
        mApiManager.forgotPassword(headerMap, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mForgotInterface.onForgotResponseError(e.getMessage());
                    }

                    @Override
                    public void onNext(String response) {
                        mForgotInterface.onForgotResponseSuccess(response);
                    }
                });
    }
}
