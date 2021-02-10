package com.reydev.tuto.androiddatabase;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class ActivityDocteur extends AppCompatActivity implements Serializable {
    private static String TAG = "ActivityDocteur";

    private static ArrayList<Docteur> mainDocteurs;
    private RequestQueue mQueue;
    private DocteurListViewAdapter lva;
    private ListView lv;
    private EditText search;

    private String MyPE_MSG= "Docteur's Name";
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionsGranted = false;
    private static double UserLongitude;
    private static double UserLatitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docteur_listview_container);

        getSupportActionBar().setTitle("Recherche Docteur");

        // GET CURRENT  DEVICE LOCATION.
        getDeviceLocation();
        hideSoftKeyboard();

        //THIS METHODE BELLOW DISABLE THE STRICT-MODE THAT SHUT THE APP DOWN IF ENABLED DUE TO A NetworkOnMainThreadException ERROR....
        //https://developer.android.com/reference/android/os/NetworkOnMainThreadException
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // FIRST CALL OF THE LIST VIEW.
        lv = (ListView) findViewById(R.id.app_Doclistview);
        // THE jsonParse Include the management of all needed resources to display the results.
        jsonParse(null);


        // SEARCH BAR: ON TEXT CHANGE IT WILL REFRESH LISTVIEW'S DATA.
        search = (EditText)findViewById(R.id.et_DocSearchByName);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if(editable.length() > 0) {
                search = (EditText) findViewById(R.id.et_DocSearchByName);
                String val = search.getText().toString();
                lv = (ListView) findViewById(R.id.app_Doclistview);
                jsonParse(val);
//                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView)view.findViewById(R.id.item_docname);
                String name = tv.getText().toString();
                Docteur Doc = (Docteur) adapterView.getAdapter().getItem(i);
                Intent onItemClickIntent = new Intent(getApplicationContext(), DocteurLocalisation.class);
                onItemClickIntent.putExtra("PE_DocName", name);
                onItemClickIntent.putExtra("PE_Docteur", Doc);
                startActivity(onItemClickIntent);
            }
        });
    }


    private void jsonParse(String key) {
        Log.e("--- JSON PARSE ---", "--- CALLED");
        // DEFAULT VALUE OF THE URL
        String url = "http://www.euniversity.xyz/getdoctor.php?KEYWORD=";
        if(key !=null) {
            url = "http://www.euniversity.xyz/getdoctor.php?KEYWORD=" + key;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("--- JSON PARSE ---", "--- CALLED");
                        try {
                            mainDocteurs = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("docteur");
                            //Log.e("JSON PARSE", "jsonArray.length() || ["+jsonArray.length()+"]");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                System.out.println("----- JSON PARSE ------ CALLED || [ "+i+" ]");
                                JSONObject Doc = jsonArray.getJSONObject(i);

//                                   // DocName
                                String DocName = Doc.getString("name");

                                    // DocSpecialite specialite
                                String DocSpecialite = Doc.getString("specialite");
//                                   // DocFix
                                String DocFix = Doc.getString("fix");
//                                   // DocAddress
                                String DocAddress = Doc.getString("address");
                                System.out.println("----- JSON PARSE ------ CALLED || [ "+i+" ] || Address: "+ DocAddress);
//                                    // DocCity
                                String DocCity = Doc.getString("city");
//                                 // DocCountry
                                String DocCountry = Doc.getString("country");
//                                   // DocLatitude
                                double DocLatitude = Double.parseDouble(Doc.getString("latitude"));
//                                   // DocLongitude
                                double DocLongitude = Double.parseDouble(Doc.getString("longitude"));
                                // Get The Distance
                                HaversineFormula calcDistance = new HaversineFormula(UserLatitude,UserLongitude,DocLatitude,DocLongitude);

                                // INSERT LINE'S DATA IN THE ARRAYLIST HOLDER.
                                mainDocteurs.add(new Docteur(DocName, DocSpecialite, DocFix, DocAddress, DocCity, DocCountry, DocLatitude, DocLongitude,calcDistance.getDistance()));
                            }

                            // SORT THE DocteurS BY DISTANCE.
                            Collections.sort(mainDocteurs);

                            // Set Up List View
                            setUpListView();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
            }
        });

        // REQUEST QUEUE
        mQueue = Volley.newRequestQueue(this);
        mQueue.add(request);
    }

    private void setUpListView() {
        lva = new DocteurListViewAdapter(this, R.layout.activity_docteur_listview_container, mainDocteurs);
        lv.setAdapter(lva);
    }

    private double[] getDeviceLocation(){
        Log.e("getDeviceLocation", "getDeviceLocation > getting current location...");

        // CHECKING IF LOCATION REQUIREMENTS IF ENABLED.
        //checkLocationRequirement();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        final double[] cord = new double[0];
        try{
            if(true){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {


                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Location currentLocation = (Location) task.getResult();
                            Log.e(TAG,"getDeviceLocation > Found Location > LAT: "+currentLocation.getLatitude()+" LNG: "+currentLocation.getLongitude());

                            //moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()), DEFAULT_ZOOM, "My Location.");
                            UserLatitude = currentLocation.getLatitude();
                            UserLongitude = currentLocation.getLongitude();

                        }else{
                            Log.e(TAG, "onComplete: current location is null");
                            Toast.makeText(ActivityDocteur.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch(SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: "+e.getMessage());
        }
        return cord;
    }

    /**
     * This Method checks if the Docone localisation feature is enabled.
     * And Request the user to enable it, in case localisation feature is disabled.
     */
    private void checkLocationRequirement() {
        Log.e(TAG, "checkLocationRequirement > CHECKING");

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
                                        ActivityDocteur.this,
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
                        resolvable.startResolutionForResult(ActivityDocteur.this, 1);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    /**
     * This Method Will check for the needed inner application's permissions, that are essential to app.
     */
    private void checkInnerAppLocationsPermissions(){
        Log.i(TAG, "checkInnerAppLocationsPermissions Method Started");

        String[]  permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                //initMap();
                Log.e(TAG,"checkInnerAppLocationsPermissions > All Needed Authorisations Are Set And Granted ");
            }else{
                Log.e(TAG,"checkInnerAppLocationsPermissions > We Need To Ask For The Needed Permissions To Be Granted. ");
                ActivityCompat.requestPermissions(this, permissions, 1234);

            }
        }else{
            Log.e(TAG,"checkInnerAppLocationsPermissions > We Need To Ask For The Needed Permissions To Be Granted. ");
            ActivityCompat.requestPermissions(this, permissions, 1234);
        }

        Log.i(TAG, "checkInnerAppLocationsPermissions Method ended");
    }

    @Override
    /**
     * This Method Will be called After (checkInnerAppLocationsPermissions) ActivityCompat.requestPermissions is called.
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionsGranted = false;

        //NOTE: LOCATION_PERMISSION_REQUEST_CODE is the code we defined early with the value (1234)...
        // We Defined the code, and passed it through the ActivityCompat requests... .
        switch (requestCode){
            case 1234 : {
                if(grantResults.length > 0 ){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;

                    //initialize the map.
                    //initMap();
                }
            }
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
