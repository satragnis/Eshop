package com.example.eshop.Network;

import com.example.eshop.Model.MainItems;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import rx.Observable;

public interface Service {

    @Headers({
            "Content-Type: application/json;chartset=utf-8",
            "Accept: application/json"
    })
    @GET(Urls.GETPRODUCTLIST)
    Observable<MainItems> getProductList(@HeaderMap Map<String,String> headers);
}
