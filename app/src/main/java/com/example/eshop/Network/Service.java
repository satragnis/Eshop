package com.example.eshop.Network;

import com.example.eshop.Model.MainItems;
import com.example.eshop.Model.ProductDetail.ProductDetail;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

public interface Service {

    @Headers({
            "Content-Type: application/json;chartset=utf-8",
            "Accept: application/json"
    })
    @GET(Urls.GETPRODUCTLIST)
    Observable<MainItems> getProductList(@HeaderMap Map<String, String> headers);

    @Headers({
            "Content-Type: application/json;chartset=utf-8",
            "Accept: application/json"
    })
    @GET(Urls.GET_PRODUCT_DETAIL)
    Observable<ProductDetail> getProductDetail(Map<String, String> headerMap,
                                               @Path("product_id") int prodID);
}
