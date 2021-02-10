package com.reydev.tuto.androiddatabase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.about);
        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashbaord:
                        startActivity(new Intent(getApplicationContext(),IMC.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:

                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.about:

                        return true;
                }
                return false;
            }
        });
    }

    public void ouvrirmap( View v){
        // Ministère de la Santé Page Accueil
        Toast.makeText(getApplicationContext(),"Ma localisation !!",Toast.LENGTH_LONG).show();
        Intent i =new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://www.google.com/maps"));
        startActivity(i);
    }

    public void ouvrirpharmacieannonces( View v){
        // Pharmacie et Annonces
        Toast.makeText(getApplicationContext(),"Pharmacie et Annonces !!",Toast.LENGTH_LONG).show();
        Intent i =new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://pharmacie.ma/annonces"));
        startActivity(i);
    }
    public void ouvrirpharmacie( View v){
        // Tout savoir sur les pharmacies
        Toast.makeText(getApplicationContext(),"Tout savoir sur les pharmacies !!",Toast.LENGTH_LONG).show();
        Intent i =new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://pharmacie.ma/"));
        startActivity(i);
    }


    public void ouvrirfaculte( View v){
        //Faculté de Médecine et de Pharmacie de Rabat
        Toast.makeText(getApplicationContext(),"Faculté de Médecine et de Pharmacie de Rabat !!",Toast.LENGTH_LONG).show();
        Intent i =new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://fmp.um5.ac.ma/"));
        startActivity(i);
    }
}