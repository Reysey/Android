package com.reydev.tuto.androiddatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class PharmacieAvcCodePostalActivity extends AppCompatActivity {

    public AutoCompleteTextView code;
    public AutoCompleteTextView nom;
    public Button query_button ;
    public Button img_start_btn;
    ArrayAdapter<String> ad_code;
    ArrayAdapter<String> ad_nom;
    ListView lv;
    ArrayList<HashMap<String,String>> ar;
    SimpleAdapter ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacie_avc_code_postal);

        getSupportActionBar().setTitle("Trouver la pharmacie avec le codepostal ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayList<HashMap<String,String>>  arl = new ArrayList<>();

        // Toaster !!

        query_button = findViewById(R.id.btn_recherche);
        ar = new ArrayList<HashMap<String, String>>();

        code= findViewById(R.id.autoCompleteTextViewCodepostal_am);

        nom= findViewById(R.id.autoCompleteTextViewName_am);
        img_start_btn= findViewById(R.id.btn_restart);

        lv= findViewById(R.id.listeR_A);
        //etape 1 :Préparer la source de données

        ad=new SimpleAdapter(getApplicationContext(),ar,R.layout.ligne,new String[]{"name","codepostal"},
                new int[]{R.id.nameph,R.id.codepostalph});

        lv.setAdapter(ad);

        com.reydev.tuto.androiddatabase.DatabaseAccess databaseAccessPh = com.reydev.tuto.androiddatabase.DatabaseAccess.getInstance(getApplicationContext());
        databaseAccessPh.open();
        ad_code = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,databaseAccessPh.reqcodepostalph());
        code.setAdapter(ad_code);


        query_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ArrayList<Pharmacie>> listePharmacies = new ArrayList<>();

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                try{

                    String codepostalph = code.getText().toString();

                    String nomph =nom.getText().toString();






                    if(nomph.equals("")&& (!codepostalph.equals(""))){
                        ar.clear();
                        ArrayList<HashMap<String,String>> arl = databaseAccess.getPh_CP(codepostalph);

                        for (HashMap<String,String> s : arl){ar.add(s);}
                        if(ar.size()==0 ){
                            Toast.makeText(getApplicationContext(),"Veuillez verifier vos données saisie !!",Toast.LENGTH_LONG).show();
                        }

                    }


                    if((!nomph.equals("")) && (!codepostalph.equals("")) ){
                        ar.clear();

                        ad=new SimpleAdapter(getApplicationContext(),ar,R.layout.modelitem,new String[]{"name","codepostal","tel","address","longitude","laltitude"},
                                new int[]{R.id.name,R.id.codepostal,R.id.tel,R.id.address,R.id.longitude,R.id.laltitude});

                        lv.setAdapter(ad);



                        // ArrayList<HashMap<String,String>> arl =databaseAccess.getPh_CN(codepostalph,nomph);

                        ArrayList<HashMap<String,String>> arl =databaseAccess.getPhT_CN(nomph,codepostalph);


                        for (HashMap<String,String> s : arl){ar.add(s);}
                        if(ar.size()==0 ){
                            // Toast !!
                        }
                        ad.notifyDataSetChanged();
                    }

                    ad.notifyDataSetChanged();
                    databaseAccess.close();


                    if(codepostalph.equals("")){
                        // Toast !!

                    }


                    for (HashMap<String,String> s : arl){ar.add(s);}

                    ad.notifyDataSetChanged();
                    databaseAccess.close();
                }catch (Exception e){

                    // Toast !!
                }
            }

        });

        code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ar.clear();
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();

                    String codepostal = code.getText().toString();
                    if((!codepostal.equals(""))){
                        ArrayList<HashMap<String,String>> arl = databaseAccess.getPh_CP(codepostal);

                        for (HashMap<String,String> s : arl){ar.add(s);}
                        if(ar.size()>1 ){
                            // Toast !!
                            ar.clear();

                        }else{
                            //Toast !!
                        }}
                    com.reydev.tuto.androiddatabase.DatabaseAccess databaseAccessNom =  com.reydev.tuto.androiddatabase.DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccessNom.open();
                    ad_nom = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,databaseAccessNom.reqnomph(codepostal));
                    nom.setAdapter(ad_nom);

                }

                String nomph =nom.getText().toString();
                Toast.makeText(PharmacieAvcCodePostalActivity.this," "+nomph, Toast.LENGTH_LONG).show();

            }

        });


    }




    private void ecoutMenuRecherche(ImageView btn, final Class classe) {
        btn.setOnClickListener(new ImageView.OnClickListener(){

            public void onClick(View v){

                Intent intent = new Intent( PharmacieAvcCodePostalActivity.this,classe);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity(intent);

            }


        });
    }



    public void clearListe_ph(View v) {

        img_start_btn.setOnClickListener(new ImageView.OnClickListener(){

            public void onClick(View v){

                Toast.makeText(PharmacieAvcCodePostalActivity.this,"C'est  effacé !!! ", Toast.LENGTH_LONG).show();
                ar.clear();
                ad.notifyDataSetChanged();
                lv.setAdapter(ad);
                code.setText("");
                nom.setText("");


            }


        });
    }









}