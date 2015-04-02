package edu.gatech.cs2340.hgt;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SalesLocationActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, com.google.android.gms.location.LocationListener{

    private GoogleMap mMap;
    private Marker marker;
    private static final int GPS_ERRORDIALOG_REQUEST = 9001;
    private GoogleApiClient mClient;
    private Location lastLocation;
    private LocationRequest mLocationRequest;

    /**
     * create the view and initiate all services
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (serviceOK()) {
            setContentView(R.layout.activity_sales_location);
            setUpMapIfNeeded();
            buildGoogleApiClient();
        } else {
            setContentView(R.layout.activity_sales_location);
        }
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            hideButtons();
            double lat = i.getDoubleExtra("lat", 0);
            double lgn = i.getDoubleExtra("lgn", 0);
            goToLocation(lat,lgn, 10);
            setMarker(i.getStringExtra("name" ), lat, lgn);
        } else {
            setButtons();
            setMarker("Please enter the location of the sale", 0, 0);
        }

    }

    private void hideButtons() {
        findViewById(R.id.location_go).setVisibility(View.INVISIBLE);
        findViewById(R.id.location_return).setVisibility(View.INVISIBLE);
    }

    private void setButtons() {
        Button goBtn = (Button) findViewById(R.id.location_go);
        Button returnBtn = (Button) findViewById(R.id.location_return);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng ll = marker.getPosition();
                String locality = marker.getTitle();
                double lat = ll.latitude;
                double lgn = ll.longitude;
                Intent returnIntent = new Intent();
                returnIntent.putExtra("lat", lat);
                returnIntent.putExtra("lgn", lgn);
                returnIntent.putExtra("locality", locality);
                setResult(RESULT_OK, returnIntent);
                finish();

            }
        });

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    geoLocate(v);
                } catch (Exception e) {
                    Toast.makeText(SalesLocationActivity.this, Arrays.toString(e.getStackTrace()),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * helper for builder the Google Map API
     */
    synchronized void buildGoogleApiClient() {
        mClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    /**
     * move camera to the location
     * @param lat
     * @param lng
     * @param zoom
     */
    private void goToLocation(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.animateCamera(update);
    }

    protected void goToCurrentLocation() {
        goToLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 15);
    }

    /**
     * hide the keyboard, and get the geolocation of the location name
     * @param v
     * @throws IOException
     */
    public void geoLocate(View v) throws IOException {
        Toast.makeText(this, "goGeoLocate", Toast.LENGTH_SHORT).show();
        EditText ed = (EditText)findViewById(R.id.location_search);
        String location = ed.getText().toString();
        if (location.length() == 0) {
            Toast.makeText(this, "Can not be blanked", Toast.LENGTH_SHORT).show();
            return;
        }
        
        hideSoftKeyBoard(v);
        
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();
        double lat = add.getLatitude();
        double lng = add.getLongitude();
        goToLocation(lat, lng, 10);
        setMarker(locality, lat, lng);
        makeToast(locality);
        
    }



    private void setMarker(String locality, double lat, double lng) {
        if (marker != null) {
            marker.remove();
        }
        MarkerOptions options = new MarkerOptions()
                                .title(locality)
                .position(new LatLng(lat, lng))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        marker = mMap.addMarker(options);
    }

    private void hideSoftKeyBoard(View v) {
        InputMethodManager im = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * check if the GooglePlayService is Okay
     * @return
     */
    boolean serviceOK() {
        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, GPS_ERRORDIALOG_REQUEST );
            dialog.show();
        } else {
            Toast.makeText(this, "dont have access to Google Play Service", Toast.LENGTH_SHORT).show();
        }
        return false;
    }



    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            } else {
                Toast.makeText(this, "map is not available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setMyLocationEnabled(true);
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        if (mClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mClient,
                   this);
        } else {
            makeToast("client not connected");
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mClient!= null && mClient.isConnected()) {
            startLocationUpdate();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(mClient);
        startLocationUpdate();
    }

    private void startLocationUpdate() {
        if (mLocationRequest == null) {
            createLocationRequest();
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mClient, mLocationRequest,
               this);

    }


    @Override
    public void onConnectionSuspended(int i) {
        makeToast("apiClient suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        makeToast(connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
