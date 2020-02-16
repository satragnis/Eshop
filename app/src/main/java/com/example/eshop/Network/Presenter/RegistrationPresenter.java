package com.example.eshop.Network.Presenter;

import com.example.eshop.Network.ApiManager;
import com.example.eshop.Network.Interfaces.RegistrationInterface;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegistrationPresenter {
    private ApiManager mApiManager;
    private RegistrationInterface mRegistrationInterface;

    public RegistrationPresenter(RegistrationInterface registrationInterface) {
        mApiManager = new ApiManager();
        this.mRegistrationInterface = registrationInterface;
    }

    public void registrationApi(Map<String, String> headerMap, Map<String, String> body) {
        mApiManager.registration(headerMap, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRegistrationInterface.onRegistrationResponseError(e.getMessage());
                    }

                    @Override
                    public void onNext(String response) {
                        mRegistrationInterface.onRegistrationResponseSuccess(response);
                    }
                });
    }
}
