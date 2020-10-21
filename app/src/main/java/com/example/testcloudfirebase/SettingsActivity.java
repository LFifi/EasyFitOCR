package com.example.testcloudfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    TextView textViewName;
    Spinner  spinnerAktywnosc,spinnerCel;
    ImageView imageViewUser;
    EditText editTextWiek, editTextWzrost, editTextWaga;
    String[] optionsAktywnosc= {"brak aktywności","niska aktywność","średnia aktywność","wysoka aktywność","bardzo wysoka aktywność"};
    String[] optionsCel= {"schudnąć","utrzymać wage","przytyć"};
    RadioGroup radioGroup;
    RadioButton radioButton,rbMale,rbFemale;
    Button buttonSave;
    int spinnerAktywnoscSelected,spinnerCelSelected;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        spinnerAktywnosc = findViewById(R.id.spinnerAktywnosc);
        spinnerCel = findViewById(R.id.spinnerCel);
        imageViewUser = findViewById(R.id.imageViewUser);
        textViewName = findViewById(R.id.textViewName);
        editTextWiek = findViewById(R.id.editTextWiek);
        editTextWzrost = findViewById(R.id.editTextWzrost);
        editTextWaga = findViewById(R.id.editTextWaga);
        radioGroup = findViewById(R.id.radioGroup);
        buttonSave = findViewById(R.id.buttonSave);
        imageViewUser = findViewById(R.id.imageViewUser);
        textViewName = findViewById(R.id.textViewName);
        rbFemale = findViewById(R.id.radioButtonFemale);
        rbMale = findViewById(R.id.radioButtonMale);
        textViewName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        Picasso.get().load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).transform(new PicassoCircleTransformation()).into(imageViewUser);

        db=FirebaseFirestore.getInstance();
        
        ArrayAdapter adapterAkrywnosc = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,optionsAktywnosc);
        adapterAkrywnosc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAktywnosc.setAdapter(adapterAkrywnosc);
        spinnerAktywnosc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerAktywnoscSelected=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapterCel = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,optionsCel);
        adapterCel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCel.setAdapter(adapterCel);
        spinnerCel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerCelSelected=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckEditText())
                {

                    int id = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(id);
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("plec", radioButton.getText());
                    docData.put("waga", Double.parseDouble(editTextWaga.getText().toString()));
                    docData.put("wzrost",Double.parseDouble(editTextWzrost.getText().toString()) );
                    docData.put("wiek", Double.parseDouble(editTextWiek.getText().toString()));
                    docData.put("aktywnosci", spinnerAktywnoscSelected);
                    docData.put("cel", spinnerCelSelected);

                    db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("save", "ok");
                            Toast.makeText(getApplicationContext(), "save", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
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
        });
   Load();
    }

    public Boolean CheckEditText(){
        if(editTextWaga.getText().toString().isEmpty()){
            editTextWaga.setError(getString(R.string.emptyEditText));
            return false;
        }
        if(editTextWzrost.getText().toString().isEmpty()){
            editTextWzrost.setError(getString(R.string.emptyEditText));
            return false;
        }
        if(editTextWiek.getText().toString().isEmpty()){
            editTextWiek.setError(getString(R.string.emptyEditText));
            return false;
        }
        return true;
    }

    public void Load(){
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
                                setData();

                            } else {

                            }
                        }else{

                        }

                    }
                });

    }

    public void setData(){
        editTextWzrost.setText(user.getWzrost().toString());
        editTextWiek.setText(user.getWiek()+"");
        editTextWaga.setText(user.getWaga().toString());
        if(rbMale.getText().equals(user.getPlec())){
            radioGroup.check(rbMale.getId());
        }else if(rbFemale.getText().equals(user.getPlec())){
            radioGroup.check(rbFemale.getId());
        }
        spinnerAktywnosc.setSelection(user.getAktywnosc());
        spinnerCel.setSelection(user.getCel());

    }

}