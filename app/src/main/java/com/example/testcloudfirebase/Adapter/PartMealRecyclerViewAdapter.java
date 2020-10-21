package com.example.testcloudfirebase.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.testcloudfirebase.MainActivity;
import com.example.testcloudfirebase.Model.Meal;
import com.example.testcloudfirebase.Model.PartMeal;
import com.example.testcloudfirebase.Model.Product;
import com.example.testcloudfirebase.R;
import com.example.testcloudfirebase.ViewHolder.OnItemClickListener;
import com.example.testcloudfirebase.ViewHolder.PartMealRecyclerViewHolder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PartMealRecyclerViewAdapter extends RecyclerView.Adapter<PartMealRecyclerViewHolder> {
    MainActivity mainActivity;
    ArrayList<PartMeal> partMealArrayList;
    HashMap<String, Product> mapProduct;
    FirebaseFirestore db;
    String nameMeal;
    OnItemClickListener mlistener;

    public PartMealRecyclerViewAdapter(MainActivity mainActivity, ArrayList<PartMeal> partMealArrayList, HashMap<String, Product> mapProduct , String nameMeal,OnItemClickListener listener) {
        this.mainActivity = mainActivity;
        this.partMealArrayList = partMealArrayList;
        this.mapProduct = mapProduct;
        this.nameMeal = nameMeal;
        this.mlistener = listener;

    }

    @NonNull
    @Override
    public PartMealRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.part_meal_row,parent,false);

        return new PartMealRecyclerViewHolder(view,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull PartMealRecyclerViewHolder holder, int position) {
//Log.e("refresh"," oooooo");
      //  Log.e("adapter2", mapProduct + "");
        if(mapProduct.containsKey(partMealArrayList.get(position).getProduktID())) {
            DecimalFormat dec = new DecimalFormat("#0.0");
            Log.e("adapter2", mapProduct.size() + "");
            Log.e("adapter22", partMealArrayList.size() + "");
            Log.e("adapter222", mapProduct.get(partMealArrayList.get(position).getProduktID()) + "");
            double waga=partMealArrayList.get(position).getWaga();
            holder.tvProductName.setText(mapProduct.get(partMealArrayList.get(position).getProduktID()).getNazwa()+"");
            Log.e("testt",partMealArrayList.get(position).getProduktID()+" "+mapProduct.get(partMealArrayList.get(position).getProduktID()).getIdProduct()+"" );
            holder.tvWaga.setText(waga+"g");
            holder.tvProductKcal.setText((int)Math.round((waga*mapProduct.get(partMealArrayList.get(position).getProduktID()).getKcal()/100))+"Kcal");
            holder.tvProductBialko.setText(dec.format(waga*mapProduct.get(partMealArrayList.get(position).getProduktID()).getBialko()/100)+"g");
            holder.tvProductTluszcz.setText(dec.format(waga*mapProduct.get(partMealArrayList.get(position).getProduktID()).getTluszcz()/100)+"g");
            holder.tvProductWegle.setText(dec.format(waga*mapProduct.get(partMealArrayList.get(position).getProduktID()).getWeglowodany()/100)+"g");

            holder.Ibtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mlistener != null){
                        mlistener.onDeleteClick(
                                partMealArrayList.get(position).getDayID(),
                                nameMeal,
                                partMealArrayList.get(position).getDocID()
                        );

                    }
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return partMealArrayList.size();
    }
}
