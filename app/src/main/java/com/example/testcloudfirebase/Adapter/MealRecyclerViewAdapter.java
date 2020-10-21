package com.example.testcloudfirebase.Adapter;

import android.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcloudfirebase.MainActivity;
import com.example.testcloudfirebase.Model.Meal;
import com.example.testcloudfirebase.Model.PartMeal;
import com.example.testcloudfirebase.Model.Product;
import com.example.testcloudfirebase.R;
import com.example.testcloudfirebase.ViewHolder.MealRecyclerViewHolder;
import com.example.testcloudfirebase.ViewHolder.OnItemClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MealRecyclerViewAdapter extends RecyclerView.Adapter<MealRecyclerViewHolder> {

    MainActivity mainActivity;
    ArrayList<Meal> mealArrayList;
    HashMap<String,Product> mapProduct;
    PartMealRecyclerViewAdapter adapter2;
    OnItemClickListener mlistener;

    public MealRecyclerViewAdapter(MainActivity mainActivity, ArrayList<Meal> mealArrayList, HashMap<String, Product> mapProduct, OnItemClickListener listener) {
        this.mainActivity = mainActivity;
        this.mealArrayList = mealArrayList;
        this.mapProduct = mapProduct;
        this.mlistener = listener;
    }

    @NonNull
    @Override
    public MealRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.list_item_single,parent,false);

        return new MealRecyclerViewHolder(view, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull MealRecyclerViewHolder holder, int position) {
        //
        DecimalFormat dec = new DecimalFormat("#0.0");
        holder.tvMealName.setText(mealArrayList.get(position).getName());
        holder.tvMealKcal.setText((int)Math.round(getSumKcal(mealArrayList.get(position).getLista()))+"Kcal");
        Log.e("Moj",mealArrayList.size()+"");
       // Log.e("Moj",mealArrayList.get(position).getLista().size()+"");
        holder.tvMealBialko.setText(dec.format(getSumBialko(mealArrayList.get(position).getLista()))+"g");
        holder.tvMealTluszcz.setText(dec.format(getSumTluszcz(mealArrayList.get(position).getLista()))+"g");
        holder.tvMealWegle.setText(dec.format(getSumWegle(mealArrayList.get(position).getLista()))+"g");

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.onAddClick(mealArrayList.get(position).getName());
            }
        });

        holder.recyclerViewPartMeal.setHasFixedSize(true);
        holder.recyclerViewPartMeal.setLayoutManager(new LinearLayoutManager(mainActivity));
        adapter2= new PartMealRecyclerViewAdapter(mainActivity, (ArrayList<PartMeal>) mealArrayList.get(position).getLista(),mapProduct, mealArrayList.get(position).getName(),mlistener);
        holder.recyclerViewPartMeal.setAdapter(adapter2);

    }

    @Override
    public int getItemCount() {
        return mealArrayList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){mlistener  = listener;}

    public Double getSumBialko(List<PartMeal> list) {
        Double temp = 0.0;
        for (PartMeal p : list) {
            if (mapProduct.containsKey(p.getProduktID()))
                temp+=p.getWaga()*mapProduct.get(p.getProduktID()).getBialko()/100;
        }
        return temp;
    }
    public Double getSumTluszcz(List<PartMeal> list) {
        Double temp = 0.0;
        for (PartMeal p : list) {
            if (mapProduct.containsKey(p.getProduktID())){
                temp+=p.getWaga()*mapProduct.get(p.getProduktID()).getTluszcz()/100;
            Log.e("adapter1",mapProduct.get(p.getProduktID()).getTluszcz()+"");
                Log.e("adapter1",mapProduct.get(p.getProduktID())+"");
            }
        }
        return temp;
    }
    public Double getSumWegle(List<PartMeal> list) {
        Double temp = 0.0;
        for (PartMeal p : list) {
            if (mapProduct.containsKey(p.getProduktID()))
                temp+=p.getWaga()*mapProduct.get(p.getProduktID()).getWeglowodany()/100;
        }
        return temp;
    }
     public Double getSumKcal(List<PartMeal> list) {
        Double temp = 0.0;

        for (PartMeal p : list) {
            if (mapProduct.containsKey(p.getProduktID()))
                temp+=p.getWaga()*mapProduct.get(p.getProduktID()).getKcal()/100;
        }

        return temp;
    }

    public void reloadAdapter2(){
        adapter2.notifyDataSetChanged();
    }



}
