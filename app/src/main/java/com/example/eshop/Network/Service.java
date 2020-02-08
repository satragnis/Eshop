package com.example.eshop.Network;

import com.example.eshop.Model.CategoryModel.CategoriesResponseModel;
import com.example.eshop.Model.MainItems;
import com.example.eshop.Model.ProductDashboardModel.ProductsDashboardResponseModel;
import com.example.eshop.Model.ProductDetailResponseModel.ProductDetailResponseModel;
import com.example.eshop.Model.ProductListModel.ProductListResponseModel;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface Service {

    @Headers({
            "Content-Type: application/json;chartset=utf-8",
            "Accept: application/json"
    })

    @GET(Urls.GETPRODUCTLIST)
    Observable<MainItems> getProductList(@HeaderMap Map<String,String> headers);


    @POST(Urls.GETALLPRODUCTLIST)
    Observable<ProductsDashboardResponseModel> getAllProductList(@HeaderMap Map<String,String> headers,@Body JSONObject jsonObject);

    @GET(Urls.GETALLCATEGORIES)
    Observable<CategoriesResponseModel> getCategotyList(@HeaderMap Map<String,String> headers);

    @POST(Urls.GETPRODUCTDETAILSBYID)
    Observable<ProductDetailResponseModel> getProductById(@HeaderMap Map<String,String> headers, @Body JSONObject jsonObject);

    @POST(Urls.GETPRODUCTLISTBYCATID)
    Observable<ProductListResponseModel> getProductListByCatId(@HeaderMap Map<String,String> headers,@Body JSONObject jsonObject);


}
