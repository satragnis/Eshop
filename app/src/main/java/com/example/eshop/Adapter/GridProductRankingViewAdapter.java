package com.example.eshop.Adapter;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eshop.Activity.ProductDetailActivity;

import com.example.eshop.R;
import com.example.eshop.Utils.Constants;
import com.example.eshop.Utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridProductRankingViewAdapter extends RecyclerView.Adapter {
    RecyclerView recyclerView;
    List<com.example.eshop.Model.ProductDashboardModel.Result> productDashboard;
    Context context;

    public GridProductRankingViewAdapter(RecyclerView recyclerView,List<com.example.eshop.Model.ProductDashboardModel.Result> productDashboard, Context context) {
        this.recyclerView = recyclerView;
        this.productDashboard = productDashboard;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemcategoryranking,viewGroup,false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vholder, int i) {
        ViewHolder holder = ((ViewHolder)vholder);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra(Constants.PRODUCT_ID_KEY,productDashboard.get(i).getId());
                context.startActivity(intent);
            }
        });
        Glide.with(context)
                .load(productDashboard.get(i).getImageUrl())
                .apply(Utils.getRequestOptionsForGlide())
                .into(holder.imageView);
        holder.prodName.setText(productDashboard.get(i).getProductName());






    }

    @Override
    public int getItemCount() {
        return productDashboard.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.addToCart)
        Button addToCartBTN;
        @BindView(R.id.tvProdName)
        TextView prodName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
