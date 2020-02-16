package com.example.eshop.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eshop.Network.Interfaces.ForgotInterface;
import com.example.eshop.Network.Presenter.ForgotPresenter;
import com.example.eshop.R;
import com.example.eshop.Utils.Constants;
import com.example.eshop.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityForgot extends AppCompatActivity implements ForgotInterface {

    @BindView(R.id.edtEmail)
    protected EditText edtEmail;

    private ForgotPresenter mForgotPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        ButterKnife.bind(this);

        initialise();
    }

    private void initialise() {
        mForgotPresenter = new ForgotPresenter(this);
    }

    public void submit(View view) {
        if (Utils.isNetworkAvailable(this)) {
            if (!checkValidation()) {
                Map<String, String> header = Utils.getHeader(this);
                Map<String, String> body = new HashMap<>();
                mForgotPresenter.callForgotPassword(header, body);
            }
        } else {
            Utils.showToasty(this, getResources().getString(R.string.no_internet_message), Constants.WARNING);
        }
    }

    private boolean checkValidation() {
        if (edtEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.error_enter_email), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onForgotResponseSuccess(String result) {

    }

    @Override
    public void onForgotResponseError(String message) {

    }
}
