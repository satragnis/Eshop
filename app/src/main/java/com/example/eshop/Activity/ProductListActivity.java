package com.example.eshop.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

//import com.example.eshop.Adapter.GridProductViewAdapter;
import com.example.eshop.Model.CategoryModel.Result;
import com.example.eshop.Model.MessageEvent;

import com.example.eshop.R;
import com.example.eshop.Utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListActivity extends AppCompatActivity {


    @BindView(R.id.grid_recyclerview)
    RecyclerView recyclerViewGrid;
    @BindView(R.id.textView)
    TextView categoryName;



//    private GridProductViewAdapter gridProductViewAdapter;
    SharedPreferences sharedPreferences;
//
//    List<Category> categories = new ArrayList<>();
//    List<Product> products = new ArrayList<>();
//    List<Tax> taxes = new ArrayList<>();
//    List<Ranking> rankings = new ArrayList<>();
    Result category;
    private int categoryPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        ButterKnife.bind(this);
//        extractIntent();


    }

    private void extractIntent() {
        if(getIntent()!=null){
            Intent intent = getIntent();
            if(intent.hasExtra(Constants.PRODUCTLISTINTENTDATA)){
                String prodStr = intent.getStringExtra(Constants.PRODUCTLISTINTENTDATA);
            }
        }
    }


//    private void inflateRecyclerProductAdapter(List<Product> products, List<Category> categories,int categoryPos) {
//        gridProductViewAdapter =
//                new GridProductViewAdapter(recyclerViewGrid,
//                        categories,
//                        products,
//                        ProductListActivity.this);
//        recyclerViewGrid.setAdapter(gridProductViewAdapter);
//        recyclerViewGrid.setHasFixedSize(true);
////        recyclerViewGrid.setLayoutManager(new GridLayoutManager(this, 2));
//        recyclerViewGrid.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//        recyclerViewGrid.getRecycledViewPool().clear();
//        gridProductViewAdapter.notifyDataSetChanged();
//    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MessageEvent messageEvent =  EventBus.getDefault().getStickyEvent(MessageEvent.class);
        onMessageEvent(messageEvent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent)
    {
        if(messageEvent.getEventType().equals(Constants.PRODUCTLISTEVENTDATA)){
            category = messageEvent.getCategories();
//            products = messageEvent.getProducts();
            categoryPos = messageEvent.getIntegerValue();
            categoryName.setText(category.getCatName());

//            inflateRecyclerProductAdapter(products,categories,categoryPos);
        }
    }
}
