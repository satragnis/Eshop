package com.example.eshop.Network;

import com.example.eshop.Model.AddToCartModel.AddToCartResponse;
import com.example.eshop.Model.CartDetail.CartDetailResponse;
import com.example.eshop.Model.CartDetail.Result;
import com.example.eshop.Model.CategoryModel.CategoriesResponseModel;
import com.example.eshop.Model.MainItems;
import com.example.eshop.Model.ProductDashboardModel.ProductsDashboardResponseModel;
import com.example.eshop.Model.ProductDetailResponseModel.ProductDetailResponseModel;

import org.json.JSONObject;
import com.example.eshop.Model.ProductDetail.ProductDetail;
import com.example.eshop.Model.ProductListModel.ProductListResponseModel;
import com.example.eshop.Model.RemoveCartModel.RemoveCartResponse;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class ApiManager {

    private Service service;

    public ApiManager(){
        service = provideRetrofit().create(Service.class);
    }

    public Observable<MainItems> getProductList(Map<String, String> headerMap){
        return service.getProductList(headerMap);
    }

    public Observable<ProductsDashboardResponseModel> getAllProductDashboard(Map<String, String> headerMap, JSONObject jsonObject){
        return service.getAllProductList(headerMap,jsonObject);
    }

    public Observable<ProductListResponseModel> getProductListByCatId(Map<String, String> headerMap, JSONObject jsonObject){
        return service.getProductListByCatId(headerMap,jsonObject);
    }

    public Observable<CategoriesResponseModel> getAllCategories(Map<String, String> headerMap){
        return service.getCategotyList(headerMap);
    }

    public Observable<ProductDetail> getProductDetail(Map<String, String> headerMap,Map<String,String> body){
        return service.getProductDetail(headerMap,body);
    }

    public Observable<CartDetailResponse> getCartList(Map<String, String> headerMap, Map<String, String> body){
        return service.getCartList(headerMap,body);
    }

    public Observable<AddToCartResponse> addToCart(Map<String, String> headerMap, Map<String, String> body){
        return service.addToCart(headerMap,body);
    }

    public Observable<RemoveCartResponse> removeCart(Map<String, String> headerMap, Map<String, String> body){
        return service.removeCart(headerMap,body);
    }

    private Retrofit provideRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30,TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .client(client)
//                .baseUrl("https://stark-spire-93433.herokuapp.com/")
                .baseUrl(Urls.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
