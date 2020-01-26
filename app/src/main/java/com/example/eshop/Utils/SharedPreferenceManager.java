package com.example.eshop.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    SharedPreferences sharedPreferences;
    Context context;

    public SharedPreferenceManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constants.KEY_PREF,Context.MODE_PRIVATE);
    }

    public void clearSharedPref(){
        sharedPreferences.edit().clear().apply();
    }

    public boolean saveValue(String Key,Object Value){
        boolean result = true;
        SharedPreferences.Editor  editor = sharedPreferences.edit();
        if(Value instanceof String) {
            editor.putString(Key, (String)Value);
        }else if(Value instanceof Boolean) {
            editor.putBoolean(Key, (Boolean)Value);
        }else if(Value instanceof Integer) {
            editor.putInt(Key, (Integer)Value);
        }else if(Value instanceof Long){
            editor.putLong(Key, (Long)Value);
        }else if(Value instanceof Float){
            editor.putFloat(Key, (Float)Value);
        }else{
            result = false;
        }
        editor.apply();
        return result;
    }

    public String getString(String Key){
        return sharedPreferences.getString(Key,"");
    }public Integer getInt(String Key){
        return sharedPreferences.getInt(Key,-1);
    }public Long getLong(String Key){
        return sharedPreferences.getLong(Key,-1L);
    }public Boolean getBoolean(String Key){
        return sharedPreferences.getBoolean(Key,false);
    }public Float getFloat(String Key){
        return sharedPreferences.getFloat(Key,-1f);
    }

}


