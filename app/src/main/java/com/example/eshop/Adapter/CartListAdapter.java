package com.example.eshop.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eshop.Activity.LoginActivity;
import com.example.eshop.Model.CartDetail.Cartdetail;
import com.example.eshop.Model.CartDetail.Product;
import com.example.eshop.Model.ProductDashboardModel.Result;
import com.example.eshop.Network.Interfaces.CartListInterface;
import com.example.eshop.R;
import com.example.eshop.Utils.Utils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartListAdapter extends RecyclerView.Adapter {
    private List<Cartdetail> mListCart;
    private Context context;
    private CartListInterface mCartListInterface;

    public CartListAdapter(CartListInterface cartListInterface, List<Cartdetail> cartDetails, Context context) {
        this.mListCart = cartDetails;
        this.context = context;
        this.mCartListInterface = cartListInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cart_list, viewGroup, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = ((ViewHolder) viewHolder);
        List<Product> product = mListCart.get(position).getProduct();

        holder.tvProductName.setText(product.get(0).getProductName());
        holder.tvProductDesc.setText(product.get(0).getProductDescription());
        holder.tvProductPrice.setText(product.get(0).getProductPrice());

        float sum = 0f;
        float price = Float.parseFloat(product.get(0).getProductPrice());
        int quantity = Integer.parseInt(mListCart.get(position).getQuantity());
//        sum = price * quantity;
        holder.tvProductSubTotal.setText(String.valueOf(price));

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCartListInterface.deleteCartItem(position);
            }
        });

        Glide.with(context)
                .load(product.get(0).getImageUrl())
                .apply(Utils.getRequestOptionsForGlide())
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
        ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
