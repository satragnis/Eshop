package com.example.eshop.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.example.eshop.Adapter.CartListAdapter;
import com.example.eshop.Model.CartDetail.Cartdetail;
import com.example.eshop.Model.CartDetail.Result;
import com.example.eshop.Model.CategoryModel.CategoriesResponseModel;
import com.example.eshop.Network.Interfaces.AddToCartInterface;
import com.example.eshop.Network.Presenter.AddToCartPresenter;
import com.example.eshop.R;
import com.example.eshop.Utils.Constants;
import com.example.eshop.Utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class AddToCartActivity extends AppCompatActivity implements AddToCartInterface {

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.tv_price)
    protected TextView mTvPrice;
    @BindView(R.id.btnPlaceOrder)
    protected Button mBtnPlaceOrder;

    private AddToCartPresenter mAddToCartPresenter;
    private CartListAdapter mCartListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        initializeView();

        //callApiCartList();
    }

    private void initializeView() {
        mAddToCartPresenter = new AddToCartPresenter(this);
    }

    private void callApiCartList() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("", "");

        if (Utils.isNetworkAvailable(this)) {
            mAddToCartPresenter.fetchCartListData(headerMap);
        } else {
            Utils.showToasty(this, getResources().getString(R.string.no_internet_message), Constants.WARNING);
        }
    }


    private void inflateRecyclerCartListAdapter(List<Cartdetail> cartdetails) {
        mCartListAdapter =
                new CartListAdapter(cartdetails,
                        AddToCartActivity.this);
        mRecyclerView.setAdapter(mCartListAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.getRecycledViewPool().clear();
        mCartListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCartResponseSuccess(Result result) {
        if (result != null) {
            inflateRecyclerCartListAdapter(result.getCartdetails());
        }
    }

    @Override
    public void onCartResponseError(String message) {

    }
}
