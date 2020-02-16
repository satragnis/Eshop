package com.example.eshop.Network.Interfaces;

public interface RegistrationInterface {
    void onRegistrationResponseSuccess(String result);

    void onRegistrationResponseError(String message);
}
