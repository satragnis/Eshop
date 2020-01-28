package com.example.eshop.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.eshop.R;
import com.example.eshop.Utils.Constants;
import com.example.eshop.Utils.Utils;

import es.dmoral.toasty.Toasty;


public class LoginActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }


    public void forgotPassword(View view) {
        Utils.showToasty(this,getResources().getString(R.string.work_in_progress), Constants.INFO);
    }

    public void gotoRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}



