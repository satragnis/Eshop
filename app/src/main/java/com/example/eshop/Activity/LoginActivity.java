package com.example.eshop.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eshop.Network.Interfaces.LoginInterface;
import com.example.eshop.Network.Presenter.LoginPresenter;
import com.example.eshop.R;
import com.example.eshop.Utils.Constants;
import com.example.eshop.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class LoginActivity extends AppCompatActivity implements LoginInterface {
    @BindView(R.id.email_ET)
    protected EditText edtEmail;
    @BindView(R.id.simplePassword)
    protected EditText edtSimplePassword;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initialise();
    }

    private void initialise() {
        mLoginPresenter = new LoginPresenter(this);
    }

    public void forgotPassword(View view) {
        startActivity(new Intent(this, ActivityForgot.class));
    }

    private boolean checkValidation() {
        if (edtEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.error_enter_email), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtSimplePassword.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.error_enter_password), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void registerNow(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void onLoginResponseSuccess(String result) {

    }

    @Override
    public void onLoginResponseError(String message) {

    }

    public void signIn(View view) {
        if (Utils.isNetworkAvailable(this)) {
            if (!checkValidation()) {
                Map<String, String> header = Utils.getHeader(this);
                Map<String, String> body = new HashMap<>();
                mLoginPresenter.callLoginApi(header, body);
            }
        } else {
            Utils.showToasty(this, getResources().getString(R.string.no_internet_message), Constants.WARNING);
        }
    }
}



