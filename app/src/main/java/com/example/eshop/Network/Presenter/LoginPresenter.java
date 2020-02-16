package com.example.eshop.Network.Presenter;

import com.example.eshop.Network.ApiManager;
import com.example.eshop.Network.Interfaces.ForgotInterface;
import com.example.eshop.Network.Interfaces.LoginInterface;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenter {
    private ApiManager mApiManager;
    private LoginInterface mLoginInterface;

    public LoginPresenter(LoginInterface loginInterface) {
        mApiManager = new ApiManager();
        this.mLoginInterface = loginInterface;
    }

    public void callLoginApi(Map<String, String> headerMap, Map<String, String> body) {
        mApiManager.login(headerMap, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoginInterface.onLoginResponseError(e.getMessage());
                    }

                    @Override
                    public void onNext(String response) {
                        mLoginInterface.onLoginResponseSuccess(response);
                    }
                });
    }
}
