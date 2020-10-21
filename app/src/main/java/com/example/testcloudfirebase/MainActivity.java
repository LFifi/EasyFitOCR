package com.example.testcloudfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testcloudfirebase.Adapter.MealRecyclerViewAdapter;
import com.example.testcloudfirebase.Model.Meal;
import com.example.testcloudfirebase.Model.PartMeal;
import com.example.testcloudfirebase.Model.Product;
import com.example.testcloudfirebase.ViewHolder.CallbackLoadDatabase;
import com.example.testcloudfirebase.ViewHolder.OnItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.nio.file.Paths.get;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

Button btn;
    FirebaseFirestore db;
    String TAG="MojLog";
    RecyclerView recyclerView;
    ArrayList<Meal> mealArrayList;
    HashMap<String, Product> mapProduct;
    private RecyclerView.LayoutManager layoutManager;
    Product product;
    DocumentReference docRef;
    MealRecyclerViewAdapter adapter;
    ImageButton Ibtn1,Ibtn2;
    FirebaseAuth firebaseAuth;
    ImageView ivPhotoUser;
    TextView tvTotalKcal, tvTotalBialko, tvTotalTluszcz, tvTotalWegle, tvWczoraj, tvDzisiaj,tvJutro,tvEmail,tvName;
    int day;
    int month;
    int year;
    int offset;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Double zapotrzebowanieKcal,zapotrzebowanieBialko, zapotrzebowanieTluszcze, zapotrzebowanieWegle;
User user;
    static final int REQUEST_IMAGE_CAPTURE = 1;

ProgressBar pBarKcal,pBarBialko,pBarTluszcze,pBarWegle;


Meal meal;
    Product p;
    Product p2;
    Product p3;
    PartMeal pp1;
    PartMeal pp2;
    PartMeal pp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTotalKcal=findViewById(R.id.tvTotalKcal);
        tvTotalBialko=findViewById(R.id.tvTotalBialko);
        tvTotalTluszcz=findViewById(R.id.tvTotalTluszcz);
        tvTotalWegle=findViewById(R.id.tvTotalWegle);
        pBarKcal=findViewById(R.id.progressBarKcal);
        pBarBialko=findViewById(R.id.progressBarBialko);
        pBarTluszcze=findViewById(R.id.progressBarTluszcze);
        pBarWegle=findViewById(R.id.progressBarWegle);

        offset=0;
        setButtonDate();
       setTextOnButtonDate(0);
        mealArrayList= new ArrayList<>();
        mapProduct= new HashMap<>();
//addtoarray();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        tvEmail=navigationView.getHeaderView(0).findViewById(R.id.textView2);
        tvName=navigationView.getHeaderView(0).findViewById(R.id.textViewUsername);
        ivPhotoUser=navigationView.getHeaderView(0).findViewById(R.id.imageView);
        tvEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        tvName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        Picasso.get().load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).transform(new PicassoCircleTransformation()).into(ivPhotoUser);





        setUpRecyclerView();
        setUpFirebase();
LoadUser();
   /*     loadDataFromFirestore2(GetStringDate(offset),new CallbackLoadDatabase() {
            @Override
            public void onCallback() {
               // SumAllMeal();
            }
        });*/
