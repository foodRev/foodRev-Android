package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchCommunity;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDriver;

public class DriverMapping extends FragmentActivity
        implements OnMapReadyCallback, LifecycleRegistryOwner {

    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);


    private GoogleMap mMap;
    private MapViewModel model;
    private Intent dispatchCreateIntent;
    private String dispatchKey;

    HashMap<String,DispatchDonor> donorUidMap = new HashMap<>();
    HashMap<String, Marker> iconDonorUidMap = new HashMap<>();

    HashMap<String,DispatchCommunity> communityUidMap = new HashMap<>();
    HashMap<String, Marker> iconCommunityUidMap = new HashMap<>();

    HashMap<String,DispatchDriver> driverUidMap = new HashMap<>();
    HashMap<String, Marker> iconDriverUidMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_mapping);

        // get dispatch key
        dispatchCreateIntent = getIntent();
        dispatchKey = dispatchCreateIntent.getStringExtra("dispatch_key");

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
        LatLng myLatLng = new LatLng(37.8087246, -122.4993607);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));

        addIcons();
    }

    // add donor icons
    private void addIcons() {

        model = ViewModelProviders.of(this).get(MapViewModel.class);
        model.setDispatchKey(dispatchKey);
        model.setupDispatchRootReference();

        // TODO: refactor to one generalized method for observing
        // halfway there, just need to use MappableObjects within the View Model
        observeCommunities();
        observeDonors();
        observeDrivers();

    }

    private void observeDrivers() {
        model.getDrivers().observe(this, dispatchDrivers -> {
            Marker mapMarker;

            LatLng driverLatLng = null;
            String driverUid;

            for (DispatchDriver dispatchDriver : dispatchDrivers) {

                // get uid
                driverUid = dispatchDriver.getUid().toString();

                // TODO still not just moving the marker : /
                if(!driverUidMap.containsKey(driverUid)) {
                    driverUidMap.put(driverUid, dispatchDriver);
                    iconDriverUidMap.put(driverUid, addMarker(driverLatLng, dispatchDriver));

                    Toast.makeText(this, "placed marker for first time", Toast.LENGTH_SHORT).show();
                } else {
                    driverUidMap.put(driverUid, dispatchDriver);
                    mapMarker = iconDriverUidMap.get(driverUid);

                    mapMarker = updateMarker(driverLatLng, mapMarker, dispatchDriver);

                    iconDriverUidMap.put(driverUid, mapMarker);
                    Toast.makeText(this, "moved marker for", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void observeCommunities() {
        model.getCommunities().observe(this, dispatchCommunities -> {
            Marker mapMarker;

            LatLng communityLatLng = null;
            String communityUid;

            for (DispatchCommunity dispatchCommunity : dispatchCommunities) {

                // get uid
                communityUid = dispatchCommunity.getUid().toString();

                // TODO still not just moving the marker : /
                if(!communityUidMap.containsKey(communityUid)) {
                    communityUidMap.put(communityUid, dispatchCommunity);
                    iconCommunityUidMap.put(communityUid, addMarker(communityLatLng, dispatchCommunity));

                    Toast.makeText(this, "placed marker for first time", Toast.LENGTH_SHORT).show();
                } else {
                    communityUidMap.put(communityUid, dispatchCommunity);
                    mapMarker = iconCommunityUidMap.get(communityUid);

                    mapMarker = updateMarker(communityLatLng, mapMarker, dispatchCommunity);

                    iconCommunityUidMap.put(communityUid, mapMarker);
                    Toast.makeText(this, "moved marker for", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void observeDonors() {

        model.getDonors().observe(this, dispatchDonors -> {
            Marker mapMarker;

            LatLng donorLatLng = null;
            String donorUid;

            for (DispatchDonor dispatchDonor : dispatchDonors) {

                // get uid
                donorUid = dispatchDonor.getUid().toString();

                // TODO still not just moving the marker : /
                if(!donorUidMap.containsKey(donorUid)) {
                    donorUidMap.put(donorUid, dispatchDonor);
                    iconDonorUidMap.put(donorUid, addMarker(donorLatLng, dispatchDonor));

                    Toast.makeText(this, "placed marker for first time", Toast.LENGTH_SHORT).show();
                } else {
                    donorUidMap.put(donorUid,dispatchDonor);
                    mapMarker = iconDonorUidMap.get(donorUid);

                    mapMarker = updateMarker(donorLatLng, mapMarker, dispatchDonor);

                    iconDonorUidMap.put(donorUid, mapMarker);
                    Toast.makeText(this, "moved marker for", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Marker addMarker(LatLng objectLatLng, MappableObject mappableObject) {
        // set LatLng
        objectLatLng = new LatLng(
                mappableObject.getLatitude(),
                mappableObject.getLongitude()
        );

        return mMap.addMarker(
                new MarkerOptions()
                        .position(objectLatLng)
                        .title(mappableObject.getName())
                        .icon(BitmapDescriptorFactory.fromResource(mappableObject.getIconId()))
        );
    }

    private Marker updateMarker(LatLng objectLatLng, Marker mapMarker, MappableObject mappableObject) {
        // set LatLng
        objectLatLng = new LatLng(
                mappableObject.getLatitude(),
                mappableObject.getLongitude()
        );

        mapMarker.setPosition(objectLatLng);
        mapMarker.setTitle(mappableObject.getName());

        if (mapMarker.isInfoWindowShown()) {
            // hide the info window
            mapMarker.setVisible(false);
            mapMarker.setVisible(true);

            // show the new info
            mapMarker.showInfoWindow();
        }

        return mapMarker;
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
