package foodrev.org.foodrev.presentation.ui.activities.rapidprototype;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import foodrev.org.foodrev.R;

public class FoodMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng myLatLng;
    private FirebaseDatabase firebaseDatabase;

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
    private DatabaseReference driverName;
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
        myLatLng = new LatLng(37.7768052, -122.4171676);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            myMarkerOptions = new MarkerOptions()
                    .position(myLatLng)
                    .title(firebaseUser.getDisplayName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_icon));
            myMarker = mMap.addMarker(myMarkerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            setupFirebase();
        }
    }

    public void setupFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();

        //Driver Status
        driverStatus = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/status"); //driving/unloading/loading
        driverCarrying = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/carrying");
        driverAssigned = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/assigned");

        //Driver Location
        driverGpsFirebaseLat = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/lat");
        driverGpsFirebaseLong = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/long");
        driverCurrentSite = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/current_site");
        driverSiteHeadingNext = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/site_heading_next");

        //Driver Info
        driverName = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/name");
        driverEmail = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/email");
        driverPhone = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/phone");

        //Driver Car/Capacity/Carrying Info
        driverCarType = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/car_type");
        driverCapacity = firebaseDatabase.getReference("DRIVER_STATUS/" + firebaseUser.getUid() + "/capacity");

        //initialize known values
        driverName.setValue(firebaseUser.getDisplayName());
        driverEmail.setValue(firebaseUser.getEmail());

        //setup GPS Tracking
        setupLocationManager();
    }

    public void updateGPS(LatLng currentLatLong) {
        myMarker.setPosition(currentLatLong);

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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
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
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
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
