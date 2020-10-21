package com.example.testcloudfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telecom.Call;
import android.util.Log;

import com.example.testcloudfirebase.Adapter.PagerAdapter;
import com.example.testcloudfirebase.Model.Meal;
import com.example.testcloudfirebase.Model.PartMeal;
import com.example.testcloudfirebase.Model.Product;
import com.example.testcloudfirebase.ViewHolder.CallbackLoadDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;

public class TabActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE =1 ;
    FirebaseFirestore db;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Bundle extras = getIntent().getExtras();
        String idDay=extras.getString("idDay");
        String idMeal=extras.getString("idMeal");
        String title=extras.getString("title");
        getSupportActionBar().setTitle(title);


        db = FirebaseFirestore.getInstance();


        TabLayout tabLayout = findViewById(R.id.tabBar);
        TabItem tabSearch = findViewById(R.id.tabSearch);
        TabItem tabAdd = findViewById(R.id.AddProduct);
        final ViewPager viewPager = findViewById(R.id.viewPager);


      //  PagerAdapter pagerAdapter = new
       //         PagerAdapter(getSupportFragmentManager(),
       //        tabLayout.getTabCount());

      //  viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


       LoadAllProductsFromFirestore(new CallbackLoadDatabase() {
            @Override
            public void onCallback() {
                PagerAdapter pagerAdapter = new
                        PagerAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), products,idDay,idMeal,title);

                viewPager.setAdapter(pagerAdapter);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();


    }

    public void LoadAllProductsFromFirestore(CallbackLoadDatabase callback)
    {
        db.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.e("Tab", task.getResult().size() + "");
                        products=new ArrayList<>();
                        for (DocumentSnapshot snapshot : task.getResult()) {

                            products.add(new Product(snapshot.get("nazwa").toString()
                                    ,snapshot.getId()
                                    ,Double.parseDouble(snapshot.get("wartosc_energetyczna").toString())
                                    ,Double.parseDouble(snapshot.get("wartosc_energetyczna").toString())
                                    ,Double.parseDouble(snapshot.get("tluszcz").toString())
                                    ,Double.parseDouble(snapshot.get("tluszcze_nasycone").toString())
                                    ,Double.parseDouble(snapshot.get("weglowodany").toString())
                                    ,Double.parseDouble(snapshot.get("cukier").toString())
                                    ,Double.parseDouble(snapshot.get("bialko").toString())
                                    ,Double.parseDouble(snapshot.get("sol").toString())));

                            Log.e("tab","wczytano:"+snapshot.getId());
                        }

                        Log.e("tab","wczytano wszystko");
                        callback.onCallback();


                    }

                });
    }




}