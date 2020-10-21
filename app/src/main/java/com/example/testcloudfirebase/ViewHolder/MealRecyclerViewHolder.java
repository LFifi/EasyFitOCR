package com.example.testcloudfirebase.ViewHolder;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcloudfirebase.R;

public class MealRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvMealName, tvMealKcal, tvMealBialko, tvMealTluszcz, tvMealWegle;
    public ImageView btnAdd,Ibtn2;
    public Button btn;
    public RecyclerView recyclerViewPartMeal;

    public MealRecyclerViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
        super(itemView);

        tvMealName = itemView.findViewById(R.id.tv_nameMeal);
        tvMealKcal = itemView.findViewById(R.id.tv_kcalMeal);
        tvMealBialko = itemView.findViewById(R.id.tv_bialkoMeal);
        tvMealTluszcz = itemView.findViewById(R.id.tv_tluszczeMeal);
        tvMealWegle = itemView.findViewById(R.id.tv_wegleMeal);
        btnAdd=itemView.findViewById(R.id.imageButtonAdd);
        btnAdd.setVisibility(View.VISIBLE);
        recyclerViewPartMeal = itemView.findViewById(R.id.recycleVievProductRow);
        recyclerViewPartMeal.setVisibility(View.GONE);
        itemView.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        if(recyclerViewPartMeal.getVisibility()==View.GONE)
            recyclerViewPartMeal.setVisibility(View.VISIBLE);
        else
            recyclerViewPartMeal.setVisibility(View.GONE);



    }
}
