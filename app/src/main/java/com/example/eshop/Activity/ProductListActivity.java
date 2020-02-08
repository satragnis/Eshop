package com.example.eshop.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

//import com.example.eshop.Adapter.GridProductViewAdapter;
import com.airbnb.lottie.LottieAnimationView;
import com.example.eshop.Adapter.GridProductViewAdapter;
import com.example.eshop.Model.Category;
import com.example.eshop.Model.CategoryModel.Result;
import com.example.eshop.Model.MessageEvent;

import com.example.eshop.Model.Product;
import com.example.eshop.Model.ProductListModel.ProductListResponseModel;
import com.example.eshop.Network.Interfaces.ProductListInterface;
import com.example.eshop.Network.Presenter.ProductListPresenter;
import com.example.eshop.R;
import com.example.eshop.Utils.Constants;
import com.example.eshop.Utils.Utils;

import org.apache.commons.collections4.functors.CatchAndRethrowClosure;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ProductListActivity extends AppCompatActivity implements ProductListInterface {


    @BindView(R.id.grid_recyclerview)
    RecyclerView recyclerViewGrid;
    @BindView(R.id.textView)
    TextView categoryName;
    @BindView(R.id.loaderLAV)
    LottieAnimationView lottieAnimationView;




    SharedPreferences sharedPreferences;
    Result category;
    private int categoryPos;
    private int startLimit = 0;
    private int EndLimit = 20;
    private GridProductViewAdapter gridProductViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        ButterKnife.bind(this);
//        extractIntent();


    }

    private void extractIntent() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            if (intent.hasExtra(Constants.PRODUCTLISTINTENTDATA)) {
                String prodStr = intent.getStringExtra(Constants.PRODUCTLISTINTENTDATA);
            }
        }
    }


    private void inflateRecyclerProductAdapter(List<com.example.eshop.Model.ProductListModel.Result> products) {
        gridProductViewAdapter =
                new GridProductViewAdapter(recyclerViewGrid,
                        products,
                        ProductListActivity.this);
        recyclerViewGrid.setAdapter(gridProductViewAdapter);
        recyclerViewGrid.setHasFixedSize(true);
//        recyclerViewGrid.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewGrid.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerViewGrid.getRecycledViewPool().clear();
        gridProductViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MessageEvent messageEvent = EventBus.getDefault().getStickyEvent(MessageEvent.class);
        onMessageEvent(messageEvent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.getEventType().equals(Constants.PRODUCTLISTEVENTDATA)) {
            category = messageEvent.getCategories();
//            products = messageEvent.getProducts();
            categoryPos = messageEvent.getIntegerValue();
            categoryName.setText(category.getCatName());
/**
 * Call api for product list from cat
 */
            getProductList(category.getId());
        }
    }

    private void getProductList(String id) {
        Map<String, String> header = Utils.getHeader(this);
        JSONObject request = getRequestObject(startLimit, EndLimit, id);
        ProductListPresenter productListPresenter = new ProductListPresenter(this);
        productListPresenter.getProductListByCatId(header, request);
    }

    @Override
    public void onProductListResponseSuccess(ProductListResponseModel mainItems) {
//        swipeRefreshLayout.setRefreshing(false);
        lottieAnimationView.setVisibility(View.GONE);
        Timber.d(">>>>>>>>>success " + mainItems.toString());
        inflateRecyclerProductAdapter(mainItems.getResult());


    }

    @Override
    public void onProductListResponseError(String message) {
//        swipeRefreshLayout.setRefreshing(false);
        lottieAnimationView.setVisibility(View.GONE);
        Timber.d(">>>>>>>>>error  " + message);
    }




    public JSONObject getRequestObject(int sl, int el, String id) {
        JSONObject request = new JSONObject();
        try {
            request.put(Constants.CATEGORY_ID_KEY, id);
            request.put(Constants.START_LIMIT_ID_KEY, sl);
            request.put(Constants.END_LIMIT_ID_KEY, el);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }
}
