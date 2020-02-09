package com.example.eshop.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import static com.example.eshop.Utils.Constants.PRODUCT_ID_KEY;
import static com.example.eshop.Utils.Constants.USER_ID_KEY;

public class CartListActivity extends AppCompatActivity implements
        CartListInterface,
        RemoveCartInterface {

    @BindView(R.id.recyclerView_cart)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.tv_price)
    protected TextView mTvPrice;
    @BindView(R.id.btnPlaceOrder)
    protected Button mBtnPlaceOrder;
    @BindView(R.id.container)
    protected ConstraintLayout mContainer;
    @BindView(R.id.loaderLAV)
    LottieAnimationView lottieAnimationView;

    private CartListPresenter mAddToCartPresenter;
    private CartDeletePresenter mCartDeletePresenter;
    private CartListAdapter mCartListAdapter;
    private List<Cartdetail> mCartDetails;

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
        Map<String, String> request = getRequestObject("2", "", "CART");

        if (Utils.isNetworkAvailable(this)) {
            mAddToCartPresenter.fetchCartListData(headerMap, request);
        } else {
            Utils.showToasty(this, getResources().getString(R.string.no_internet_message), Constants.WARNING);
        }
    }

    private void inflateRecyclerCartListAdapter(List<Cartdetail> cartDetails) {
        mCartDetails.addAll(cartDetails);
        mCartListAdapter = new CartListAdapter(this, cartDetails,
                CartListActivity.this);
        mRecyclerView.setAdapter(mCartListAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.getRecycledViewPool().clear();
        mCartListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCartResponseSuccess(CartDetailResponse result) {
        lottieAnimationView.setVisibility(View.GONE);
        if (result != null) {
            inflateRecyclerCartListAdapter(result.getResult().getCartdetails());
        }else {
            Utils.showToasty(this, result.getMessage(), Constants.INFO);
        }
    }

    @Override
    public void onCartResponseError(String message) {
        lottieAnimationView.setVisibility(View.GONE);
        Utils.showToasty(this, message, Constants.ERROR);
    }

    @Override
    public void deleteCartItem(int position) {
        confirmDialog(position);
    }

    private void confirmDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Cart")
                .setMessage("Do you really want to delete this cart?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Map<String, String> headerMap = Utils.getHeader(CartListActivity.this);
                        Map<String, String> request = getRequestObject("2", mCartDetails.get(position).getProduct().get(0).getId(), "DELETE");
                        mCartDeletePresenter.cartDelete(headerMap, request);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    public Map<String, String> getRequestObject(String id, String pId, String type) {
        Map<String, String> request = new HashMap<>();
        try {
            if (type.equalsIgnoreCase("DELETE")) {
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
        if (result != null) {
            Utils.showToasty(CartListActivity.this, result.getMessage(), Constants.SUCCESS);
            callApiCartList();
        }else {
            Utils.showToasty(this, result.getMessage(), Constants.INFO);
        }
    }

    @Override
    public void onCartRemoveResponseError(String message) {
        lottieAnimationView.setVisibility(View.GONE);
        Utils.showToasty(this, message, Constants.ERROR);
    }
}