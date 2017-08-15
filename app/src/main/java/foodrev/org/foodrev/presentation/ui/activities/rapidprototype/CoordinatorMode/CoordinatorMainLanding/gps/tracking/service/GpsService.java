package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.CoordinatorMainLanding.gps.tracking.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class GpsService extends Service implements LocationListener {

    HashMap<String,Object> gpsPostHashMap;
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

    private Date date;
    private SimpleDateFormat hrFormat;
    private SimpleDateFormat dayFormat;
    private String dayPattern;
    private String hrTimeStampPattern;
    private String dateString;
    private String dayString;
    private String hrString;

    public GpsService() {

        setupFirebaseUser();
        setupTimeFormats();
        gpsPostHashMap = new HashMap<>();

        // setup firebase paths
        setupFirebase();
    }

    private void setupFirebaseUser() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
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


    }

    private void writeNewEntry(String hrTimeStampPattern, Date date, double latitude, double longitude) {
        //clear hashmap if populated
        if(!gpsPostHashMap.isEmpty()) {
            gpsPostHashMap.clear();
        }

        gpsPostHashMap.put("username", firebaseUser.getDisplayName());
        gpsPostHashMap.put("latitude", latitude);
        gpsPostHashMap.put("longitude", longitude);
        gpsPostHashMap.put("timestamp",date.getTime());
        gpsPostHashMap.put("hr_timestamp",hrTimeStampPattern);

    }

    /**
     * updates firebase with new lat lng values
     * coordinatorLatLng is top-level scope within the class
     */
    private void updateGps(LatLng currentLatLng) {

        date = new Date();

        // time stamp hr
        hrString = hrFormat.format(date);

        // index
        dayString = dayFormat.format(date);

        // update hashMap
        writeNewEntry(hrString, date, currentLatLng.latitude, currentLatLng.longitude);

        // push to the timestamp
        coordinatorFirebase.child(dayString).child(hrString).setValue(gpsPostHashMap);
    }

    private void setupFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        coordinatorFirebase = firebaseDatabase.getReference("TRACKING/" + firebaseUser.getDisplayName());
    }


    private void setupTimeFormats() {
        hrTimeStampPattern = "yyyy-MM-dd-HH-mm-ss";
        hrFormat = new SimpleDateFormat(hrTimeStampPattern);

        dayPattern = "yyyy-MM-dd";
        dayFormat = new SimpleDateFormat(dayPattern);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
