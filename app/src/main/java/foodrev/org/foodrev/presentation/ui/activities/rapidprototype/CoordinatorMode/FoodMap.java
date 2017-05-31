package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import foodrev.org.foodrev.R;

//import foodrev.org.foodrev.R;

public class FoodMap extends FragmentActivity implements OnMapReadyCallback {

    private static final int MIN_TIME = 5000;
    private static final int MIN_DISTANCE = 10;

    private HashMap<String,Marker> driverLocations = new HashMap<String,Marker>();

    private GoogleMap mMap;
    private LatLng myLatLng;
    private FirebaseDatabase firebaseDatabase;

    // Driver Status Root
    private DatabaseReference driverStatusRoot; //driving/unloading/loading

    //Driver Status
    private DatabaseReference driverStatus; //driving/unloading/loading
    private DatabaseReference driverCarrying; //string description of meals carrying for FoodMap
    private DatabaseReference driverAssigned;

    //Driver Location
    private DatabaseReference driverGpsFirebaseLat;
    private DatabaseReference driverGpsFirebaseLong;
    private DatabaseReference driverCurrentSite;
    private DatabaseReference driverSiteHeadingNext;

    //Driver Info
    private DatabaseReference driverFullName;
    private DatabaseReference driverEmail;
    private DatabaseReference driverPhone;

    //Driver Car Info
    private DatabaseReference driverCarType;
    private DatabaseReference driverCapacity;

    private MarkerOptions myMarkerOptions;
    private Marker myMarker;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1;
    private final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 0;
    private final int PERMISSION_ALL = 1;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        myLatLng = new LatLng(37.8087246, -122.4993607);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (firebaseUser != null) {
//            myMarkerOptions = new MarkerOptions()
//                    .position(myLatLng)
//                    .title(firebaseUser.getDisplayName())
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_icon));
//            myMarker = mMap.addMarker(myMarkerOptions);
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            setupFirebase();
//        }
    }

    public void setupFirebase() {
        //setup GPS Tracking
        setupLocationManager();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        firebaseDatabase = FirebaseDatabase.getInstance();

        //Driver Status Root
        driverStatusRoot = firebaseDatabase.getReference("DRIVER_STATUS/");

        //Driver Status
        driverStatus = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/status"); //driving/unloading/loading
        driverCarrying = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/carrying");
        driverAssigned = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/assigned");

        //Driver Location
        driverGpsFirebaseLat = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/latitude");
        driverGpsFirebaseLong = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/longitude");
        driverCurrentSite = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/current_site");
        driverSiteHeadingNext = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/site_heading_next");

        //Driver Info
        driverFullName = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/fullName");
        driverEmail = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/email");
        driverPhone = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/phone");

        //Driver Car/Capacity/Carrying Info
        driverCarType = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/car_type");
        driverCapacity = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/capacity");

        //initialize known values
        driverFullName.setValue(firebaseUser.getDisplayName());
        driverEmail.setValue(firebaseUser.getEmail());

        //watch all drivers
        ValueEventListener driverUpdates = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    LatLng driverLatLng;

                if (dataSnapshot.exists()) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        double driverLatitude;
                        double driverLongitude;

                        if (child.child("latitude").getValue(Double.class) == null
                                || child.child("longitude").getValue(Double.class) == null ) {
                            continue;
                        } else if (!driverLocations.containsKey(child.getKey())) {
                            //store latlng in LatLng object
                            driverLatLng = new LatLng(child.child("latitude").getValue(Double.class),
                                    child.child("longitude").getValue(Double.class));

                            //create marker description
                            myMarkerOptions = new MarkerOptions()
                                    .position(driverLatLng)
                                    .title(child.child("fullName").getValue(String.class))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_icon));

                            //create new marker
                            Marker driverMarker = mMap.addMarker(myMarkerOptions);

                            //store marker in hashmap
                            driverLocations.put(child.getKey(), driverMarker);
                        } else {
                            driverLatLng = driverLocations.get(child.getKey()).getPosition();
                            driverLatitude = child.child("latitude").getValue(Double.class);
                            driverLongitude = child.child("longitude").getValue(Double.class);
                            if (driverLatLng.latitude == driverLatitude
                                    && driverLatLng.longitude == driverLongitude) {
                                continue;
                            } else {
                                driverLocations.get(child.getKey()).setPosition(
                                        new LatLng(driverLatitude, driverLongitude));
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        driverStatusRoot.addValueEventListener(driverUpdates);


    }

    public void updateGPS(LatLng currentLatLong) {
        driverGpsFirebaseLat.setValue(myLatLng.latitude);
        driverGpsFirebaseLong.setValue(myLatLng.longitude);
    }

    public void setupLocationManager() {
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                updateGPS(myLatLng);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };


        String[] PERMISSIONS = { Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
        // Register the listener with the Location Manager to receive location updates
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS, PERMISSION_ALL);
            return;
        } else {
            requestLocationUpdate();
        }
    }

    // having now wrapped location update request in permissions, we'll need to
    // annotate to suppress missing permissions notification
    // source: http://stackoverflow.com/questions/35124794/android-studio-remove-security-exception-warning
    @SuppressWarnings({"MissingPermission"})
    private void requestLocationUpdate() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
    }

    //permissions helper
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        String permission = permissions[0];
        int grantResult = grantResults[0];

        switch(requestCode) {
            case 1:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    requestLocationUpdate();
                } else {
                    Toast.makeText(this, "Enable GPS for this app to proceed", Toast.LENGTH_SHORT).show();
                    //return to main activity
                    finish();
                }
            }
        }
    }


    @Override
    public void onPause(){
        super.onPause();
        if(locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
