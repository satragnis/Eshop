package com.example.eshop.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eshop.Model.Category;
import com.example.eshop.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalCategoryAdapter extends RecyclerView.Adapter {
    RecyclerView recyclerView;
    List<Category> categories;
    Context context;
    private View.OnClickListener onItemClickListener;

    public HorizontalCategoryAdapter(RecyclerView recyclerView, List<Category> categories, Context context) {
        this.recyclerView = recyclerView;
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categoryrow,viewGroup,false);
        viewHolder = new HorizontalCategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ((ViewHolder)holder).cattextView.setText(categories.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category_name)
        TextView cattextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);

        }
    }


    public void setItemClickListener(View.OnClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
