package com.example.mycontentpages.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.Application;
import android.content.ClipData;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycontentpages.MainActivity;
import com.example.mycontentpages.Utils.BufferData;
import com.example.mycontentpages.Utils.DataContainer;
import com.example.mycontentpages.attractionInfo.AttractionDetailsActivity;
import com.example.mycontentpages.R;
import com.example.mycontentpages.data.Place;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsFragment extends Fragment {
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static Marker myLocationMarker;
    private static double defaultLat=54.969697+0.02;
    private static double defaultLng=-1.624609+0.02;
    private  static double testLat=defaultLat;
    private static double testLng=defaultLng;
    private static double myRealLat;
    private static double myRealLng;
    public static PopupMenu popupMenu;
    private static Button button_up,button_left,button_right,button_down,button_filter;
    private static String switchModelA="FREE Model";
    private static String switchModelB="FIXED Model";
    private static  List<MarkerOptions> markerOptionList=new ArrayList<>();
    private static List<Marker> markers=new ArrayList<>();
    private static Map<Marker,MarkerOptions> MM=new HashMap<>();
    private static Map<MarkerOptions, String> MO_ID=new HashMap<>(); //MarkerOptions and GooglePlaceID
    private  static GoogleMap thisMap;
    private   static Circle displayCircle;
    private static Double pointChangeIndex=0.003;

    private static Integer VisualDistance=1000;
    private static boolean testMode= true;
    private static String TITLE_CURRENT_POSITION="Your Are Here :)";


    /////////////////////////////////////////////////////////////
    View rootView;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {


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
        modifyInfoWindow(thisMap);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    myRealLat=location.getLatitude();
                    myRealLng=location.getLongitude();

                    //加载初始位置：测试位置
                    LatLng defaultLatLng=new LatLng(defaultLat,defaultLng);
                    MarkerOptions marker_myLocation=new MarkerOptions().position(defaultLatLng).title(TITLE_CURRENT_POSITION).snippet(""+testLat+","+testLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    myLocationMarker = thisMap.addMarker(marker_myLocation);
                    CircleOptions circleOptions = new CircleOptions()
                            .center(defaultLatLng)
                            .radius(VisualDistance) // radius: "VisualDistance" meters
                            .strokeWidth(2)
                            .strokeColor(Color.BLUE)
                            .fillColor(Color.argb(70, 0, 100, 10));
                    thisMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 14));
                    try {
                        MainActivity.initThread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //加载所有MarkerOption
                    for(Place place: DataContainer.getPlaceContainer()){
                        MarkerOptions newMO=new MarkerOptions().position(new LatLng(place.getLatitude(), place.getLongitude())).title(place.getName()).snippet("Click to learn more").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        markerOptionList.add(newMO);
                        MO_ID.put(newMO,place.getGooglePlaceId());
                    }
                    displayCircle= thisMap.addCircle(circleOptions);
                    //加载所有marker
                    for (MarkerOptions mo:markerOptionList){
                        Marker newMarker=thisMap.addMarker(mo);
                        newMarker.setVisible(false);
                        markers.add(newMarker);
                        MM.put(newMarker,mo);
                    }
                    updateView();


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
                                if (testMode==true){return;}
                                if(testMode=false){
                                    testLat=location.getLatitude();
                                    testLng=location.getLongitude();
                                    updateView();
                                }
                            }
                        };
                        locationManager.requestLocationUpdates(LocationManager.FUSED_PROVIDER, 3000, 0, locationListener);
                    }
                    Button switchButton=getActivity().findViewById(R.id.switchButton);
                    switchButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            changeButtonVisible(testMode);
                            int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());
                            if (status != ConnectionResult.SUCCESS) {
                                if (GoogleApiAvailability.getInstance().isUserResolvableError(status)) {
                                    GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), status, 2404).show();
                                    Toast.makeText(getActivity(),"Please get GooglePlay service connected",Toast.LENGTH_SHORT).show();

                                }else{Toast.makeText(getActivity(),"Can not connect to GooglePlay service",Toast.LENGTH_LONG).show();}
                            }
                            if(testMode==true){
                                testMode=false;
                                testLat=myRealLat;
                                testLng=myRealLng;
                                updateView();
                                Log.i("switchButton","switch to real mode");
                            }else {testMode=true;
                                testLat=defaultLat;
                                testLng=defaultLng;
                                updateView();
                                Log.i("switchButton","switch to test mode");}
                            String currentText = switchButton .getText().toString();
                            // 判断当前文本是否为 "Text 1"，如果是则切换为 "Text 2"，否则切换为 "Text 1"
                            if (currentText.equals(switchModelA)) {
                                switchButton.setText(switchModelB);
                            } else {
                                switchButton.setText(switchModelA);
                            }
                        }

                    });


                    button_up=getActivity().findViewById(R.id.upButton);
                    button_left=getActivity().findViewById(R.id.leftButton);
                    button_right=getActivity().findViewById(R.id.rightButton);
                    button_down=getActivity().findViewById(R.id.downButton);
