package com.matchmaker.matchmaker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.errors.ApiException;

import java.io.IOException;


//Part of code taken from google API demos on Github
public class Navigation extends AppCompatActivity implements OnMapReadyCallback {


    private static final String TAG = Navigation.class.getSimpleName();
    private GoogleMap mMap;


    //Sets API Key for google maps
    public String directionsApiKey = "AIzaSyDhh8c3OnMvqVpQXab1ermm8cfCH9lZaYc";

    public Navigation() throws InterruptedException, ApiException, IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_navigation);


        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    //Once the map is ready it calls this so the map can be manipulated.
    //Adds in origin and destination markers and draws a path between the two
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;


        addMarkersToMap(destination, mMap);

        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 12));
            }
        });

    }

    public LatLng destination = MatchDetailsActivity.event_location;
    public String destination_address = MatchDetailsActivity.address;

    //Adds position markers to the map to mark origin and destination.
    private void addMarkersToMap(LatLng destination, GoogleMap mMap) {
        mMap.addMarker(new MarkerOptions()
                .position(destination)
                .title(destination_address)
        );
    }
}

