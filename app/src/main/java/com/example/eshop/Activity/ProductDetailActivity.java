package com.example.eshop.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.eshop.Model.AddToCartModel.AddToCartResponse;
import com.example.eshop.Model.CartDetail.CartDetailResponse;
import com.example.eshop.Model.ProductDetail.OtherImage;
import com.example.eshop.Model.ProductDetail.ProductDetail;
import com.example.eshop.Model.ProductDetail.Result;
import com.example.eshop.Network.Interfaces.AddToCartInterface;
import com.example.eshop.Network.Interfaces.ProductDetailInterface;
import com.example.eshop.Network.Presenter.AddToCartPresenter;
import com.example.eshop.Network.Presenter.ProductDetailPresenter;
import com.example.eshop.Network.Urls;
import com.example.eshop.R;
import com.example.eshop.Utils.Constants;
import com.example.eshop.Utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.eshop.Utils.Constants.USER_ID_KEY;

public class ProductDetailActivity extends AppCompatActivity implements
        ProductDetailInterface,
        AddToCartInterface {

    @BindView(R.id.slider)
    protected SliderLayout sliderLayout;
    @BindView(R.id.tv_product_name)
    protected TextView mTvProductName;
    @BindView(R.id.tv_category_name)
    protected TextView mTvCategoryName;
    @BindView(R.id.tv_price)
    protected TextView mTvPrice;
    @BindView(R.id.textView)
    protected TextView mTvTitle;
    @BindView(R.id.tv_discount_price)
    protected TextView mTvDiscountPrice;
    @BindView(R.id.rating)
    protected AppCompatRatingBar mRating;
    @BindView(R.id.btn_add_to_cart)
    protected Button mBtnAddToCart;
    @BindView(R.id.view_cart)
    protected Button mBtnViewCart;
    @BindView(R.id.scrollView)
    protected ScrollView scrollView;
    @BindView(R.id.loaderLAV)
    LottieAnimationView lottieAnimationView;
    @BindView(R.id.imageV)
    ImageView productImageView;

    private AddToCartPresenter mAddToCartPresenter;

    private ProductDetailPresenter mProductDetailPresenter;
    private String productId;
    private String mStrQuantity;
    private String mStrUserId = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        extractIntent();
        initialize();
        callApiProductDetail();
    }


    private void extractIntent() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            if (intent.hasExtra(Constants.PRODUCT_ID_KEY)) {
                productId = intent.getStringExtra(Constants.PRODUCT_ID_KEY);
            }
        }
    }

    private void initialize() {
        mProductDetailPresenter = new ProductDetailPresenter(this);
        mAddToCartPresenter = new AddToCartPresenter(this);
    }

    private void callApiProductDetail() {
        Map<String, String> header = Utils.getHeader(this);
        Map<String, String> request = getRequestObject(productId, Constants.PRODUCT);
        if (Utils.isNetworkAvailable(this)) {
            mProductDetailPresenter.getProductDetail(header, request);
        } else {
            lottieAnimationView.setVisibility(View.GONE);
            Utils.showToasty(this, getResources().getString(R.string.no_internet_message), Constants.WARNING);
        }
    }

    private void inflateBannerList(final Result result) {
        if (result.getOtherImages().size() > 0) {
            sliderLayout.setVisibility(View.VISIBLE);
            productImageView.setVisibility(View.GONE);
            for (int i = 0; i < result.getOtherImages().size(); i++) {
                final OtherImage banner = result.getOtherImages().get(i);
                DefaultSliderView defaultSliderView = new DefaultSliderView(this);
                defaultSliderView.error(R.drawable.productplaceholder)
                        .empty(R.drawable.productplaceholder)
                        .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {
                                //bannerClickListener(banner);
                            }
                        }).image(Urls.GET_BASE_IMAGE_URL +
                        (banner.getProductImage() != null ? banner.getProductImage() : ""))
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);
                sliderLayout.addSlider(defaultSliderView);
                //sliderLayout.startAutoCycle();
            }
        } else {
            sliderLayout.setVisibility(View.GONE);
            productImageView.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(result.getImageUrl())
                    .apply(Utils.getRequestOptionsForGlide())
                    .into(productImageView);

        }
    }

    @Override
    public void onProductResponseSuccess(ProductDetail productDetail) {
        lottieAnimationView.setVisibility(View.GONE);
        if (productDetail != null) {
            scrollView.setVisibility(View.VISIBLE);
            mBtnAddToCart.setEnabled(true);
            setDataToView(productDetail.getResult());
        } else {
            scrollView.setVisibility(View.VISIBLE);
            mBtnAddToCart.setEnabled(false);
        }
    }

    private void setDataToView(List<Result> result) {
        mStrQuantity = result.get(0).getAvlQuantity()
                != null ? result.get(0).getAvlQuantity() : "";

        inflateBannerList(result.get(0));
        mTvProductName.setText(result.get(0).getProductName() != null ? result.get(0).getProductName() : "");
        mTvCategoryName.setText(result.get(0).getCategoryName() != null ? result.get(0).getCategoryName() : "");
        String price = result.get(0).getProductPrice() != null ? result.get(0).getProductPrice() : "";
        mTvPrice.setText(getResources().getString(R.string.rupees_symbol_label) + "" + price);
        mTvDiscountPrice.setText(result.get(0).getDiscount() != null ? result.get(0).getDiscount() : "");
        mRating.setRating(result.get(0).getRating() != null ? Float.parseFloat(result.get(0).getRating()) : (float) 0.0);
        mTvTitle.setText(result.get(0).getCategoryName() != null ? result.get(0).getCategoryName() : "");
    }

    @Override
    public void onProductResponseError(String message) {
        lottieAnimationView.setVisibility(View.GONE);
        Utils.showToasty(this, message, Constants.ERROR);
    }

    @OnClick(R.id.btn_add_to_cart)
    void clickAddToCart() {
        Map<String, String> header = Utils.getHeader(this);
        Map<String, String> request = getRequestObject(productId, Constants.CART);
        if (Utils.isNetworkAvailable(this)) {
            mAddToCartPresenter.addCartApi(header, request);
        } else {
            Utils.showToasty(this, getResources().getString(R.string.no_internet_message), Constants.WARNING);
        }
    }

    @OnClick(R.id.view_cart)
    void goToCart() {
        Intent intent = new Intent(ProductDetailActivity.this, CartListActivity.class);
        startActivity(intent);
        finish();
    }


    public Map<String, String> getRequestObject(String id, String type) {
        Map<String, String> request = new HashMap<>();
        try {
            request.put(Constants.PRODUCT_ID_KEY, id);
            if (type.equalsIgnoreCase(Constants.CART)) {
                request.put(Constants.USER_ID_KEY, mStrUserId);
                request.put(Constants.TOTAL_ITEM_QUANTITY, mStrQuantity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    @Override
    public void onAddCartResponseSuccess(AddToCartResponse result) {
        lottieAnimationView.setVisibility(View.GONE);
        if (result != null) {
            Utils.showToasty(ProductDetailActivity.this, result.getMessage(), Constants.SUCCESS);
        }
    }

    @Override
    public void onAddCartResponseError(String message) {
        lottieAnimationView.setVisibility(View.GONE);
        Utils.showToasty(this, message, Constants.ERROR);
    }
}
