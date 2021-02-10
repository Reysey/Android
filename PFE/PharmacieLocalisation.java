package com.reydev.tuto.androiddatabase;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class PharmacieLocalisation extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "-<-<-<| PharmacieLicalisation |>->->-";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private GoogleMap mMap;
    private Context context;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15f;
    private boolean mLocationPermissionsGranted = false;
    private static Pharmacie pe_ph;
    //private double distance;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacie_localisation);


        // FILLING THE TEXTVIEW (PHARMACIE NAME)
        TextView tv = (TextView) findViewById(R.id.pl_phname);
        tv.setText(getIntent().getSerializableExtra("PE_PhName").toString());

        pe_ph = (Pharmacie) getIntent().getSerializableExtra("PE_Pharmacie");
        Toast.makeText(this, pe_ph.getPhAddress(), Toast.LENGTH_SHORT).show();

        //-----------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------

        //MAP INIT
        if (checkGoogleServices()) {
            Log.e(TAG, "SHIT STARTED.");
            checkInnerAppLocationsPermissions();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("LongLogTag")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e(TAG, "onMapReady > Started.");
        mMap = googleMap;
        //getDeviceLocation();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        //mMap.setMaxZoomPreference(15f);
        // Add a marker in Pharmacie and move the camera

        //LatLng temara = new LatLng(33.9257714, -6.9195057);
        LatLng geol = new LatLng(pe_ph.getPhLatitude(), pe_ph.getPhLongitude());
        mMap.addMarker(new MarkerOptions().position(geol).title("Pharmacie: "+pe_ph.getPhName()+"."));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(temara));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geol, 15f));
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
            @SuppressLint("LongLogTag")
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
                                        PharmacieLocalisation.this,
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
                        resolvable.startResolutionForResult(PharmacieLocalisation.this, 1);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });

    }



    @SuppressLint("LongLogTag")
    /**
     * This Method Checks if Google Services Are Working.
     * Case Not, A dialog with pre-built in message will show up to fix things.
     */
    public boolean checkGoogleServices(){
        Log.i(TAG, "checkGoogleServices Method Started");

        // GETTING GOOGLE SERVICES AVAILABILITY STATUS CODE.
        int gs_availabilityStatus = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(PharmacieLocalisation.this);

        //Checking... IF THE CONNECTION WAS SUCCESSFUL.
        Log.i(TAG, "checkGoogleServices > Checking Connection Status.");
        if(gs_availabilityStatus == ConnectionResult.SUCCESS){
            Log.i(TAG, "... Checking Connection Status > SUCCESS.");
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(gs_availabilityStatus)){
            Log.i(TAG, "... Checking Connection Status > RESOLVABLE ERROR.");

            // GETTING THE DIALOG REQUEST ALREADY DEFINED BY GOOGLE FOR THIS PARTICULAR ERROR (MAY BE WRONG VERSION...)
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(PharmacieLocalisation.this, gs_availabilityStatus, 9001);
            dialog.show();
        }else{
            Log.i(TAG, "... Checking Connection Status > FATAL ERROR.");
            Toast.makeText(this, "ERROR: CONTACT SUPPORT FOR HELP.",Toast.LENGTH_LONG).show();
        }
        Log.i(TAG, "checkGoogleServices Method Ended");
        return false;
    }


    @SuppressLint("LongLogTag")
    /**
     * This Method Will check for the needed inner application's permissions, that are essential to app.
     */
    private void checkInnerAppLocationsPermissions(){
        Log.i(TAG, "checkInnerAppLocationsPermissions Method Started");

        String[]  permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
                Log.e(TAG,"checkInnerAppLocationsPermissions > All Needed Authorisations Are Set And Granted ");
            }else{
                Log.e(TAG,"checkInnerAppLocationsPermissions > We Need To Ask For The Needed Permissions To Be Granted. ");
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);

            }
        }else{
            Log.e(TAG,"checkInnerAppLocationsPermissions > We Need To Ask For The Needed Permissions To Be Granted. ");
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
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
            case LOCATION_PERMISSION_REQUEST_CODE : {
                if(grantResults.length > 0 ){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;

                    //initialize the map.
                    initMap();
                }
            }
        }
    }

    @SuppressLint("LongLogTag")
    private void initMap() {
        Log.e(TAG, "initMap > Initializing The Map... ");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(PharmacieLocalisation.this);
    }

    @SuppressLint("LongLogTag")
    /**
     * This Method Will Basically Find the location of the current device in which the application is runnning.
     */
    private void getDeviceLocation(){
        Log.e(TAG, "getDeviceLocation > getting current location...");

        // CHECKING IF LOCATION REQUIREMENTS IF ENABLED.
        checkLocationRequirement();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Location currentLocation = (Location) task.getResult();
                            Log.e(TAG,"getDeviceLocation > Found Location > LAT: "+currentLocation.getLatitude()+" LNG: "+currentLocation.getLongitude());

                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()), DEFAULT_ZOOM, "My Location.");

                        }else{
                            Log.e(TAG, "onComplete: current location is null");
                            Toast.makeText(PharmacieLocalisation.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch(SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: "+e.getMessage());
        }
    }

    @SuppressLint("LongLogTag")
    private void moveCamera(LatLng latlng, float zoom, String title){
        Log.e(TAG, "moveCamera: Moving the Camera to the Lat: "+latlng.latitude+", and lng: "+latlng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));

        if(!title.equals("My Location.")){
            MarkerOptions options = new MarkerOptions().position(latlng).title(title);
            mMap.addMarker(options);
        }

        // HIDE KEYBOARD AFTER SEARCH CLICK, FUNCTION FOR OLD PHONES.
        //hideSoftKeyboard();
    }

    /**
     * HaverSine is a functiton to define the distance between two locations.
     * @param lat1 Latitude of the first position
     * @param lon1 Longitude of the first position
     * @param lat2 Latitude of the second position
     * @param lon2 Longitude of the second position
     */
    public double haversine(double lat1, double lon1, double lat2, double lon2) {
        double Rad = 6372.8; //Earth's Radius In kilometers
        // TODO Auto-generated method stub
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double distance = Rad * c;
        return distance;
    }
}