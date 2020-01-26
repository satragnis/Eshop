package com.example.eshop.Network.Presenter;

import com.example.eshop.Model.MainItems;
import com.example.eshop.Network.ApiManager;
import com.example.eshop.Network.Interfaces.ItemInterface;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ItemPresenter{



private ItemInterface itemInterface;

public ItemPresenter(ItemInterface itemInterface){
    this.itemInterface = itemInterface;
}

    public void getItemList(Map<String, String> headerMap){
        ApiManager  apiManager = new ApiManager();

        apiManager.getProductList(headerMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MainItems>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        itemInterface.onItemResponseError(e.getMessage());
                    }

                    @Override
                    public void onNext(MainItems mainItems) {
                        itemInterface.onItemResponseSuccess(mainItems);
                    }
                });


    }


}
