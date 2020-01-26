package com.example.eshop.Application;

import android.support.multidex.MultiDexApplication;

import com.example.eshop.BuildConfig;
import com.example.eshop.EshopDB.MyMigration;

import java.util.Timer;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmSchema;
import timber.log.Timber;

public class MainApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Timber.plant(new Timber.DebugTree());
        setRealm();
    }

    private void setRealm() {
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder();

        RealmConfiguration configuration = builder
                .name(BuildConfig.DB)
                .schemaVersion(1)
                .migration(new MyMigration())
//                .encryptionKey(BuildConfig.REALM.getBytes())
                .build();

        Realm.setDefaultConfiguration(configuration);
    }
}
