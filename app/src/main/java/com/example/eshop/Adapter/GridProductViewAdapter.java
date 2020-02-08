package com.example.eshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.eshop.Activity.LoginActivity;

import com.example.eshop.Activity.ProductDetailActivity;
import com.example.eshop.Model.ProductListModel.Result;
import com.example.eshop.R;
import com.example.eshop.Utils.Constants;
import com.example.eshop.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class GridProductViewAdapter extends RecyclerView.Adapter {
    RecyclerView recyclerView;
//    List<Category> categories;
    List<Result> products;
    Context context;

    public GridProductViewAdapter(RecyclerView recyclerView, List<Result>  products, Context context) {
        this.recyclerView = recyclerView;
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemcategory,viewGroup,false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vholder, int i) {
        ViewHolder holder = ((ViewHolder)vholder);
        Result product = products.get(i);
        holder.prodName.setText(product.getProductName());
        holder.addToCartBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, LoginActivity.class));
            }
        });

            if(product.getProductPrice()!=null){
                holder.addToCartBTN.setEnabled(true);
                holder.addToCartBTN.setClickable(true);
            }else{
                holder.addToCartBTN.setEnabled(false);
                holder.addToCartBTN.setClickable(false);
            }
            holder.addToCartBTN.setEnabled(false);
            holder.addToCartBTN.setClickable(false);
            String price = product.getProductPrice()==null?"------":product.getProductPrice();
            String avail = product.getAvlQuantity()==null?"------":product.getAvlQuantity();
            holder.productPrice.setText("Price: "+price+" | "+"Availability: "+avail);


        Glide.with(context)
                .load(product.getImageUrl())
                .apply(Utils.getRequestOptionsForGlide())
                .into(holder.imageView);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra(Constants.PRODUCT_ID_KEY,product.getId());
                context.startActivity(intent);
            }
        });






    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.addToCart)
        Button addToCartBTN;
        @BindView(R.id.textView3)
        TextView prodName;
        @BindView(R.id.productPrice)
        TextView productPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
