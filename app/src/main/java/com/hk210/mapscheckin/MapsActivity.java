package com.hk210.mapscheckin;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hk210.mapscheckin.Database.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private Toolbar toolbar;
    private SupportMapFragment mapFragment;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private double lat, lang;
    private static final int REQUEST_LOCATION = 1111;
    private DBHelper dbHelper;
    private String latitude, longitude, time;
    private String a = "Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        init();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocation();
    }
    private void init(){
        toolbar = findViewById(R.id.toolbarMAps);
        dbHelper = new DBHelper(this);
        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
    }

    private void getLocation(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            OnGPS();
        }
        else{
            if (ActivityCompat.checkSelfPermission(
                    MapsActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);}
            else{
                Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (locationGPS != null) {
                    lat = locationGPS.getLatitude();
                    lang = locationGPS.getLongitude();
                    LatLng currentLoc = new LatLng(lat, lang);
                    mMap.addMarker(new MarkerOptions().position(currentLoc).title("You are Here"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLoc));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc,18));

                    latitude = String.valueOf(lat);
                    longitude = String.valueOf(lang);
                    currenttime();

                } else {
                    Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void onclick(View view) {
        getLocation();
        Boolean checkdata = dbHelper.insertData(time,longitude, latitude);
        if(checkdata == true){
            Toast.makeText(this, "Check-in was successful", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Failed to create Check-In", Toast.LENGTH_SHORT).show();
        }

    }
    public String currenttime(){
        long currentTime= System.currentTimeMillis();
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(currentTime);
        String showTime=String.format("%1$tI:%1$tM:%1$tS %1$Tp",cal);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(cal.getTime());
        time = showTime +" "+ formattedDate;

        return time;
    }

    public void intentonclick(View view) {
        startActivity(new Intent(MapsActivity.this, CheckInHistory.class));
    }
}