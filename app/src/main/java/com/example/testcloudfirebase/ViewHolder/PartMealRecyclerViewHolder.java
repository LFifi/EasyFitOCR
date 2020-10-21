package com.example.testcloudfirebase.ViewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcloudfirebase.R;

public class PartMealRecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView tvProductName,tvWaga, tvProductKcal, tvProductBialko, tvProductTluszcz, tvProductWegle;
    public ImageView Ibtn1;


    public PartMealRecyclerViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
        super(itemView);

        tvProductName = itemView.findViewById(R.id.tv_nameProduct);
        tvWaga = itemView.findViewById(R.id.tvWagaProduct);
        tvProductKcal = itemView.findViewById(R.id.tv_kcalProduct);
        tvProductBialko = itemView.findViewById(R.id.tv_bialkoProduct);
        tvProductTluszcz = itemView.findViewById(R.id.tv_tluszczeProduct);
        tvProductWegle = itemView.findViewById(R.id.tv_wegleProduct);
        Ibtn1=itemView.findViewById(R.id.imageButtonDelete);
        Ibtn1.setVisibility(View.VISIBLE);




    }
}