//btn=findViewById(R.id.button);

        tvDzisiaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // getActivity().grantUriPermission("com.example.testcloudfirebase", photoURI,Intent.FLAG_GRANT_READ_URI_PERMISSION);


                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }

                }

        });





        adapter = new MealRecyclerViewAdapter(MainActivity.this, mealArrayList, mapProduct, new OnItemClickListener() {
            @Override
            public void onAddClick(String idDoc) {
               // Toast.makeText(MainActivity.this, "kliknieto:"+position, Toast.LENGTH_SHORT).show();
                try {
                    Intent k = new Intent(getApplicationContext(), TabActivity.class);
                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("idDay",GetStringDate(offset));
                    dataBundle.putString("idMeal", idDoc);
                    dataBundle.putString("title",tvDzisiaj.getText().toString());
                    k.putExtras(dataBundle);
                    startActivity(k);

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDeleteClick(String idDay, String idDoc, String idProd) {
                DeletePartMeal(idDay,idDoc,idProd);
            }

            @Override
            public void OnAddProd(String idProduct) {

            }
        });
                //adapter.setClickListener(this);
                recyclerView.setAdapter(adapter);

       // adapter.setOn
       // adapter.notifyDataSetChanged();

       // recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvWczoraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offset+=-1;
                setTextOnButtonDate(offset);
                loadDataFromFirestore(GetStringDate(offset),new CallbackLoadDatabase() {
                    @Override
                    public void onCallback() {
                        SumAllMeal();
                    }
                });
            }
        });

        tvJutro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offset+=1;
                setTextOnButtonDate(offset);
                loadDataFromFirestore(GetStringDate(offset), new CallbackLoadDatabase() {
                    @Override
                    public void onCallback() {
                        SumAllMeal();
                    }
                });
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if(menuItem.getItemId() == R.id.home){
            drawer.closeDrawers();
        }else if(menuItem.getItemId() == R.id.settings)
        {
            drawer.closeDrawers();
            startActivity(new Intent(this, SettingsActivity.class));
        } else if(menuItem.getItemId() == R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,Login.class));
            finish();
        }
        return true;
    }
    @Override
    public void onResume(){
        super.onResume();
        loadDataFromFirestore(GetStringDate(offset),new CallbackLoadDatabase() {
            @Override
            public void onCallback() {
                // SumAllMeal();
                 LoadUser();
            }
        });

    }

    public void showlistmeal() {

Log.i(TAG,"size"+mealArrayList.get(0).getLista().size());
        for(Meal m: mealArrayList)
        {
           // System.out.print(m.getName()+" "+m.getId());
            Log.e(TAG, m.getName()+" "+m.getId());
            for(PartMeal p: m.getLista())
            {
               // System.out.print(p.getProdukt()+" "+p.getWaga());
              //  Log.i(TAG, p.getProdukt()+" "+p.getWaga());
            }

        }
    }

    private  void addtoarray()
    {
        ArrayList<PartMeal> partMeals=new ArrayList<>();
       // partMeals.add(new PartMeal(100,"sadcz"));
       // partMeals.add(new PartMeal(100,"sadcz"));
        mealArrayList.add(new Meal("naz1", 12, partMeals));
        mealArrayList.add(new Meal("naz1", 12, partMeals));
        mealArrayList.add(new Meal("naz1", 12, partMeals));
    }


    private void loadDataFromFirestore(String date, CallbackLoadDatabase callbackLoadDatabase) {
        mealArrayList.clear();
        final List<String> list = Arrays.asList("Śniadanie", "Śniadnie II", "Lunch", "Obiad", "Kolacja" );
        for(final String s: list) {
            db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("Day")
                    .document(date)
                    .collection(s)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            Log.e(TAG, task.getResult().size() + "");
                            ArrayList<PartMeal> partMeals=new ArrayList<>();
                            for (DocumentSnapshot snapshot : task.getResult()) {
                                partMeals.add(new PartMeal(Double.valueOf(snapshot.get("waga") + "")
                                        ,(String)snapshot.get("idProduktu"),snapshot.getId(),snapshot.get("idDay")+""));
                                getProductFromFirebase((String)snapshot.get("idProduktu"),callbackLoadDatabase);
                            }
                            Meal m=new Meal(s,list.indexOf(s),partMeals);
                            mealArrayList.add(m);
                            mealArrayList.sort((Meal m1, Meal m2) ->{
                                if (m1.getId()>m2.getId())
                                    return 1;
                                if (m1.getId()<m2.getId())
                                    return -1;
                                return 0;
                            });
                            adapter.notifyDataSetChanged();
                            callbackLoadDatabase.onCallback();
                        }
                    });
        }
    }

    private void getProductFromFirebase(final String id, CallbackLoadDatabase callbackLoadDatabase)
    {

        docRef = db.collection("Products").document(id);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    product=new Product();
                    product.setNazwa((String)snapshot.get("nazwa"));
                    Log.e(TAG,"jurus "+snapshot.get("wartosc_energetyczna")+" "+snapshot.get("nazwa")+" "+id);

                    product.setKcal(Double.parseDouble(snapshot.get("wartosc_energetyczna").toString()));
                    product.setIdProduct(snapshot.getId());
                    product.setWartoscEnergetyczna(Double.parseDouble(snapshot.get("wartosc_energetyczna").toString()));
                    product.setTluszcz(Double.parseDouble(snapshot.get("tluszcz").toString()));
                    product.setTluszczNasycony(Double.parseDouble(snapshot.get("tluszcze_nasycone").toString()));
                    product.setWeglowodany(Double.parseDouble(snapshot.get("weglowodany").toString()));
                    product.setWeglowodanyWTymCukry(Double.parseDouble(snapshot.get("cukier").toString()));
                    product.setBialko(Double.parseDouble(snapshot.get("bialko").toString()));
                    product.setSol(Double.parseDouble(snapshot.get("sol").toString()));

                    mapProduct.put(id,product);
                    Log.e(TAG,"jurus2 "+product.getNazwa()+" "+product.getIdProduct()+" "+id);
                } else {
                    Log.d(TAG, "Current data: null");
                    //return;
                }
                adapter.notifyDataSetChanged();
                callbackLoadDatabase.onCallback();
            }
        });



    }
    private void AddToFirebase(Product p)
    {
        Map<String, Object> docData = new HashMap<>();
        docData.put("nzawa", p.getNazwa());
        docData.put("wartosc_energetyczna", p.getWartoscEnergetyczna());
        docData.put("tluszcz", p.getTluszcz());
        docData.put("tluszcze_nasycone", p.getTluszczNasycony());
        docData.put("weglowodany", p.getWeglowodany());
        docData.put("cukry", p.getWeglowodanyWTymCukry());
        docData.put("bialko", p.getBialko());
        docData.put("sol", p.getSol());
        db.collection("Products").document().set(docData);

    }


    private void setUpFirebase() {
        db = FirebaseFirestore.getInstance();
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.meal_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void DeletePartMeal(String Day, String Meal, String Prod){
        db.collection("Users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Day")
                .document(Day)
                .collection(Meal)
                .document(Prod)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "usunięto!"+Day+"  "+Meal+"  "+Prod, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "nie usunieto!", Toast.LENGTH_SHORT).show();
                    }
                });
        loadDataFromFirestore(GetStringDate(offset),new CallbackLoadDatabase() {
            @Override
            public void onCallback() {
                SumAllMeal();

            }
        });
        //adapter.notifyDataSetChanged();
        //adapter.reloadAdapter2();

    }

    private void SumAllMeal(){
        Double kcal,bialko,tluszcz,wegle;
        kcal=bialko=tluszcz=wegle=0.0;
        DecimalFormat dec = new DecimalFormat("#0.0");
        Log.e("Moj", "jestem w "+mealArrayList.size());
        for(Meal m: mealArrayList){
            Log.e("Moj", "jestem w pendli");
            for (PartMeal p: m.getLista()) {
                Log.e("Moj", "jestem w pendli-----");
                if (mapProduct.containsKey(p.getProduktID())) {
                    Log.e("Moj", "jestem w petli lalalal" + mapProduct.get(p.getProduktID()).getNazwa() + " " + mapProduct.size());
                    kcal += mapProduct.get(p.getProduktID()).getKcal() * p.getWaga() / 100;
                    bialko += mapProduct.get(p.getProduktID()).getBialko() * p.getWaga() / 100;
                    tluszcz += mapProduct.get(p.getProduktID()).getTluszcz() * p.getWaga() / 100;
                    wegle += mapProduct.get(p.getProduktID()).getWeglowodany() * p.getWaga() / 100;

                }

            }
            }

            tvTotalKcal.setText("Kalorie\n"+(int)Math.round(kcal)+"kcal\n/"+(int)Math.round(zapotrzebowanieKcal)+"kcal");
            tvTotalBialko.setText("Białko\n"+dec.format(bialko)+"g\n/"+dec.format(zapotrzebowanieBialko)+"g");
            tvTotalTluszcz.setText("Tłuszcze\n"+dec.format(tluszcz)+"g\n/"+dec.format(zapotrzebowanieTluszcze)+"g");
            tvTotalWegle.setText("Węglowodany\n"+dec.format(wegle)+"g\n/"+dec.format(zapotrzebowanieWegle)+"g");
       // pBarWegle=new ProgressBar(getApplicationContext(),null,R.style.progressBarBlue);
  /*      pBarKcal.setMax((int)Math.round(zapotrzebowanieKcal));
        pBarKcal.setProgress((int)Math.round(kcal));
        pBarBialko.setMax((int)Math.round(zapotrzebowanieBialko));
        pBarBialko.setProgress((int)Math.round(bialko));
        pBarTluszcze.setMax((int)Math.round(zapotrzebowanieTluszcze));
        pBarTluszcze.setProgress((int)Math.round(tluszcz));
        pBarWegle.setMax((int)Math.round(zapotrzebowanieWegle));
        pBarWegle.setProgress((int)Math.round(wegle));*/
  setColorBar(pBarKcal,(int)Math.round(zapotrzebowanieKcal),(int)Math.round(kcal));
  setColorBar(pBarBialko,(int)Math.round(zapotrzebowanieBialko),(int)Math.round(bialko));
  setColorBar(pBarTluszcze,(int)Math.round(zapotrzebowanieTluszcze),(int)Math.round(tluszcz));
  setColorBar(pBarWegle,(int)Math.round(zapotrzebowanieWegle),(int)Math.round(wegle));




    }
    public void setColorBar(ProgressBar bar,int max,int progress){
        if(progress<=1.1*max && progress>=0.9*max)
            bar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorGreenLight), android.graphics.PorterDuff.Mode.SRC_IN);
        else if(progress>1.1*max)
            bar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        else
            bar.getProgressDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

        bar.setMax(max);
        bar.setProgress(progress);


    }

    public String GetStringDate(int offsetDate){
        DecimalFormat dec = new DecimalFormat("#00");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,offsetDate);
        day= calendar.get(Calendar.DAY_OF_MONTH);
         month=calendar.get(Calendar.MONTH)+1;
         year=calendar.get(Calendar.YEAR);

        String s=day+""+dec.format(month)+""+year;
                        //calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.getDefault()));
        return s;

    }


    public void setButtonDate(){
        tvWczoraj=findViewById(R.id.tvWczoraj);
        tvDzisiaj=findViewById(R.id.tvDzisiaj);
        tvJutro=findViewById(R.id.tvJutro);
    }

    public void setTextOnButtonDate(int setoff){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,setoff-1);
        tvWczoraj.setText(calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault()));
        calendar.add(Calendar.DATE,1);
        tvDzisiaj.setText(calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault()));
        calendar.add(Calendar.DATE,1);
        tvJutro.setText(calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault()));



    }

    public void LoadUser(){
        user=new User();
        db.collection("Users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()&&document.get("aktywnosci")!=null) {
                       user.setPlec(document.get("plec").toString());
                       user.setAktywnosc(Integer.parseInt(document.get("aktywnosci").toString()));
                       user.setCel(Integer.parseInt(document.get("cel").toString()));
                       user.setWiek((int)Double.parseDouble(document.get("wiek").toString()));
                       user.setWaga((double)document.get("waga"));
                       user.setWzrost((double)document.get("wzrost"));
                        ObliczZapotzrebowanie();

                    } else {
                        startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                        finish();
                    }
                }else{
                    Log.d(TAG, "No succes");
                }

            }
        });


    }



public void ObliczZapotzrebowanie(){
        Double podstawa,Xwaga,Ywzrost,Zwiek,bmr,cpm;
        if(user.plec.equals("male")){
            podstawa=66.0;
            Xwaga=13.7;
            Ywzrost=5.0;
            Zwiek=6.76;
        }else{
            podstawa=655.0;
            Xwaga=9.6;
            Ywzrost=1.8;
            Zwiek=4.7;
        }
        bmr=podstawa+Xwaga*user.getWaga()+Ywzrost*user.getWzrost()+Zwiek*user.getWiek();
        cpm=bmr*(1.2+0.2*user.getAktywnosc());
        switch (user.getCel()){
            case 0:
                zapotrzebowanieKcal=cpm-300;
               break;
            case 1:
                zapotrzebowanieKcal=cpm;
                break;
            case 2:
                zapotrzebowanieKcal=cpm+300;
                break;
        }
        zapotrzebowanieBialko=(zapotrzebowanieKcal*0.25)/4;
        zapotrzebowanieTluszcze=(zapotrzebowanieKcal*0.25)/9;
        zapotrzebowanieWegle=(zapotrzebowanieKcal*0.5)/4;
    SumAllMeal();


    }

}



