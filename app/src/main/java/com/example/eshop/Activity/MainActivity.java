package com.example.eshop.Activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.eshop.Adapter.GridProductRankingViewAdapter;
import com.example.eshop.Adapter.HorizontalCategoryAdapter;
import com.example.eshop.EshopDB.DBHelper;
import com.example.eshop.Model.CategoryModel.CategoriesResponseModel;
import com.example.eshop.Model.CategoryModel.Result;
import com.example.eshop.Model.MessageEvent;


import com.example.eshop.Model.ProductDashboardModel.ProductsDashboardResponseModel;
import com.example.eshop.Network.Interfaces.CategoryListItemInterface;
import com.example.eshop.Network.Interfaces.ProductDashboardInterface;
import com.example.eshop.Network.Interfaces.ProductListInterface;
import com.example.eshop.Network.Presenter.AllCategoryPresenter;
import com.example.eshop.Network.Presenter.AllProductDashboardPresenter;
import com.example.eshop.R;
import com.example.eshop.Utils.Constants;
import com.example.eshop.Utils.Utils;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements CategoryListItemInterface, ProductDashboardInterface {


    @BindView(R.id.slider)
    SliderLayout sliderLayout;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerViewGrid;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.spinner_ranking)
    MaterialSpinner spinnerRanking;
    @BindView(R.id.loaderLAV)
    LottieAnimationView lottieAnimationView;
    @BindView(R.id.my_awesome_toolbar)
    Toolbar toolbar;


    Map<String, String> header;
    DBHelper dbHelper;
    private HorizontalCategoryAdapter mAdapter;
    private GridProductRankingViewAdapter gridProductRankingViewAdapter;


    List<Result> categories = new ArrayList<>();
    private Drawer drawer;
    private String endLimit ="50";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupMenu();
        setupToolbar();
        dbHelper = new DBHelper(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                makeAPICalls();
            }
        });
//        setupSpinnerRanking();
    }

    @Override
    protected void onResume() {
        super.onResume();
        makeAPICalls();
    }


//    private void setupSpinnerRanking() {
//        spinnerRanking.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
//                Timber.d(">>>>> ranking selected " + rankings.get(position));
//                List<ProductRanking> productRankings = rankings.get(position).getProducts();
//                inflateProductRankingAdapter(productRankings,integerProductHashMap);
//            }
//        });
//        spinnerRanking.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {
//            @Override
//            public void onNothingSelected(MaterialSpinner spinner) {
//                List<ProductRanking> productRankings = rankings.get(0).getProducts();
//                inflateProductRankingAdapter(productRankings,integerProductHashMap);
//            }
//        });
//        spinnerRanking.setSelectedIndex(0);
//    }


    //    -----------------------------------code for toolbar START---------------------------------
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cart_action: {//index returned when home button pressed
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                return true;
            }case R.id.menu_info_action: {  //index returned when home button pressed
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                return true;
            }case android.R.id.home: {  //index returned when home button pressed
                drawer.openDrawer();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //    -----------------------------------code for toolbar END---------------------------------


    private void makeAPICalls() {
        if (Utils.isNetworkAvailable(this)) {
            inflateDummyBanner();
            getItemsData();
        }else{
            Utils.showToasty(this,getResources().getString(R.string.no_internet_message),Constants.WARNING);
        }
    }

    private void setupMenu() {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_my_orders);
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(3).withName(R.string.sign_in);
        SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_settings);

//create the drawer and remember the `Drawer` result object
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withCloseOnClick(true)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        item4,
                        item3
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        boolean result= false;
                        switch (position){
                            case 0: startActivity(new Intent(MainActivity.this, MainActivity.class));
                            finish();
                            result =true;
                            break;

                            case 1:
                                Utils.showToasty(MainActivity.this,getResources().getString(R.string.work_in_progress), Constants.INFO);
                            result =true;
                            break;

                            case 2:
                                Utils.showToasty(MainActivity.this,getResources().getString(R.string.work_in_progress), Constants.INFO);
                            result =true;
                            break;

                            case 3:
                                Utils.showToasty(MainActivity.this,getResources().getString(R.string.work_in_progress), Constants.INFO);
                                result =true;
                            break;

                            case 4:
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                result =true;
                            break;
                        }
                        return result;
                    }
                })
                .build();
    }

    private void getItemsData() {
        Map<String,String> header = Utils.getHeader(this);
        Map<String,String> body = new HashMap<>();
        body.put(Constants.END_LIMIT_ID_KEY,endLimit);

        AllProductDashboardPresenter allProductDashboardPresenter = new AllProductDashboardPresenter(this);
        allProductDashboardPresenter.getAllProduct(header,body);

        AllCategoryPresenter allCategoryPresenter =  new AllCategoryPresenter(this);
        allCategoryPresenter.getAllCategories(header);



    }


    private void inflateDummyBanner() {
        DefaultSliderView textSliderView = new DefaultSliderView(this);
        textSliderView.error(R.drawable.productplaceholder)
                .empty(R.drawable.productplaceholder)
                .image("https://picsum.photos/500/300")
                .setScaleType(BaseSliderView.ScaleType.Fit);
        sliderLayout.addSlider(textSliderView);
    }



