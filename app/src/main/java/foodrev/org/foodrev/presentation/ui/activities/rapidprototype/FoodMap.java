package foodrev.org.foodrev.presentation.ui.activities.rapidprototype;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.location.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import foodrev.org.foodrev.R;

public class FoodMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng myLatLng;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference driverGpsFirebaseLat;
    private DatabaseReference driverGpsFirebaseLong;
    private MarkerOptions myMarkerOptions;
    private Marker myMarker;
    private LocationManager locationManager;
    private LocationListener locationListener;
private final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1;
    private final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 0;
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
        myMarkerOptions = new MarkerOptions().position(myLatLng).title("Your Location");
        myMarker = mMap.addMarker(myMarkerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
        setupFirebase();
    }


    public void setupFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        driverGpsFirebaseLat = firebaseDatabase.getReference("DRIVER_STATUS/Larry_S/lat");
        driverGpsFirebaseLong = firebaseDatabase.getReference("DRIVER_STATUS/Larry_S/long");

        driverGpsFirebaseLat.setValue(myLatLng.latitude);
        driverGpsFirebaseLong.setValue(myLatLng.longitude);
        setupLocationManager();
    }

    public void updateUI(LatLng currentLatLong) {
        myMarker.setPosition(currentLatLong);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLong));

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
                updateUI(myLatLng);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

// Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_ACCESS_FINE_LOCATION);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }
}
