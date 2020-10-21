package com.example.testcloudfirebase.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcloudfirebase.R;

public class ProductsListRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView tvName;
    public ImageView btnAdd;
    public ProductsListRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        tvName=itemView.findViewById(R.id.tv_nameProdRow);
        btnAdd=itemView.findViewById(R.id.imageButtonRow);
    }
}
