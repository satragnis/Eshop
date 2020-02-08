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
import com.example.eshop.Activity.LoginActivity;

import com.example.eshop.Model.ProductDashboardModel.Result;
import com.example.eshop.R;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class GridProductRankingViewAdapter extends RecyclerView.Adapter {
    RecyclerView recyclerView;
    HashMap<Integer,String> integerProductHashMap;
    List<Result> productRankings;
    Context context;

    public GridProductRankingViewAdapter(RecyclerView recyclerView, List<Result> productRankings, HashMap<Integer,String> integerProductHashMap, Context context) {
        this.recyclerView = recyclerView;
        this.productRankings = productRankings;
        this.integerProductHashMap = integerProductHashMap;
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
        holder.addToCartBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, LoginActivity.class));
            }
        });

//        Integer productId = productRankings.get(i).getId();
//        Product product = integerProductHashMap.get("0");
        Result product = productRankings.get(i);
        if(product==null)return;
        holder.prodName.setText(product.getProductName());
//        List<Variant> variants =  product.getVariants();
        /*for (int j=0;j<variants.size();j++) {
            Variant variant = variants.get(j);
            Chip chip = new Chip(context);
            if(variant.getColor()!=null && variant.getPrice()!=null){
                chip.setClickable(true);
                chip.setEnabled(true);
                chip.setCheckable(true);
                holder.addToCartBTN.setEnabled(true);
                holder.addToCartBTN.setClickable(true);
            }else{
                chip.setClickable(false);
                chip.setEnabled(false);
                chip.setCheckable(false);
                holder.addToCartBTN.setEnabled(false);
                holder.addToCartBTN.setClickable(false);
            }
            holder.addToCartBTN.setEnabled(false);
            holder.addToCartBTN.setClickable(false);
            String color = variant.getColor()==null?"N/A":variant.getColor();
            String size  =  variant.getSize()==null?"N/A":variant.getSize();
            String price = variant.getPrice()==null?"------": String.valueOf(variant.getPrice());
            chip.setText("Color: "+color
                    +" | Size: "+size
                    +" | Price: "+context.getResources().getString(R.string.rupees_symbol_label)+price);
            holder.chipGroup.addView(chip);
            int finalJ = j;
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        Timber.d(">>>> variant color "+variants.get(finalJ).getColor());
                        Timber.d(">>>> variant size "+variants.get(finalJ).getSize());
                        Timber.d(">>>> variant price "+variants.get(finalJ).getPrice());
                        Timber.d(">>>> variant id "+variants.get(finalJ).getId());
                        holder.addToCartBTN.setEnabled(true);
                        holder.addToCartBTN.setClickable(true);
                    }else{
                        holder.addToCartBTN.setEnabled(false);
                        holder.addToCartBTN.setClickable(false);
                    }
                }
            });
        }*/
        holder.chipGroup.setSingleSelection(true);
//        holder.chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(ChipGroup chipGroup, int i) {
//                chipGroup.getCheckedChipId();
//                Timber.d(">>>> chip i "+i);
//                Timber.d(">>>> chipGroup.getCheckedChipId(); "+chipGroup.getCheckedChipId());
//
//                if(i==-1){
//                    holder.addToCartBTN.setEnabled(false);
//                }
//
//            }
//        });

        Glide.with(context)
                .load("https://picsum.photos/200/100")
                .into(holder.imageView);






    }

    @Override
    public int getItemCount() {
        return productRankings.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.addToCart)
        Button addToCartBTN;
        @BindView(R.id.chip_group)
        ChipGroup chipGroup;
        @BindView(R.id.tvProdName)
        TextView prodName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
