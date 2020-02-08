package com.example.eshop.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.eshop.Model.ProductDetail.OtherImage;
import com.example.eshop.Model.ProductDetail.ProductDetail;
import com.example.eshop.Model.ProductDetail.Result;
import com.example.eshop.Network.Interfaces.ProductDetailInterface;
import com.example.eshop.Network.Presenter.ProductDetailPresenter;
import com.example.eshop.Network.Urls;
import com.example.eshop.R;
import com.example.eshop.Utils.Constants;
import com.example.eshop.Utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductDetailActivity extends AppCompatActivity implements
        ProductDetailInterface {

    @BindView(R.id.slider)
    protected SliderLayout sliderLayout;
    @BindView(R.id.tv_product_name)
    protected TextView mTvProductName;
    @BindView(R.id.tv_category_name)
    protected TextView mTvCategoryName;
    @BindView(R.id.tv_price)
    protected TextView mTvPrice;
    @BindView(R.id.tv_discount_price)
    protected TextView mTvDiscountPrice;
    @BindView(R.id.rating)
    protected AppCompatRatingBar mRating;
    @BindView(R.id.btn_add_to_cart)
    protected Button mBtnAddToCart;

    private ProductDetailPresenter mProductDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initialize();

        //callApiProductDetail();
    }

    private void initialize() {
        mProductDetailPresenter = new ProductDetailPresenter(this);
    }

    private void callApiProductDetail() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("", "");

        if (Utils.isNetworkAvailable(this)) {
            mProductDetailPresenter.getProductDetail(headerMap, 8);
        } else {
            Utils.showToasty(this, getResources().getString(R.string.no_internet_message), Constants.WARNING);
        }
    }

    private void inflateBannerList(final Result result) {
        for (int i = 0; i < result.getOtherImages().size(); i++) {
            final OtherImage banner = result.getOtherImages().get(i);
            DefaultSliderView defaultSliderView = new DefaultSliderView(this);
            defaultSliderView.error(R.drawable.banner_place_holder)
                    .empty(R.drawable.banner_place_holder)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            //bannerClickListener(banner);
                        }
                    }).image(Urls.GET_BASE_IMAGE_URL +
                    (banner.getProductImage() != null ? banner.getProductImage() : ""))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sliderLayout.addSlider(defaultSliderView);
            //sliderLayout.startAutoCycle();
        }
    }

    @Override
    public void onProductResponseSuccess(ProductDetail productDetail) {
        if (productDetail != null) {
            setDataToView(productDetail.getResult());
        }
    }

    private void setDataToView(List<Result> result) {
        inflateBannerList(result.get(0));
        mTvProductName.setText(result.get(0).getProductName() != null ? result.get(0).getProductName() : "");
        mTvCategoryName.setText(result.get(0).getCategoryName() != null ? result.get(0).getCategoryName() : "");
        mTvPrice.setText(result.get(0).getProductPrice() != null ? result.get(0).getProductPrice() : "");
        mTvDiscountPrice.setText(result.get(0).getDiscount() != null ? result.get(0).getDiscount() : "");
        mRating.setRating(result.get(0).getRating() != null ? Float.parseFloat(result.get(0).getRating()) : (float) 0.0);
    }

    @Override
    public void onProductResponseError(String message) {

    }

    @OnClick(R.id.btn_add_to_cart)
    void clickAddToCart() {
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
    }
}
