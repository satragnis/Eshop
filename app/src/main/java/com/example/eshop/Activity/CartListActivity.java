package com.example.eshop.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.eshop.Adapter.CartListAdapter;
import com.example.eshop.Model.CartDetail.CartDetailResponse;
import com.example.eshop.Model.CartDetail.Cartdetail;
import com.example.eshop.Model.RemoveCartModel.RemoveCartResponse;
import com.example.eshop.Network.Interfaces.CartListInterface;
import com.example.eshop.Network.Interfaces.RemoveCartInterface;
import com.example.eshop.Network.Presenter.CartDeletePresenter;
import com.example.eshop.Network.Presenter.CartListPresenter;
import com.example.eshop.R;
import com.example.eshop.Utils.Constants;
import com.example.eshop.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.eshop.Utils.Constants.PRODUCT_ID_KEY;
import static com.example.eshop.Utils.Constants.USER_ID_KEY;

public class CartListActivity extends AppCompatActivity implements
        CartListInterface,
        RemoveCartInterface {

    @BindView(R.id.recyclerView_cart)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.tv_price)
    protected TextView mTvPrice;
    @BindView(R.id.btnCheckout)
    protected Button mBtnCheckout;
    @BindView(R.id.tv_cart_empty)
    protected TextView mTvCartEmpty;
    @BindView(R.id.loaderLAV)
    LottieAnimationView lottieAnimationView;
    @BindView(R.id.lottieAnimationViewEmpty)
    LottieAnimationView lottieAnimationViewEmpty;

    private CartListPresenter mAddToCartPresenter;
    private CartDeletePresenter mCartDeletePresenter;
    private CartListAdapter mCartListAdapter;
    private List<Cartdetail> mCartDetails;
    private String UserID = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        ButterKnife.bind(this);

        initializeView();

        callApiCartList();
    }

    private void initializeView() {
        mCartDetails = new ArrayList<>();
        mAddToCartPresenter = new CartListPresenter(this);
        mCartDeletePresenter = new CartDeletePresenter(this);
    }

    private void callApiCartList() {
        Map<String, String> headerMap = Utils.getHeader(this);
        Map<String, String> request = getRequestObject(UserID, "", Constants.CART);

        if (Utils.isNetworkAvailable(this)) {
            mAddToCartPresenter.fetchCartListData(headerMap, request);
        } else {
            Utils.showToasty(this, getResources().getString(R.string.no_internet_message), Constants.WARNING);
        }
    }

    private void inflateRecyclerCartListAdapter(List<Cartdetail> cartDetails) {
        if (cartDetails.size() > 0) {
            isVisibility(false);
            setTotalPrice(cartDetails);
            mCartDetails.addAll(cartDetails);
            mCartListAdapter = new CartListAdapter(this, cartDetails,
                    CartListActivity.this);
            mRecyclerView.setAdapter(mCartListAdapter);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.getRecycledViewPool().clear();
            mCartListAdapter.notifyDataSetChanged();
        } else {
            isVisibility(true);
        }
    }

    private void setTotalPrice(List<Cartdetail> cartDetails) {
        float total = 0f;
        for (Cartdetail cartDetails1 : cartDetails) {

            int quantity = Integer.parseInt(cartDetails1.getQuantity());
            float price = Float.parseFloat(cartDetails1.getProduct().get(0).getProductPrice());

            float subTotal = quantity * price;
            total = total + subTotal;
        }
        mTvPrice.setText(getResources().getString(R.string.rupees_symbol_label) + total);
    }

    @Override
    public void onCartResponseSuccess(CartDetailResponse result) {
        lottieAnimationView.setVisibility(View.GONE);
        if (result == null) return;
        List<Cartdetail> cartDetail = new ArrayList<>();
        if (result.getResult().getCartdetails() != null) {
            cartDetail = result.getResult().getCartdetails();
        }
        inflateRecyclerCartListAdapter(cartDetail);
    }

    @Override
    public void onCartResponseError(String message) {
        lottieAnimationView.setVisibility(View.GONE);
        Utils.showToasty(this, message, Constants.ERROR);
    }

    public void isVisibility(boolean isTrue) {
        if (isTrue) {
            mTvPrice.setVisibility(View.GONE);
            mBtnCheckout.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            mTvCartEmpty.setVisibility(View.VISIBLE);
            lottieAnimationViewEmpty.setVisibility(View.VISIBLE);
        } else {
            mTvPrice.setVisibility(View.VISIBLE);
            mBtnCheckout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mTvCartEmpty.setVisibility(View.GONE);
            lottieAnimationViewEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void deleteCartItem(int position) {
        confirmDialog(position);
    }

    private void confirmDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.Delete_Title_label))
                .setMessage(getResources().getString(R.string.Cart_item_delete_msg))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Map<String, String> headerMap = Utils.getHeader(CartListActivity.this);
                        Map<String, String> request = getRequestObject(UserID, mCartDetails.get(position).getProduct().get(0).getId(), Constants.DELETE);
                        mCartDeletePresenter.cartDelete(headerMap, request);
                    }
                }).setNegativeButton(android.R.string.no, null).show();
    }

    public Map<String, String> getRequestObject(String id, String pId, String type) {
        Map<String, String> request = new HashMap<>();
        try {
            if (type.equalsIgnoreCase(Constants.DELETE)) {
                request.put(PRODUCT_ID_KEY, pId);
            }
            request.put(USER_ID_KEY, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    @Override
    public void onCartRemoveResponseSuccess(RemoveCartResponse result) {
        lottieAnimationView.setVisibility(View.GONE);
        if (result == null) return;
        Utils.showToasty(CartListActivity.this, result.getMessage(), Constants.SUCCESS);
        callApiCartList();
    }

    @Override
    public void onCartRemoveResponseError(String message) {
        lottieAnimationView.setVisibility(View.GONE);
        Utils.showToasty(this, message, Constants.ERROR);
    }

    @OnClick(R.id.btnCheckout)
    public void checkOut() {
        Utils.showToasty(CartListActivity.this, getResources().getString(R.string.work_in_progress), Constants.INFO);
    }

    public void goBack(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
