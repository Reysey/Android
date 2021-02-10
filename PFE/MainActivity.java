package com.reydev.tuto.androiddatabase;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MAINACTIVITY";
    private ArrayList<TitleAndDescription> main_tad_obj;
    private  View org;
    private  View animatedLayout;
    private  View ppBackground;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"white\">"+getString(R.string.app_slogan)+"</font>"));

        checkLocationRequirement();



        org            = (RelativeLayout)findViewById(R.id.origin);
        /* DEV-MODE */ org.setVisibility(View.GONE);
        animatedLayout   = (LinearLayout)findViewById(R.id.AnimatedLayout);
        ppBackground     = (LinearLayout)findViewById(R.id.popup_background);

        // ||       MAIN LISTVIEW PROCESS STARTED
        /* || */
        /* || */    // INIT THE OBJECT.
        /* || */    main_tad_obj = new ArrayList<>();
        /* || */
        /* || */    // GETTING RESOURCE FROM res/values/strings
        /* || */    Resources res               = getResources();
        /* || */    String[] allTitles          = res.getStringArray(R.array.titles);
        /* || */    String[] allDescriptions    = res.getStringArray(R.array.description);
        /* || */
        /* || */    fillTad(allTitles,allDescriptions);
        /* || */
        /* || */    ListView listView = (ListView) this.findViewById(R.id.RD_listview);
        /* || */    TwoItemsListViewAdapter adapter = new TwoItemsListViewAdapter(this, R.layout.activity_listview_contents, main_tad_obj);
        /* || */    listView.setAdapter(adapter);
        /* || */
        /* || */    // SETTING UP THE LIST VIEW
        /* || */    ListView lv = (ListView)findViewById(R.id.RD_listview);
        /* || */    // LIST VIEW ON CLICK AT AN ITEM
        /* || */    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        /* || */    @Override
        /* || */    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent;

                        // TEXT TITILE  - TITLE OF THE OPTION SELCTED
                        TextView txtTitle = (TextView)findViewById(R.id.btn_title);
                        // BTN OPTION1 - List Des Pharmacie.
                        final Button btnO1 = (Button)findViewById(R.id.btn_option1);
                        // BTN OPTION2 - Rechercher Une Pharmacie.
                        Button btnO2 = (Button)findViewById(R.id.btn_option2);
                        // BTN OPTION3 - Pharmacie Approximité.
                        Button btnO3 = (Button)findViewById(R.id.btn_option3);
                        // BTN OPTION3 - Pharmacie Approximité.
                        Button btnO4 = (Button)findViewById(R.id.btn_option4);

                        switch (i){
                             case 0:  /*intent = new Intent(getApplicationContext(), ActivityPharmacie.class);startActivity(intent);*/
                                 // PHARMACIE
                                    // SET THE BUTTONS NAMES
                                    // ---------------------
                                    // ---------------------
                                    // TEXT TITILE  - TITLE OF THE OPTION SELCTED
                                    txtTitle.setText("Recherche Pharmacie");

                                    // BTN OPTION1 - List Des Pharmacie.
                                        btnO1.setVisibility(View.VISIBLE);
                                        btnO2.setVisibility(View.VISIBLE);
                                        btnO3.setVisibility(View.VISIBLE);
                                        btnO4.setVisibility(View.VISIBLE);

                                        btnO1.setText("List Des Pharmacies");
                                        btnO1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                Intent i = new Intent(getApplicationContext(), ActivityPharmacie.class);
                                                i.putExtra("flag", "false");
                                                startActivity(i);
                                            }
                                        });

                                    // BTN OPTION2 - Rechercher Une Pharmacie.
                                        btnO2.setText("Recherche Par Nom/Address");
                                        btnO2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent i = new Intent(getApplicationContext(), ActivityPharmacie.class);
                                                i.putExtra("flag", "true");
                                                startActivity(i);
                                            }
                                        });

                                    // BTN OPTION3 - Pharmacie Approximité.
                                        btnO3.setText("Pharmacie Approximité");
                                        btnO3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                //
                                            }
                                        });

                                         // BTN OPTION4 - RECHERCH PAR CODE POSTAL.
                                         btnO4.setText("Rechercher Par CodePostal");
                                         btnO4.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 startActivity(new Intent(getApplicationContext(),PharmacieAvcCodePostalActivity.class));
                                             }
                                         });


                                 popupOpen(org, animatedLayout, ppBackground);

                             break;
                             case 1:
                                 Intent i2 = new Intent(getApplicationContext(), ActivityDocteur.class);
                                 i2.putExtra("flag", "true");
                                 startActivity(i2);
                                 break;
                             case 2:
                                 Toast.makeText(getApplicationContext(),"Calculer Votre Indice de Masse Corporel(IMC)",Toast.LENGTH_LONG).show();
                                     intent = new Intent(getApplicationContext(), IMC.class);
                                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                     startActivity(intent);
                                 break;
                            default:
                                Toast.makeText(getApplicationContext(),"Error - Restart The App & Try Again.", Toast.LENGTH_LONG).show();
                            }
        /* || */        }
        /* || */    });
        /* || */
        // ||       MAIN LISTVIEW PROCESS ENDED.

        // POPUP MANAGEMENT
        Button btnClose = (Button)findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupClose(org, animatedLayout, ppBackground);
            }
        });


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

                        //startActivity(new Intent(getApplicationContext(),IMC.class));
                        //overridePendingTransition(0,0);
                       Intent intent = new Intent(getApplicationContext(), Avertissement.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                       startActivity(intent);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        return true;
                    case R.id.about:
                        Toast.makeText(getApplicationContext(),"Site Web Utile",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),About.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



    }

    /**
     *  FILL THE MAIN ARRAY LIST WITH OPTIONS.
     * @param titles
     * @param descriptions
     */
    public void fillTad(String[] titles, String[] descriptions){
        int len = titles.length;
        int i = 0;
        for( i = 0; i < len; i++ ){
            main_tad_obj.add(new TitleAndDescription(titles[i], descriptions[i]));
        }
    }

    public void popupOpen(final View org, final View animatedLayout, final View background){
        // SHOW THE OPTIONS POPUP
        //LinearLayout org = (LinearLayout) findViewById(R.id.origin);
        ListView lv = (ListView)findViewById(R.id.RD_listview);
        lv.setVisibility(View.GONE);

        background.setAlpha(0f);
        animatedLayout.setVisibility(View.VISIBLE);
        org.setVisibility(View.VISIBLE); Log.e("SET VISIBILITY", "SET VISIBILITY: VISIBLE");
        background.animate()
                .alpha(0.9f)
                .setDuration(1000)
                .setListener(null);

        //LinearLayout  animatedLayout = (LinearLayout)findViewById(R.id.AnimatedLayout);
        ViewGroup.LayoutParams animatedLayout_params = animatedLayout.getLayoutParams();
        animatedLayout_params.height    = 0;
        animatedLayout_params.width     = ViewGroup.LayoutParams.MATCH_PARENT;
        animatedLayout.setLayoutParams(animatedLayout_params);
//                                                        //animatedLayout.getMeasuredHeight()
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int animationPercentage = (Integer) animator.getAnimatedValue();

                //LinearLayout  animatedLayout = (LinearLayout)findViewById(R.id.AnimatedLayout);
                ViewGroup.LayoutParams animatedLayout_params = animatedLayout.getLayoutParams();
                Log.e("ON ANIMATION", "VALUE SIZE: [  "+animationPercentage+"  ]");
                //LinearLayout org = (LinearLayout)findViewById(R.id.origin);
                ViewGroup.LayoutParams para = org.getLayoutParams();
                Log.e("ON CREATE", "SIZE: [ "+ org.getMeasuredHeight()+" ]" );
                //animatedLayout_params.height =  ViewGroup.LayoutParams.MATCH_PARENT;
                if(animationPercentage < 5){
                    Log.e(" ON ANIMATION" , "SKIP");
                }else
                if(animationPercentage < org.getMeasuredHeight()){
                    animatedLayout_params.height =  animationPercentage;
                }

//                animatedLayout_params.width =  ViewGroup.LayoutParams.MATCH_PARENT;
               animatedLayout.setLayoutParams(animatedLayout_params);
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();
    }

    public void popupClose(final View org, final View animatedLayout, final View background){
                ListView lv = (ListView)findViewById(R.id.RD_listview);
                lv.setVisibility(View.VISIBLE);


                ViewGroup.LayoutParams animatedLayout_params = animatedLayout.getLayoutParams();

                ValueAnimator valueAnimator = ValueAnimator.ofInt(animatedLayout.getMeasuredHeight(), 0);

                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        int animationPercentage = (Integer) animator.getAnimatedValue();
                        Log.e("ON ANIMATION", "VALUE SIZE: [  " + animationPercentage + "  ]");

                        ViewGroup.LayoutParams animatedLayout_params = animatedLayout.getLayoutParams();
                        animatedLayout_params.height = animationPercentage;

                        if (animationPercentage == 0) {

                            animatedLayout.setVisibility(View.GONE);
                            background.setAlpha(0.9f);
                            background.animate()
                                    .alpha(0f)
                                    .setDuration(250)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            org.setVisibility(View.GONE);
                                            //Intent i = new Intent(getApplicationContext(), MainActivity.class);startActivity(i);
                                        }
                                    });

                        }
                        findViewById(R.id.AnimatedLayout).setLayoutParams(animatedLayout_params);
                    }
                });
                valueAnimator.setDuration(500);
                valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                valueAnimator.start();
    }


    // EOC



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exempl_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
           /* case R.id.item0:
                Intent i= new Intent(MainActivity.this,findPlacesActivity.class);
                startActivity(i);
                return true;

            */
            case R.id.item1:
                //Intent ii= new Intent(MainActivity.this,PharmacieAvcCodePostalActivity.class);
                //startActivity(ii);
                //Intent iii= new Intent(MainActivity.this,About.class);
               // startActivity(iii);
                Toast.makeText(this, "Kinesitherapeute", Toast.LENGTH_SHORT).show();
                Intent i =new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.santeannonces.ma/annonce-detail/rabat/services-divers.12263/kinesitherapeute-a-domicile-rabat-temara.html"));
                startActivity(i);
                return true;
            case R.id.item2:
                //Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, " Laboratoire", Toast.LENGTH_SHORT).show();
                Intent ii =new Intent(Intent.ACTION_VIEW);
                ii.setData(Uri.parse("https://www.emploi.ma/recruteur/2217637"));
                startActivity(ii);
                return true;
            case R.id.item3:
                //Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Radiologue", Toast.LENGTH_SHORT).show();
                Intent iii =new Intent(Intent.ACTION_VIEW);
                iii.setData(Uri.parse("https://www.med.tn/docteur-maroc/radiologue/temara/temara-massira/"));
                startActivity(iii);
                return true;
                /*
            case R.id.subitem1:
                Toast.makeText(this, "Sub Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem2:
                Toast.makeText(this, "Sub Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;

                 */
            default:return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This Method checks if the phone localisation feature is enabled.
     * And Request the user to enable it, in case localisation feature is disabled.
     */
    private void checkLocationRequirement() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);


        SettingsClient client = LocationServices.getSettingsClient(this);

        //  check whether current location settings are satisfied
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try{

                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Log.e(TAG, "checkLocationRequirement > location is enabled.");
                }catch(ApiException exception){
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        MainActivity.this,
                                        1);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MainActivity.this, 1);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });

    }

}