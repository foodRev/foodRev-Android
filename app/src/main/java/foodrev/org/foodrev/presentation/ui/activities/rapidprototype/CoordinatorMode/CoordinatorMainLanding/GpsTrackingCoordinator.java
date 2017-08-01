package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.CoordinatorMainLanding;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import foodrev.org.foodrev.R;


public class GpsTrackingCoordinator extends AppCompatActivity {


    // TODO place this into view model, associate the GPS data with live data for user

    // specifications for GPS - may want to encapsulate with the gps class
    private static final int MIN_TIME = 5000;
    private static final int MIN_DISTANCE = 0;

    // LatLng
    private LatLng coordinatorLatLng;

    // Firebase Members
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference coordinatorFirebase;
    private FirebaseUser firebaseUser;

    // GPS Related
    private LocationManager locationManager;
    private LocationListener locationListener;

    // permissions related
    private final int PERMISSION_ALL = 1;

    // UI Related
    @BindView(R.id.gps_toggle_button) ToggleButton gpsToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_tracking_coordinator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        // setup firebase paths
        setupFirebase();
    }

    @OnCheckedChanged(R.id.gps_toggle_button)
    public void toggleGps(boolean isChecked) {
        if (isChecked) {
            // setup gps tracking
            setupGpsTracking();
            coordinatorFirebase.child("tracking").setValue("true");
            Log.d("CoordinatorGPS", "toggleGps: starting gps tracking");
        } else {
            if(locationManager != null && locationListener != null) {
                locationManager.removeUpdates(locationListener);
                Log.d("CoordinatorGPS", "toggleGps: removing gps tracking");
                coordinatorFirebase.child("tracking").setValue("false");
            }

        }

    }

    /**
     * updates firebase with new lat lng values
     * coordinatorLatLng is top-level scope within the class
     */
    private void updateGps(LatLng currentLatLng) {
        coordinatorFirebase.child("latitude").setValue(currentLatLng.latitude);
        coordinatorFirebase.child("longitude").setValue(currentLatLng.longitude);
        Log.d("CoordinatorGPS", "updateGps: " + String.valueOf(currentLatLng));
    }

    private void setupFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        coordinatorFirebase = firebaseDatabase.getReference("DRIVERS/-KglyP-QRvRh5fIZJxHD/");
    }
    private void setupGpsTracking() {
        setupLocationManager();

    }

    public void setupLocationManager() {
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                coordinatorLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                // update firebase with new lat lng values
                updateGps(coordinatorLatLng);
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