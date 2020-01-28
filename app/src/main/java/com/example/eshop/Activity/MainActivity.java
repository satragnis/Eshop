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
import com.example.eshop.Adapter.GridProductViewAdapter;
import com.example.eshop.Adapter.HorizontalCategoryAdapter;
import com.example.eshop.EshopDB.DBHelper;
import com.example.eshop.Model.Category;
import com.example.eshop.Model.MainItems;
import com.example.eshop.Model.MessageEvent;
import com.example.eshop.Model.Product;
import com.example.eshop.Model.ProductRanking;
import com.example.eshop.Model.Ranking;
import com.example.eshop.Model.Tax;
import com.example.eshop.Model.Variant;
import com.example.eshop.Network.Interfaces.ItemInterface;
import com.example.eshop.Network.Presenter.ItemPresenter;
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

import static com.example.eshop.Utils.ModelHelper.getProductFromCategoryByPosition;
import static com.example.eshop.Utils.ModelHelper.getAllProductHashMapFromCategories;

public class MainActivity extends AppCompatActivity implements ItemInterface {


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
//    private GridProductViewAdapter gridProductViewAdapter;
    private GridProductRankingViewAdapter gridProductRankingViewAdapter;


    List<Category> categories = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    List<Variant> variants = new ArrayList<>();
    List<Tax> taxes = new ArrayList<>();
    List<Ranking> rankings = new ArrayList<>();
    HashMap<Integer,Product> integerProductHashMap = new HashMap<>();
    private Drawer drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupMenu();
        setupToolbar();
        makeAPICalls();
        dbHelper = new DBHelper(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                makeAPICalls();
            }
        });
        setupSpinnerRanking();
    }

    private void setupSpinnerRanking() {
        spinnerRanking.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Timber.d(">>>>> ranking selected " + rankings.get(position));
                List<ProductRanking> productRankings = rankings.get(position).getProducts();
                inflateProductRankingAdapter(productRankings,integerProductHashMap);
            }
        });
        spinnerRanking.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {
            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                List<ProductRanking> productRankings = rankings.get(0).getProducts();
                inflateProductRankingAdapter(productRankings,integerProductHashMap);
            }
        });
        spinnerRanking.setSelectedIndex(0);
    }


    //    -----------------------------------code for toolbar START---------------------------------
    private void setupToolbar() {
        setSupportActionBar(toolbar);
//        toolbar.setTitle("");
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        toolbar.setMenu();
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
        ItemPresenter itemPresenter = new ItemPresenter(this);
        itemPresenter.getItemList(Utils.getHeaderData(this));
    }


