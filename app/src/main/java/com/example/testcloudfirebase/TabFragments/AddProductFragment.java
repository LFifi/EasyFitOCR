package com.example.testcloudfirebase.TabFragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testcloudfirebase.LongestCommonSubstring;
import com.example.testcloudfirebase.Model.Product;
import com.example.testcloudfirebase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment {

    EditText name,wartoscNa,wartoscEnergetyczna,tluszcz,nasycone,weglowodany,cukry,bialko,sol;
    Button takPhoto,save;
    View view;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    public static final Double NaN = -9999.0;
    static final int CROP_PIC = 2;
    Bitmap imageBitmap;
    String currentPhotoPath;
    File photoFile, photoFile2;
    Uri photoURI;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_add_product, container, false);
setAllEditText();
setAllButton();
takPhoto.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       dispatchTakePictureIntent();


    }
});
save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if(checkAllEditText()) {
            Toast.makeText(getContext(), "kliknieto", Toast.LENGTH_LONG).show();
            setDataInFirestone();

        }
    }
});
         return view;
    }

    public void setAllEditText(){
        name=view.findViewById(R.id.name_product);
        wartoscNa=view.findViewById(R.id.wartosc_na);
        wartoscEnergetyczna=view.findViewById(R.id.wartosc_energetyczna);
        tluszcz=view.findViewById(R.id.tluszcze);
        nasycone=view.findViewById(R.id.nasycone);
        weglowodany=view.findViewById(R.id.weglowodany);
        cukry=view.findViewById(R.id.cukry);
        bialko=view.findViewById(R.id.bialka);
        sol=view.findViewById(R.id.sol);
    }

    public void setAllButton(){
        takPhoto=view.findViewById(R.id.zdjecieBtn);
        save=view.findViewById(R.id.save);
    }

    public boolean checkAllEditText(){
        if(name.getText().toString().isEmpty()){
            name.setError(getString(R.string.emptyEditText));
            return false;
        }
        if(wartoscNa.getText().toString().isEmpty()){
            wartoscNa.setError(getString(R.string.emptyEditText));
            return false;
        }
        if(wartoscEnergetyczna.getText().toString().isEmpty()){
            wartoscEnergetyczna.setError(getString(R.string.emptyEditText));
            return false;
        }
        if(tluszcz.getText().toString().isEmpty()){
            tluszcz.setError(getString(R.string.emptyEditText));
            return false;
        }
        if(nasycone.getText().toString().isEmpty()){
            nasycone.setError(getString(R.string.emptyEditText));
            return false;
        }
        if(weglowodany.getText().toString().isEmpty()){
            weglowodany.setError(getString(R.string.emptyEditText));
            return false;
        }
        if(cukry.getText().toString().isEmpty()){
            cukry.setError(getString(R.string.emptyEditText));
            return false;
        }
        if(bialko.getText().toString().isEmpty()){
            bialko.setError(getString(R.string.emptyEditText));
            return false;
        }
        if(sol.getText().toString().isEmpty()){
            sol.setError(getString(R.string.emptyEditText));
            return false;
        }
        return true;
    }

    public void setDataInFirestone(){
        FirebaseFirestore db;
        db=FirebaseFirestore.getInstance();
        double waga=Double.parseDouble(wartoscNa.getText().toString());
        Map<String, Object> docData = new HashMap<>();
        docData.put("nazwa", name.getText().toString());
        docData.put("wartosc_energetyczna",Double.parseDouble((wartoscEnergetyczna.getText().toString()))*100/waga);
        docData.put("tluszcz", Double.parseDouble((tluszcz.getText().toString()))*100/waga);
        docData.put("tluszcze_nasycone", Double.parseDouble((nasycone.getText().toString()))*100/waga);
        docData.put("weglowodany", Double.parseDouble((weglowodany.getText().toString()))*100/waga);
        docData.put("cukier", Double.parseDouble((cukry.getText().toString()))*100/waga);
        docData.put("bialko", Double.parseDouble((bialko.getText().toString()))*100/waga);
        docData.put("sol", Double.parseDouble(sol.getText().toString())*100/waga);
        Toast.makeText(getContext(), "kliknieto", Toast.LENGTH_LONG).show();
        db.collection("Products").document().set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               // Toast.makeText(getContext(),"Dodano: "+name.getText().toString(),Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Nie Dodano: "+name.getText().toString(),Toast.LENGTH_LONG).show();

            }
        });

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            photoFile=null;
            try{
                photoFile=createImageFile();
            }catch (IOException ex)
            {
                Log.e("TAG","error:"+ex);
            }
            if(photoFile!=null)
            {
                photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.testcloudfirebase.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                this.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }}
        }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
           performCrop();
        }
        if (requestCode == CROP_PIC && resultCode == Activity.RESULT_OK) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize=1;
            imageBitmap= BitmapFactory.decodeFile(String.valueOf(photoFile),bmOptions);
            detectTextFromImage();

        }


    }



    private void detectTextFromImage() {
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        firebaseVisionTextRecognizer.processImage(firebaseVisionImage)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                textFromImage(firebaseVisionText); }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error: ",e.getMessage());
            }
        }); }

    private void textFromImage(FirebaseVisionText firebaseVisionText) {
        Product prod = new Product();
        Point p = null;
        double wartoscOdzywcza=0;
        LongestCommonSubstring obj = new LongestCommonSubstring();
        List<FirebaseVisionText.TextBlock> blockList = firebaseVisionText.getTextBlocks();
        if (blockList.size() == 0) {
        } else {
            for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
                for (FirebaseVisionText.Line line : block.getLines())
                    for (FirebaseVisionText.Element elm : line.getElements()) {
                        if (obj.lcsByString(elm.getText(), "tluszcz") > 4 ) {
                            p = new Point(elm.getCornerPoints()[0]);
                            double temp = GetValueFromFirebaseVision(blockList,p);
                            if(prod.getTluszcz()<temp)
                                prod.setTluszcz(temp);
                        }
                        else if (obj.lcsByString(elm.getText(), "nasycone") > 6) {
                            p = new Point(elm.getCornerPoints()[0]);
                            prod.setTluszczNasycony(GetValueFromFirebaseVision(blockList,p));
                        }
                        else if (obj.lcsByString(elm.getText(), "weglowodany") > 9) {
                            p = new Point(elm.getCornerPoints()[0]);
                            prod.setWeglowodany(GetValueFromFirebaseVision(blockList,p));
                        }
                        else if (obj.lcsByString(elm.getText(), "cukry") > 3) {
                            p = new Point(elm.getCornerPoints()[0]);
                            prod.setWeglowodanyWTymCukry(GetValueFromFirebaseVision(blockList,p));
                        }
                        else if (obj.lcsByString(elm.getText(), "bialko") > 4) {
                            p = new Point(elm.getCornerPoints()[0]);
                            prod.setBialko(GetValueFromFirebaseVision(blockList,p));
                        }
                        else if (obj.lcsByString(elm.getText(), "sól") >1) {
                            p = new Point(elm.getCornerPoints()[0]);
                            prod.setSol(GetValueFromFirebaseVision(blockList,p));
                        }
                        else if (obj.lcsByString(elm.getText(), "energetyczna") >10) {
                            p = new Point(elm.getCornerPoints()[0]);
                            prod.setKcal(GetValueFromFirebaseVisionKcl(blockList,p));
                        }
                        else if (obj.lcsByString(elm.getText(), "odzywcza") >5) {
                            p = new Point(elm.getCornerPoints()[0]);
                            wartoscOdzywcza=GetValueFromFirebaseVision(blockList,p);
                        }
                    }
            }
        }
        setEditTextFromProduct(prod,wartoscOdzywcza);
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private double GetValueFromFirebaseVision(List<FirebaseVisionText.TextBlock> firebaseVisionText, Point p) {
        LongestCommonSubstring obj = new LongestCommonSubstring();
        for (FirebaseVisionText.TextBlock block2 : firebaseVisionText) {
            for (FirebaseVisionText.Line line2 : block2.getLines()){
                for (FirebaseVisionText.Element elm2 : line2.getElements()) {
                    Log.i("mojlog",elm2.getText()+";"+elm2.getCornerPoints()[0].y+";"+p.y);
                    if (p != null && elm2.getCornerPoints()[0].y > (p.y - imageBitmap.getHeight()/20) && elm2.getCornerPoints()[0].y < (p.y + imageBitmap.getHeight()/20)) {
                        Log.i("mojlog4",elm2.getText()+";"+elm2.getCornerPoints()[0].y+";"+p.y);
                        if (obj.isDouble(elm2.getText()) ||
                                (elm2.getText().length()>1 && obj.isDouble(elm2.getText().substring(0, elm2.getText().length() - 1)))) {

                            return obj.returnDouble(elm2.getText());

                        }

                    }

                }
            }}
        return -9999;
    }

    private double GetValueFromFirebaseVisionKcl(List<FirebaseVisionText.TextBlock> firebaseVisionText, Point p) {
        LongestCommonSubstring obj = new LongestCommonSubstring();
        double doubleToReturn=9999.0;
        for (FirebaseVisionText.TextBlock block2 : firebaseVisionText) {
            for (FirebaseVisionText.Line line2 : block2.getLines()){
                for (FirebaseVisionText.Element elm2 : line2.getElements()) {
                    Log.i("mojlog",elm2.getText()+";"+elm2.getCornerPoints()[0].y+";"+p.y);
                    if (p != null && elm2.getCornerPoints()[0].y > (p.y - 40) && elm2.getCornerPoints()[0].y < (p.y + 40)) {
                        Log.i("loggg",elm2.getText());
                        String temp=elm2.getText().replaceAll("[^0-9]+", "");
                        Log.i("loggg2",temp+"long:"+temp.length());
                        if(temp.length()>1) {
                            if (Double.parseDouble(temp) < doubleToReturn) ;
                            doubleToReturn = Double.parseDouble(temp);
                        }
                    }

                }

            }
        }
        return NaN;
    }



    private void performCrop() {
        try {
            photoFile2=createImageFile();
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            Uri contentUri  = FileProvider.getUriForFile(getContext(),
                    "com.example.testcloudfirebase.fileprovider",
                    photoFile);
            getActivity().getApplicationContext().grantUriPermission("com.android.camera",
                    contentUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            cropIntent.setDataAndType(contentUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("noFaceDetection", true);
            cropIntent.putExtra("return-data", false);
            cropIntent.putExtra ("outputFormat", Bitmap.CompressFormat.JPEG.name ());
            //cropIntent.putExtra (MediaStore.EXTRA_OUTPUT, photoURI);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            this.startActivityForResult(cropIntent, CROP_PIC);
        }
        catch (ActivityNotFoundException e) {
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setEditTextFromProduct(Product p,Double wartoscOdzywczaNa){
        if(wartoscOdzywczaNa!=NaN){
            wartoscNa.setText(wartoscOdzywczaNa+"");
        }

        if(p.getKcal()!=NaN){
            wartoscEnergetyczna.setText(p.getKcal()+"");
        }
        if(p.getTluszcz()!=NaN){
            tluszcz.setText(p.getTluszcz()+"");
        }
        if(p.getTluszczNasycony()!=NaN){
            nasycone.setText(p.getTluszczNasycony()+"");
        }
        if(p.getWeglowodany()!=NaN){
            weglowodany.setText(p.getWeglowodany()+"");
        }
        if(p.getWeglowodanyWTymCukry()!=NaN){
            cukry.setText(p.getWeglowodanyWTymCukry()+"");
        }
        if(p.getBialko()!=NaN){
            bialko.setText(p.getBialko()+"");
        }
        if(p.getSol()!=NaN){
            sol.setText(p.getSol()+"");
        }
    }

    private boolean permissions(List<String> listPermissionsNeeded) {

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return true;
        }
        return false;
    }
}