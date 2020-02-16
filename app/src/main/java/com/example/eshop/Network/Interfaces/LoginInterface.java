package com.example.eshop.Network.Interfaces;

public interface LoginInterface {
    void onLoginResponseSuccess(String result);

    void onLoginResponseError(String message);
}
