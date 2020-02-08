package com.example.eshop.Network.Presenter;

import com.example.eshop.Model.MainItems;
import com.example.eshop.Model.ProductDashboardModel.ProductsDashboardResponseModel;
import com.example.eshop.Network.ApiManager;
import com.example.eshop.Network.Interfaces.ItemInterface;
import com.example.eshop.Network.Interfaces.ProductDashboardInterface;

import org.json.JSONObject;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AllProductDashboardPresenter {



private ProductDashboardInterface itemInterface;

public AllProductDashboardPresenter(ProductDashboardInterface itemInterface){
    this.itemInterface = itemInterface;
}

    public void getAllProduct(Map<String, String> headerMap, JSONObject jsonObject){
        ApiManager  apiManager = new ApiManager();

        apiManager.getAllProductDashboard(headerMap,jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProductsDashboardResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        itemInterface.onProductResponseError(e.getMessage());
                    }

                    @Override
                    public void onNext(ProductsDashboardResponseModel mainItems) {
                        itemInterface.onProductResponseSuccess(mainItems);
                    }
                });


    }


}
