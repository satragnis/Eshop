package com.example.eshop.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eshop.Model.AddToCartModel.AddToCartResponse;
import com.example.eshop.Network.Interfaces.RegistrationInterface;
import com.example.eshop.Network.Presenter.RegistrationPresenter;
import com.example.eshop.R;
import com.example.eshop.Utils.Constants;
import com.example.eshop.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements RegistrationInterface {

    @BindView(R.id.edtFirstName)
    protected EditText edtFirstName;
    @BindView(R.id.edtLastName)
    protected EditText edtLastName;
    @BindView(R.id.edtEmail)
    protected EditText edtEmail;
    @BindView(R.id.simplePassword)
    protected EditText edtSimplePassword;
    @BindView(R.id.confirmPassword)
    protected EditText edtConfirmPassword;
    @BindView(R.id.mobileNo)
    protected EditText edtMobileNo;
    @BindView(R.id.checkBoxTerms)
    protected CheckBox checkBoxTerms;

    private RegistrationPresenter mRegistrationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initialise();
    }

    private void initialise() {
        mRegistrationPresenter = new RegistrationPresenter(this);
    }

    public void createAccount(View view) {
        if (Utils.isNetworkAvailable(this)) {
            if (!checkValidation()) {
                Map<String, String> header = Utils.getHeader(this);
                Map<String, String> body = getRequestObject("", "", "", "", "");
                mRegistrationPresenter.registrationApi(header, body);
            }
        } else {
            Utils.showToasty(this, getResources().getString(R.string.no_internet_message), Constants.WARNING);
        }
    }

    private boolean checkValidation() {
        if (edtFirstName.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.error_enter_first_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtLastName.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.error_enter_last_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.error_enter_email), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtSimplePassword.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.error_enter_password), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtConfirmPassword.getText().toString().isEmpty() ||
                !edtConfirmPassword.getText().toString().equalsIgnoreCase(edtSimplePassword.getText().toString())) {
            Toast.makeText(this, getString(R.string.error_enter_confirm_password), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtMobileNo.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.error_enter_mobile_no), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkBoxTerms.isChecked()) {
            Toast.makeText(this, getString(R.string.terms_and_conditions), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public Map<String, String> getRequestObject(String firstName, String lastName, String email, String password, String mobile) {
        Map<String, String> request = new HashMap<>();
        try {
            request.put(Constants.FIRST_NAME, firstName);
            request.put(Constants.LAST_NAME, lastName);
            request.put(Constants.EMAIL_ADDRESS, email);
            request.put(Constants.PASSWORD, password);
            request.put(Constants.MOBILE_NO, mobile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    @Override
    public void onRegistrationResponseSuccess(String result) {

    }

    @Override
    public void onRegistrationResponseError(String message) {

    }
}
