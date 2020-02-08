package com.example.eshop.Network;

import com.example.eshop.Model.CategoryModel.CategoriesResponseModel;
import com.example.eshop.Model.MainItems;
import com.example.eshop.Model.ProductDetail.ProductDetail;
import com.example.eshop.Model.ProductDashboardModel.ProductsDashboardResponseModel;
import com.example.eshop.Model.ProductDetailResponseModel.ProductDetailResponseModel;
import com.example.eshop.Model.ProductListModel.ProductListResponseModel;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.POST;
import rx.Observable;

public interface Service {

    @Headers({
//            "Content-Type: application/json;chartset=utf-8",
            "Content-Type: application/x-www-form-urlencoded;chartset=utf-8",
            "Accept: application/json"
    })


    @GET(Urls.GETPRODUCTLIST)
    Observable<MainItems> getProductList(@HeaderMap Map<String,String> headers);


    @POST(Urls.GETALLPRODUCTLIST)
    @FormUrlEncoded
    Observable<ProductsDashboardResponseModel> getAllProductList(@HeaderMap Map<String,String> headers,@FieldMap Map<String,String> body);

    @GET(Urls.GETALLCATEGORIES)
    Observable<CategoriesResponseModel> getCategotyList(@HeaderMap Map<String,String> headers);

//    @POST(Urls.GETPRODUCTDETAILSBYID)
//    Observable<ProductDetailResponseModel> getProductById(@HeaderMap Map<String,String> headers, @Body JSONObject jsonObject);

    @POST(Urls.GETPRODUCTLISTBYCATID)
    Observable<ProductListResponseModel> getProductListByCatId(@HeaderMap Map<String,String> headers,@Body JSONObject jsonObject);



    @POST(Urls.GET_PRODUCT_DETAIL)
    @FormUrlEncoded
    Observable<ProductDetail> getProductDetail(@HeaderMap  Map<String, String> headerMap, @FieldMap Map<String,String> body);
}
