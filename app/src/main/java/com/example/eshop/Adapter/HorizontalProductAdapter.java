package com.example.eshop.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.eshop.Model.Category;

import java.util.List;

public class HorizontalProductAdapter extends RecyclerView.Adapter {
    RecyclerView recyclerView;
    List<Category> categories;
    Context context;

    public HorizontalProductAdapter(RecyclerView recyclerView, List<Category> categories, Context context) {
        this.recyclerView = recyclerView;
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
