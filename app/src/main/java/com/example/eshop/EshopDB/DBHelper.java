package com.example.eshop.EshopDB;

import android.content.Context;

import com.example.eshop.Model.MainItems;
//import com.example.eshop.Model.RealmModel.ItemsDBModel;

import io.realm.Realm;
import timber.log.Timber;

public class DBHelper {
    Context context;

    public DBHelper(Context context) {
        this.context = context;
    }

//    public void addAllItems(MainItems mainItems){
//        Realm realm = Realm.getDefaultInstance();
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                try {
////                    ItemsDBModel itemsDBModel = (ItemsDBModel) mainItems.clone();
//                    realm.insertOrUpdate(mainItems);
//                    Timber.d(">>>> data added");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Timber.d(">>>> data exception");
//                }
//            }
//        });
//    }
}
