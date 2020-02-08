package com.example.eshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.chip.ChipGroup;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eshop.Activity.LoginActivity;
import com.example.eshop.Model.CartDetail.Cartdetail;
import com.example.eshop.Model.ProductDashboardModel.Result;
import com.example.eshop.R;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartListAdapter extends RecyclerView.Adapter {
    private List<Cartdetail> mListCart;
    private Context context;

    public CartListAdapter(List<Cartdetail> cartDetails, Context context) {
        this.mListCart = cartDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemCartList, viewGroup, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = ((ViewHolder) viewHolder);
        holder.tvProductName.setText("");
        holder.tvProductDesc.setText("");
        holder.tvProductPrice.setText("");
        holder.tvProductSubTotal.setText("");

        holder.imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Glide.with(context)
                .load("https://picsum.photos/200/100")
                .into(holder.imageProduct);
    }

    @Override
    public int getItemCount() {
        return mListCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.imageProduct)
        ImageView imageProduct;
        @BindView(R.id.tvProductDesc)
        TextView tvProductDesc;
        @BindView(R.id.tvProductPrice)
        TextView tvProductPrice;
        @BindView(R.id.tvProductName)
        TextView tvProductName;
        @BindView(R.id.tvProductSubTotal)
        TextView tvProductSubTotal;
        @BindView(R.id.imgCancel)
        ImageView imgCancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