//    private void inflateRankingSpinner(List<Ranking> rankings1) {
//        spinnerRanking.setText(getResources().getString(R.string.sort_by));
//        List<String> rankingLabel = new ArrayList<>();
//        for (Ranking ranking : rankings1) {
//            rankingLabel.add(ranking.getRanking());
//        }
//        spinnerRanking.setItems(rankingLabel);
//    }


    private void inflateRecyclerViewAdapter(RecyclerView recyclerView, List<Result> categories) {
        mAdapter = new HorizontalCategoryAdapter(recyclerView, categories, this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        llm.setReverseLayout(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setItemClickListener(onItemClickListener);
    }


    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO: Step 4 of 4: Finally call getTag() on the view.
            // This viewHolder will have all required values.
            Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.zoom_in);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                    int position = viewHolder.getAdapterPosition();
                    if (viewHolder != null) {
                        Timber.d(">>>>> Cat selected " + viewHolder.getAdapterPosition());
                        Timber.d(">>>>> Cat selected " + categories.get(position).getCatName());
                        moveToProductListingActivity(categories.get(position));
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(animation);
        }
    };


    private void moveToProductListingActivity(Result category) {
        startActivity(new Intent(MainActivity.this, ProductListActivity.class));
        MessageEvent messageEvent = new MessageEvent(Constants.PRODUCTLISTEVENTDATA);
        messageEvent.setCategories(category);
        EventBus.getDefault().postSticky(messageEvent);
    }

    private void inflateProductRankingAdapter(List<com.example.eshop.Model.ProductDashboardModel.Result> productDashboard) {
        gridProductRankingViewAdapter =
                new GridProductRankingViewAdapter(recyclerViewGrid,
                        productDashboard,
                        MainActivity.this);
        recyclerViewGrid.setAdapter(gridProductRankingViewAdapter);
        recyclerViewGrid.setHasFixedSize(true);
        recyclerViewGrid.setLayoutManager(new GridLayoutManager(this, Utils.calculateNoOfColumns(this,180)));
        recyclerViewGrid.getRecycledViewPool().clear();
        gridProductRankingViewAdapter.notifyDataSetChanged();
    }


//    @Override
//    public void onItemResponseSuccess(MainItems mainItems) {
//        swipeRefreshLayout.setRefreshing(false);
//        lottieAnimationView.setVisibility(View.GONE);
//        Timber.d(">>>>>>>>>success " + mainItems.toString());
//        categories.clear();
//        rankings.clear();
//        categories.addAll(mainItems.getCategories());
//        rankings.addAll(mainItems.getRankings());
//
//        inflateRecyclerViewAdapter(recyclerView1, mainItems.getCategories());
//        inflateRankingSpinner(rankings);
//        integerProductHashMap = getAllProductHashMapFromCategories(products,categories);
//
//    }
//
//    @Override
//    public void onItemResponseError(String message) {
//        swipeRefreshLayout.setRefreshing(false);
//        lottieAnimationView.setVisibility(View.GONE);
//        Timber.d(">>>>>>>>>error  " + message);
//    }

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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {

    }

    @Override
    public void onCategoryResponseSuccess(CategoriesResponseModel mainItems) {
        swipeRefreshLayout.setRefreshing(false);
        lottieAnimationView.setVisibility(View.GONE);
        Timber.d(">>>>>>>>>success " + mainItems.toString());
        categories.clear();
        categories.addAll(mainItems.getResult());
        inflateRecyclerViewAdapter(recyclerView1, mainItems.getResult());

    }

    @Override
    public void onCategoryResponseError(String message) {
        swipeRefreshLayout.setRefreshing(false);
        lottieAnimationView.setVisibility(View.GONE);
        Utils.showToasty(this,message,Constants.ERROR);
    }

    @Override
    public void onProductResponseSuccess(ProductsDashboardResponseModel mainItems) {
        swipeRefreshLayout.setRefreshing(false);
        lottieAnimationView.setVisibility(View.GONE);
        inflateProductRankingAdapter(mainItems.getResult());
    }

    @Override
    public void onProductResponseError(String message) {
        swipeRefreshLayout.setRefreshing(false);
        lottieAnimationView.setVisibility(View.GONE);
        Utils.showToasty(this,message,Constants.ERROR);
    }
}
