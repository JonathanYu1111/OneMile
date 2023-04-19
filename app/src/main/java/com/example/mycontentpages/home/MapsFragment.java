package com.example.mycontentpages.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mycontentpages.attractionInfo.AttractionDetailsActivity;
import com.example.mycontentpages.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsFragment extends Fragment {
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Marker myLocationMarker;
    private  static double testLat=54.978;
    private static double testLng=-1.6178;
    ArrayList<MarkerOptions> markerOptionList=new ArrayList<MarkerOptions>();
    ArrayList<Marker> markers=new ArrayList<>();
    GoogleMap thisMap;
    private static boolean locationUpdate= true;

    /////////////////////////////////////////////////////////////
    View rootView;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            Button  button_myLocation;

            button_myLocation=getActivity().findViewById(R.id.switchButton);

            thisMap=googleMap;
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
            Dexter.withContext(getActivity()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            mapStart();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                            while (true) {
                                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG);
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            while (true) {
                                Toast.makeText(getActivity(), "Permission Should Be Shown", Toast.LENGTH_LONG);
                            }
                        }
                    }).check();

            //添加marker跳转页面，到AttractionDetailsPage
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    Intent intent = new Intent(getActivity(), AttractionDetailsActivity.class);
                    startActivity(intent);
                    return false;
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_maps, container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }



    public void mapStart(){

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location!=null){
                    LatLng myLatLng=new LatLng(location.getLatitude(),location.getLongitude());
                    //add  element options
                    MarkerOptions marker_myLocation=new MarkerOptions().position(myLatLng).title("Current Location");
                    myLocationMarker = thisMap.addMarker(marker_myLocation);
                    CircleOptions circleOptions = new CircleOptions()
                            .center(myLatLng)
                            .radius(2000) // radius in meters
                            .strokeWidth(2)
                            .strokeColor(Color.BLUE)
                            .fillColor(Color.argb(70, 0, 100, 10));
                    //add  elements

                    thisMap.addMarker(marker_myLocation);

                    thisMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 13));

                    double testlat=54.978;
                    double testlng=-1.6178;
                    markerOptionList.add(new MarkerOptions().position(new LatLng(testlat+0.018, testlng+0.015)).title("Marker1").snippet("Marker1 Snippet"));
                    markerOptionList.add(new MarkerOptions().position(new LatLng(testlat+0.028, testlng+0.025)).title("Marker2").snippet("Marker2 Snippet"));
                    markerOptionList.add(new MarkerOptions().position(new LatLng(testlat+0.040, testlng+0.035)).title("Marker3").snippet("Marker3 Snippet"));
                    markerOptionList.add(new MarkerOptions().position(new LatLng(testlat+0.051, testlng+0.045)).title("Marker4").snippet("Marker4 Snippet"));
                    markerOptionList.add(new MarkerOptions().position(new LatLng(testlat+0.060, testlng+0.052)).title("Marker5").snippet("Marker5 Snippet"));

                    Circle displayCircle= thisMap.addCircle(circleOptions);

                    for (MarkerOptions mo:markerOptionList){
                        Marker newMarker=thisMap.addMarker(mo);
                        newMarker.setVisible(false);
                        markers.add(newMarker);
                    }
                    {   //without method
                        Context context=getActivity();
                        locationManager = (LocationManager) ContextCompat.getSystemService(context,LocationManager.class);
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        locationListener = new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                if (locationUpdate==false){return;}
                                testLat += 0.01;
                                testLng += 0.01;
                                LatLng newestLatLng = new LatLng(testLat, testLng);
                                myLocationMarker.setPosition(newestLatLng);
                                thisMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newestLatLng, thisMap.getCameraPosition().zoom));
                                Log.i("Location", "Location changed");
                                displayCircle.setCenter(newestLatLng);

                                for(Marker m:markers){
                                    Location location1=new Location("");
                                    Location location2=new Location("");
                                    location1.setLatitude(testLat);
                                    location1.setLongitude(testLng);
                                    location2.setLatitude(m.getPosition().latitude);
                                    location2.setLongitude(m.getPosition().longitude);
                                    if(location1.distanceTo(location2)<=2000){
                                        m.setVisible(true);
                                    }else{
                                        m.setVisible(false);
                                    }

                                }
                            }
                        };
                        locationManager.requestLocationUpdates(LocationManager.FUSED_PROVIDER, 4000, 0, locationListener);
                    }

                    Button switchButton=getActivity().findViewById(R.id.switchButton);
                    switchButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());
                            if (status != ConnectionResult.SUCCESS) {
                                if (GoogleApiAvailability.getInstance().isUserResolvableError(status)) {
                                    GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), status, 2404).show();
                                    Toast.makeText(getActivity(),"Please get GooglePlay service connected",Toast.LENGTH_SHORT);
                                }else{Toast.makeText(getActivity(),"Can not connect to GooglePlay service",Toast.LENGTH_LONG);}
                            }


                            if(locationUpdate==true){
                                locationUpdate=false;
                                Log.i("switchButton","Stop updating");
                            }else {locationUpdate=true;
                                Log.i("switchButton","Start updating");}

                        }

                    });

                    thisMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            Toast.makeText(getActivity(),"经度:"+marker.getPosition().latitude+"\n纬度："+marker.getPosition().longitude,Toast.LENGTH_LONG).show();
                            return true;
                        }






                    });
                }}


        });}}