//    private void inflateBannerList(final BannersResponse bannersResponse1) {
//        for (int i = 0; i < bannersResponse1.getCount(); i++) {
//            final BannersResponse.Banner banner = bannersResponse1.getBanners().get(i);
//            DefaultSliderView defaultSliderView = new DefaultSliderView(getActivity());
//            defaultSliderView.error(R.drawable.banner_place_holder)
//                    .empty(R.drawable.banner_place_holder)
//                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
//                        @Override
//                        public void onSliderClick(BaseSliderView slider) {
//                            bannerClickListener(banner);
//                        }
//                    }).image(banner.getImageUrl())
//                    .setScaleType(BaseSliderView.ScaleType.Fit);
//            sliderLayout.addSlider(defaultSliderView);
////            sliderLayout.startAutoCycle();
//        }
//    }

    private void inflateDummyBanner() {
        DefaultSliderView textSliderView = new DefaultSliderView(this);
        textSliderView.error(R.drawable.productplaceholder)
                .empty(R.drawable.productplaceholder)
                .image("https://picsum.photos/500/300")
                .setScaleType(BaseSliderView.ScaleType.Fit);
        sliderLayout.addSlider(textSliderView);
    }

    @Override
    public void onItemResponseSuccess(MainItems mainItems) {
        swipeRefreshLayout.setRefreshing(false);
        lottieAnimationView.setVisibility(View.GONE);
        Timber.d(">>>>>>>>>success " + mainItems.toString());
        categories.clear();
        rankings.clear();
        categories = mainItems.getCategories();
        rankings = mainItems.getRankings();


        inflateRecyclerViewAdapter(recyclerView1, mainItems.getCategories());

        inflateRankingSpinner(rankings);
        integerProductHashMap = getAllProductHashMapFromCategories(products,categories);

    }

    private void inflateRankingSpinner(List<Ranking> rankings1) {
        spinnerRanking.setText(getResources().getString(R.string.sort_by));
        List<String> rankingLabel = new ArrayList<>();
        int i = 0;
        for (Ranking ranking : rankings1) {
            rankingLabel.add(ranking.getRanking());
            i++;
        }
        spinnerRanking.setItems(rankingLabel);
    }


    private void inflateRecyclerViewAdapter(RecyclerView recyclerView, List<com.example.eshop.Model.Category> categories) {
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
                        Timber.d(">>>>> Cat selected " + categories.get(position).getName());
                        products.clear();
                        products = getProductFromCategoryByPosition(products,categories,position);
                        moveToProductListingActivity(position);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(animation);
        }
    };


    private void moveToProductListingActivity(int pos) {
        startActivity(new Intent(MainActivity.this, ProductListActivity.class));
//                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
        MessageEvent messageEvent = new MessageEvent(Constants.PRODUCTLISTEVENTDATA);
        messageEvent.setCategories(categories);
        messageEvent.setRankings(rankings);
        messageEvent.setProducts(products);
        messageEvent.setIntegerValue(pos);
        EventBus.getDefault().postSticky(messageEvent);
    }

    private void inflateProductRankingAdapter(List<ProductRanking> productRankings, HashMap<Integer, Product> integerProductHashMap) {
        gridProductRankingViewAdapter =
                new GridProductRankingViewAdapter(recyclerViewGrid,
                        productRankings,
                        integerProductHashMap,
                        MainActivity.this);
        recyclerViewGrid.setAdapter(gridProductRankingViewAdapter);
        recyclerViewGrid.setHasFixedSize(true);
        recyclerViewGrid.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewGrid.getRecycledViewPool().clear();
        gridProductRankingViewAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemResponseError(String message) {
        swipeRefreshLayout.setRefreshing(false);
        lottieAnimationView.setVisibility(View.GONE);
        Timber.d(">>>>>>>>>error  " + message);
    }

//    private void bannerClickListener(BannersResponse.Banner banner) {
//        if (banner != null) {
//            if (banner.getInAppRedirect()) {
//                if (banner.getRedirectOptions().getType().equalsIgnoreCase("contest")) {
//                    Intent intent = new Intent(getActivity(), AccountWebviewActivity.class);
//                    intent.putExtra("url", banner.getUrl());
//                    intent.putExtra("title", "Contest");
//                    intent.putExtra("from", "homefragment");
//                    Objects.requireNonNull(getActivity()).startActivity(intent);
//                } else if (banner.getRedirectOptions().getType().equalsIgnoreCase("exchange")) {
//                    String baseCurrency = banner.getRedirectOptions().getBaseCurrency();
//                    String targetCurrency = banner.getRedirectOptions().getTargetCurrency();
//                    ((MainActivity) Objects.requireNonNull(getActivity())).setBottomItem(2);
//                    ((MainActivity) Objects.requireNonNull(getActivity())).loadFragment(new TradeFragmentViewPagerFragment(baseCurrency, targetCurrency, "buy", false));
//                }
//            } else {
//                Intent intent = new Intent(getActivity(), AccountWebviewActivity.class);
//                intent.putExtra("url", banner.getUrl());
//                intent.putExtra("title", "Koinex");
//                intent.putExtra("from", "homefragment");
//                Objects.requireNonNull(getActivity()).startActivity(intent);
//            }
//        }
//
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
}
