package com.reydev.tuto.androiddatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Avertissement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avertissement);

        getSupportActionBar().setTitle("Avertissement :");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashbaord:
                        Toast.makeText(getApplicationContext(),"Avertissement!!",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.home:
                        //startActivity(new Intent(getApplicationContext(),IMC.class));
                        //overridePendingTransition(0,0);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(intent);
                        return true;
                    case R.id.about:
                        Toast.makeText(getApplicationContext(),"Site Web Utile",Toast.LENGTH_LONG).show();
                        Intent intentt = new Intent(getApplicationContext(),About.class);
                        intentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(intentt);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}