//                    button_filter = getActivity().findViewById(R.id.filterButton);
//                    button_filter.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if(popupMenu==null)
//                             popupMenu = new PopupMenu(getActivity(), view);
//                            popupMenu.getMenuInflater().inflate(R.menu.filter_menu2, popupMenu.getMenu());
//                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                                @Override
//                                public boolean onMenuItemClick(MenuItem menuItem) {
//                                    // 处理菜单项的点击事件
//                                   // menuItem.setChecked(!menuItem.isChecked());
//                                    menuItem.setChecked(!menuItem.isChecked());
//                                    if(!menuItem.getTitle().equals("all")){
//                                    BufferData.setSelectedPlaceType(""+menuItem.getTitle());}
//                                    else{ BufferData.setSelectedPlaceType("");}
//                                    updateView();
//                                    Log.i("menu",""+menuItem.getTitle());
//                                    /////
//                                    return true;
//                                }
//                            });
//                            popupMenu.show();
//                        }
//                    });

                    button_up.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            testLat+=pointChangeIndex;
                            updateView();
                        }
                    });
                    button_down.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            testLat-=pointChangeIndex;
                            updateView();
                        }
                    });
                    button_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            testLng-=pointChangeIndex;
                            updateView();
                        }
                    });
                    button_right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            testLng+=pointChangeIndex;
                            updateView();
                        }
                    });
                    thisMap.setOnMarkerClickListener(new OnMarkerDoubleClickListener() {
                        @Override
                        public void onMarkerDoubleClick(Marker marker) {
                            marker.showInfoWindow();
                        }
                    });
                }}
        });}
    public static void updateView(){
        LatLng newestLatLng = new LatLng(testLat, testLng);
        myLocationMarker.setPosition(newestLatLng);
        thisMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newestLatLng, thisMap.getCameraPosition().zoom));
        Log.i("Location", "Location changed");
        displayCircle.setCenter(newestLatLng);
   //显示范围内的点
           List<String> IDs=new ArrayList<>(); //to collect googleID in range
        for(Marker m:markers){
            Location location1=new Location("");
            Location location2=new Location("");
            location1.setLatitude(testLat);
            location1.setLongitude(testLng);
            location2.setLatitude(m.getPosition().latitude);
            location2.setLongitude(m.getPosition().longitude);
   //TODO: to modify the below part
            String markerPlaceType="";
            for(Place place:DataContainer.getPlaceContainer()){
               if(place.getGooglePlaceId().equals(MO_ID.get (MM.get(m))))
               { markerPlaceType=place.getType();}
             }

            if(location1.distanceTo(location2)<=VisualDistance&& markerPlaceType.contains(BufferData.getSelectedPlaceType())){
                m.setVisible(true);
                String googleID_in_Range=MO_ID.get(MM.get(m));
                IDs.add(googleID_in_Range);
            }else{
                m.setVisible(false);
            }
        }
        BufferData.setInRangeIDs(IDs);
    }

    public void changeButtonVisible(Boolean tf){
        if(!tf) {
            button_up.setVisibility(View.VISIBLE);
            button_left.setVisibility(View.VISIBLE);
            button_right.setVisibility(View.VISIBLE);
            button_down.setVisibility(View.VISIBLE);
        }else{
            button_up.setVisibility(View.GONE);
            button_left.setVisibility(View.GONE);
            button_right.setVisibility(View.GONE);
            button_down.setVisibility(View.GONE);
        }

    }


    public abstract class OnMarkerDoubleClickListener implements GoogleMap.OnMarkerClickListener {
        private static final long DOUBLE_CLICK_TIME_DELTA = 300; // 双击时间间隔，单位：毫秒
        private long lastClickTime = 0;

        @Override
        public boolean onMarkerClick(Marker marker) {
            long clickTime = System.currentTimeMillis();
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                onMarkerDoubleClick(marker);
            }
            lastClickTime = clickTime;
            marker.showInfoWindow();
            return true;
        }

        public abstract void onMarkerDoubleClick(Marker marker);
    }


    //the popup window of marker
    public void modifyInfoWindow(GoogleMap googleMap){

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(@NonNull Marker marker) {
                if(!marker.getTitle().equals(TITLE_CURRENT_POSITION)){
                Intent intent = new Intent(getActivity(), AttractionDetailsActivity.class);
                intent.putExtra("placeID", MO_ID.get(MM.get(marker)));
                startActivity(intent);}
            }
        });
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Nullable
            @Override
            public View getInfoContents(@NonNull Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.marker_infowindow, null);

                // 设置自定义视图的属性
                TextView titleTextView = view.findViewById(R.id.window_title);
                TextView snippetTextView=view.findViewById(R.id.window_snippet);
                titleTextView.setText(marker.getTitle());
                snippetTextView.setText(marker.getSnippet());
                return view;
            }

            @Override
            public View getInfoWindow(Marker marker) {

//                View view = getLayoutInflater().inflate(R.layout.marker_infowindow, null);
//
//                // 设置自定义视图的属性
//                TextView titleTextView = view.findViewById(R.id.window_title);
//                TextView snippetTextView=view.findViewById(R.id.window_snippet);
//                Button windowButton=view.findViewById(R.id.window_button);
//
//                titleTextView.setText(marker.getTitle());
//                snippetTextView.setText(marker.getSnippet());
//
//                windowButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.i("window","window click");
//                        Toast.makeText(getActivity(),"window click",Toast.LENGTH_LONG).show();
//                    }
//                });
//                return view;
                return null;
            }});
    }

}







