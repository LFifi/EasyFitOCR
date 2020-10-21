package com.example.testcloudfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testcloudfirebase.Model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class AddingProductActivity extends AppCompatActivity {

    FirebaseFirestore db;
    public Button btn;
    EditText waga;
    String idDay,idMeal,idProduct;
    TextView tvNazwa,tvWartoscEnergetyczna,tvTluszcz,tvNasycone,tvWegle,tvCukry,tvBialko,tvSol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_product);
        db = FirebaseFirestore.getInstance();
        waga = findViewById(R.id.etWaga);
        Bundle extras = getIntent().getExtras();
        idDay=extras.getString("idDay");
        idMeal = extras.getString("idMeal");
        idProduct= extras.getString("idProduct");
        getSupportActionBar().setTitle(extras.getString("title"));
        //getSupportActionBar().setTitle(a+"  "+aa);
        setTextView();
        setTextViewFromFirestone();
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePartMeal();
            }
        });


    }

    public  void savePartMeal(){
        if(!waga.getText().toString().isEmpty()){
            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();
            Map<String, Object> docData = new HashMap<>();
            docData.put("idDay", idDay);
            docData.put("idDoc",ts );
            docData.put("idProduktu", idProduct);
            docData.put("waga", Double.parseDouble(waga.getText().toString()));
            db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("Day").document(idDay).collection(idMeal).document(ts).set(docData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("save", "ok");
                    Toast.makeText(getApplicationContext(), "Dodano", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("save", "error");
                }
            });
        }
    }
    public void setTextViewFromFirestone(){
        db.collection("Products")
                .document(idProduct).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("mTAG", "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    tvNazwa.setText((String)snapshot.get("nazwa"));
                    tvWartoscEnergetyczna.setText(snapshot.get("wartosc_energetyczna").toString());
                    tvTluszcz.setText(snapshot.get("tluszcz").toString());
                    tvNasycone.setText(snapshot.get("tluszcze_nasycone").toString());
                    tvWegle.setText(snapshot.get("weglowodany").toString());
                    tvCukry.setText(snapshot.get("cukier").toString());
                    tvBialko.setText(snapshot.get("bialko").toString());
                    tvSol.setText(snapshot.get("sol").toString());
                } else {
                }
            }
        });
    }



    public void setTextView(){
        tvNazwa=findViewById(R.id.tv_nazwa);
        tvWartoscEnergetyczna=findViewById(R.id.tv_wartoscEnergetyczna);
        tvTluszcz=findViewById(R.id.tv_tluszcze);
        tvNasycone=findViewById(R.id.tv_nasycone);
        tvWegle=findViewById(R.id.tv_wegle);
        tvCukry=findViewById(R.id.tv_cukry);
        tvBialko=findViewById(R.id.tv_bialko);
        tvSol=findViewById(R.id.tv_sol);
    }
}