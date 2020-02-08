package com.example.eshop.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.eshop.BuildConfig;
import com.example.eshop.R;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Utils {

    public static void showToasty(Context context, String msg, String type) {
        try {
            Toasty.Config.getInstance()
                    .setErrorColor(context.getResources().getColor(R.color.colorRed))
                    .setInfoColor(context.getResources().getColor(R.color.colorGreyText))
                    .setSuccessColor(context.getResources().getColor(R.color.colorGreen))
                    .setWarningColor(context.getResources().getColor(R.color.colorYellow))
//                    .setToastTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/muli_regular.ttf"))
                    .apply();
            if (context != null) {
                switch (type) {
                    case Constants.INFO:
                        try {
                            Toasty.info(context, msg, Toast.LENGTH_SHORT, true).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case Constants.ERROR:
                        try {
                            Toasty.error(context, msg, Toast.LENGTH_SHORT, true).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case Constants.SUCCESS:
                        try {
                            Toasty.success(context, msg, Toast.LENGTH_SHORT, true).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case Constants.WARNING:
                        try {
                            Toasty.warning(context, msg, Toast.LENGTH_SHORT, true).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
//                    Toasty.info(context,msg,Toast.LENGTH_SHORT,true).show();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static Map<String, String> getHeaderData(Context context) {

        Map<String, String> headerMap = new HashMap<>();

//        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.key_pref), Context.MODE_PRIVATE);

//        if (sharedPreferences != null) {

//            headerMap.put("deviceid", sharedPreferences.getString("device_id", " ").trim());
            headerMap.put("appversion", String.valueOf(BuildConfig.VERSION_CODE));
//            headerMap.put("devicetoken", sharedPreferences.getString(context.getString(R.string.key_authToken), " ").trim());
            headerMap.put("devicemodel", Build.MODEL);
//            headerMap.put("authorization", sharedPreferences.getString(context.getString(R.string.key_userToken), "").trim());
//        }

        return headerMap;
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static RequestOptions getRequestOptionsForGlide() {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.productplaceholder)
                .error(R.drawable.productplaceholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        return options;
    }

    public static Map<String, String> getHeader(Context context) {
        Map<String, String> header = new HashMap<>();
        if (context!=null)
        header.put(Constants.Authorization, Constants.AuthorizationValue);
        return header;
    }


    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
        return noOfColumns;
    }

}
