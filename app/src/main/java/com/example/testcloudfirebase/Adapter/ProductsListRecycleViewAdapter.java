package com.example.testcloudfirebase.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcloudfirebase.Model.Product;
import com.example.testcloudfirebase.R;
import com.example.testcloudfirebase.ViewHolder.MealRecyclerViewHolder;
import com.example.testcloudfirebase.ViewHolder.OnItemClickListener;
import com.example.testcloudfirebase.ViewHolder.ProductsListRecyclerViewHolder;

import java.util.ArrayList;


public class ProductsListRecycleViewAdapter extends RecyclerView.Adapter<ProductsListRecyclerViewHolder> {

     ArrayList<Product> productsArrayList;
     OnItemClickListener listener;

    public ProductsListRecycleViewAdapter(ArrayList<Product> productsArrayList, OnItemClickListener listener){
        this.productsArrayList = productsArrayList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ProductsListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_row,parent,false);

        return new ProductsListRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsListRecyclerViewHolder holder, int position) {

        holder.tvName.setText(productsArrayList.get(position).getNazwa());
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnAddProd(productsArrayList.get(position).getIdProduct());
            }
        });

    }


    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }
}
