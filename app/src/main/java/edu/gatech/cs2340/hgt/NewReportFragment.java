package edu.gatech.cs2340.hgt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class NewReportFragment extends Fragment implements OnMapReadyCallback {

    private static final double
    SEATTLE_LNG =-122.33207,
    SYDNEY_LAT = -33.867487,
    SYDNEY_LNG = 151.20699,
    NEWYORK_LAT = 40.714353,
    NEWYORK_LNG = -74.005973;

    private Button submitBtn;
    private Button cancelBtn;
    private EditText itemName;
    private EditText price;
    private NewReportCallBack activity;
    private String curUsername;
    private String locality;
    private Context context;
    private double lat;
    private double lgn;
  //  private GoogleMap mMap;
  //  private MapView mMapView;
    private Menu menu;
    private Button goLocationBtn;



    public NewReportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_report, container, false);
        submitBtn = (Button) rootView.findViewById(R.id.new_report_submitBtn);
        cancelBtn = (Button)rootView.findViewById(R.id.new_report_cancelBtn);
        goLocationBtn = (Button)rootView.findViewById(R.id.new_report_location);
        itemName = (EditText)rootView.findViewById(R.id.new_report_item_name);
        price = (EditText)rootView.findViewById(R.id.new_report_price);
        //mMapView = (MapView)rootView.findViewById(R.id.map);
        submitBtn.setOnClickListener(new OnSubmitListener());
        cancelBtn.setOnClickListener(new OnCancelListner());
        goLocationBtn.setOnClickListener(new OnGoLocationListener());

        //mMapView.onCreate(savedInstanceState);
        //mMapView.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        Toast.makeText(this.getActivity(), "map is Ready!", Toast.LENGTH_SHORT).show();
//        this.mMap = googleMap;
    }


    private class OnCancelListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            activity.returnToUserHomeActivity();
        }
    }

    private class OnGoLocationListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, SalesLocationActivity.class);
            startActivityForResult(i, 100);


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK ) {
            locality = data.getStringExtra("locality");
            Toast.makeText(this.context, locality, Toast.LENGTH_SHORT).show();
        }
        this.lat = data.getDoubleExtra("lat", 0);
        this.lgn = data.getDoubleExtra("lgn", 0);
    }

    private class OnSubmitListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (addNewReport(itemName.getText().toString(), price.getText().toString(),
                    locality, lat, lgn)) {
                displayAlert("You have successfull reported a sale");
            } else {
                displayAlert("error occurs please try again");
            }
        }
    }

    /**
     * add new report to dataBase
     * @param itemName
     * @param price
     * @param location
     * @return
     */
    private boolean addNewReport(String itemName, String price, String location) {
        return activity.addNewReport(itemName, price, location);
    }

    private boolean addNewReport(String itemName, String price, String location, double lat, double lgn) {
        return activity.addNewReport(itemName, price, location, lat, lgn);
    }

    /**
     * the helper method for creating a alert box to display a message
     * @param s
     */
    private void displayAlert(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder((Activity)activity);
        builder.setMessage(s)
                .setTitle("Notification")
                .create()
                .show();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (NewReportCallBack)activity;
        this.context = activity;

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_currentLocation:
//               Toast.makeText(this.getActivity(), "currentLoc", Toast.LENGTH_SHORT);
//               goCurrentLocation();
//               break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    private void goCurrentLocation() {
//        System.out.println("goCurrentLocation");
//        goLocation(NEWYORK_LAT, NEWYORK_LNG, 10);
//    }

//    private void goLocation(double lat, double lng, float zoom) {
//        LatLng ll = new LatLng(lat, lng);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
//        mMap.animateCamera(update);
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mMapView.onDestroy();
//    }

    public interface NewReportCallBack {
        public boolean returnToUserHomeActivity();
        public boolean addNewReport(String itemName, String price, String location);
        public boolean addNewReport(String itemName, String price, String location, double lat, double lgn);
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mMapView.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mMapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mMapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mMapView.onLowMemory();
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mMapView.onSaveInstanceState(outState);
//    }
}
