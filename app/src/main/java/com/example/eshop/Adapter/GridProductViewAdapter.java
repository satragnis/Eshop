package com.example.eshop.Adapter;

import android.content.Context;
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
import com.example.eshop.Model.Category;
import com.example.eshop.Model.Product;
import com.example.eshop.Model.Variant;
import com.example.eshop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class GridProductViewAdapter extends RecyclerView.Adapter {
    RecyclerView recyclerView;
    List<Category> categories;
    List<Product> products;
    Context context;

    public GridProductViewAdapter(RecyclerView recyclerView, List<Category> categories,List<Product> products, Context context) {
        this.recyclerView = recyclerView;
        this.categories = categories;
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
        holder.prodName.setText(products.get(i).getName());
        holder.addToCartBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        List<Variant> variants =  products.get(i).getVariants();
        for (int j=0;j<variants.size();j++) {
            Variant variant = variants.get(j);
            Chip chip = new Chip(context);
            if(variant.getColor()!=null && variant.getSize()!=null && variant.getPrice()!=null){
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
                    }
                }
            });
        }
        holder.chipGroup.setSingleSelection(true);

        Glide.with(context)
                .load("https://picsum.photos/200/100")
                .into(holder.imageView);






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
        @BindView(R.id.chip_group)
        ChipGroup chipGroup;
        @BindView(R.id.textView3)
        TextView prodName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
