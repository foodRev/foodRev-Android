package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;

public class DriverMapping extends FragmentActivity
        implements OnMapReadyCallback, LifecycleRegistryOwner {

    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);


    private GoogleMap mMap;
    private MapViewModel model;
    private Intent dispatchCreateIntent;
    private String dispatchKey;

    HashMap<String,DispatchDonor> donorUidMap;
    HashMap<String, Marker> iconDonorUidMap;

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

        addDonorIcons();

    }

    // add donor icons
    private void addDonorIcons() {

        model = ViewModelProviders.of(this).get(MapViewModel.class);
        model.setDispatchKey(dispatchKey);
        model.setupDispatchRootReference();
        model.getDonors().observe(this, dispatchDonors -> {

            donorUidMap = new HashMap<>();
            iconDonorUidMap = new HashMap<>();

            Marker mapMarker;

            LatLng donorLatLng = null;
            String donorUid;

            for (DispatchDonor dispatchDonor : dispatchDonors) {

                // get uid
                donorUid = dispatchDonor.getDonorUid().toString();

                // TODO still not just moving the marker : /
                if(!donorUidMap.containsKey(donorUid)) {
                    donorUidMap.put(donorUid, dispatchDonor);
                    iconDonorUidMap.put(donorUid, addMarker(donorLatLng, dispatchDonor));

                    addMarker(donorLatLng, dispatchDonor);
                } else {
                    donorUidMap.put(donorUid,dispatchDonor);
                    mapMarker = iconDonorUidMap.get(donorUid);

                    mapMarker = updateMarker(donorLatLng, mapMarker, dispatchDonor);

                    iconDonorUidMap.put(donorUid, mapMarker);
                }

            }
        });
    }

    private Marker addMarker(LatLng donorLatLng, DispatchDonor dispatchDonor) {
        // set LatLng
        donorLatLng = new LatLng(
                dispatchDonor.getLatitude(),
                dispatchDonor.getLongitude()
        );

        return mMap.addMarker(
                new MarkerOptions()
                        .position(donorLatLng)
                        .title(dispatchDonor.getDonorName())
        );
    }

    private Marker updateMarker(LatLng donorLatLng, Marker mapMarker, DispatchDonor dispatchDonor) {
        // set LatLng
        donorLatLng = new LatLng(
                dispatchDonor.getLatitude(),
                dispatchDonor.getLongitude()
        );

        mapMarker.setPosition(donorLatLng);

        return mapMarker;
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
