package com.example.eshop.Network.Presenter;

import com.example.eshop.Model.CategoryModel.CategoriesResponseModel;
import com.example.eshop.Model.ProductDashboardModel.ProductsDashboardResponseModel;
import com.example.eshop.Network.ApiManager;
import com.example.eshop.Network.Interfaces.CategoryListItemInterface;
import com.example.eshop.Network.Interfaces.ProductDashboardInterface;

import org.json.JSONObject;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AllCategoryPresenter {



private CategoryListItemInterface itemInterface;

public AllCategoryPresenter(CategoryListItemInterface itemInterface){
    this.itemInterface = itemInterface;
}

    public void getAllCategories(Map<String, String> headerMap){
        ApiManager  apiManager = new ApiManager();

        apiManager.getAllCategories(headerMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoriesResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        itemInterface.onCategoryResponseError(e.getMessage());
                    }

                    @Override
                    public void onNext(CategoriesResponseModel mainItems) {
                        itemInterface.onCategoryResponseSuccess(mainItems);
                    }
                });


    }


}
