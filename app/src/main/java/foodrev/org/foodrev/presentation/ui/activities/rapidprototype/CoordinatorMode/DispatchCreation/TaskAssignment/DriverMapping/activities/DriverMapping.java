package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.activities;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchCommunity;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDriver;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.Interfaces.MappableObject;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata.DriverTaskList;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata.DriverTaskListBuilder;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata.TaskItem;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata.TaskItemBuilder;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.viewmodels.MapViewModel;

public class DriverMapping extends FragmentActivity
        implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback, LifecycleRegistryOwner {

    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    // Floating action button
    private FloatingActionButton upload_task_fab;
    private FloatingActionButton previous_driver_fab;
    private FloatingActionButton next_driver_fab;

    // Polyline
    private Polyline routeLine;

    // TODO use this to replace waypoints
    private DriverTaskListBuilder driverTaskListBuilder;
    private TaskItemBuilder taskItemBuilder;
    private DriverTaskList driverTaskList;
    private List<TaskItem> taskItemList = new ArrayList<>();
    private TaskItem taskItem;

    // TODO replace with DriverTask
    private LatLng waypoint;
    private List<LatLng> waypoints = new ArrayList<>();

    private GoogleMap mMap;
    private MapViewModel model;
    private Intent dispatchCreateIntent;
    private String dispatchKey;

    private HashMap<String,DispatchDonor> donorUidMap = new HashMap<>();
    private HashMap<String, Marker> iconDonorUidMap = new HashMap<>();

    private HashMap<String,DispatchCommunity> communityUidMap = new HashMap<>();
    private HashMap<String, Marker> iconCommunityUidMap = new HashMap<>();

    private TreeMap<String,DispatchDriver> driverUidMap = new TreeMap<>();
    private HashMap<String, Marker> iconDriverUidMap = new HashMap<>();

    // current textview
    private String currentTextViewHash = null;


    private PolylineOptions polylineOptions;
    private TextView driverNameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_mapping);
        driverNameTV = (TextView) findViewById(R.id.map_view_driver_name);

        // get dispatch key
        dispatchCreateIntent = getIntent();
        dispatchKey = dispatchCreateIntent.getStringExtra("dispatch_key");

        // setup builders
        driverTaskListBuilder = new DriverTaskListBuilder();
        driverTaskList = driverTaskListBuilder.createDriverTaskList();
        taskItemBuilder = new TaskItemBuilder();

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
        addRouteLine();
        setupButtons();
    }


    private void setupButtons() {
       upload_task_fab = (FloatingActionButton) findViewById(R.id.upload_task_list);
       previous_driver_fab = (FloatingActionButton) findViewById(R.id.previous_driver);
       next_driver_fab = (FloatingActionButton) findViewById(R.id.next_driver);

        upload_task_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO
                driverTaskList.setDispatchId(dispatchKey);
                driverTaskList.setDriverHashId(currentTextViewHash);
                driverTaskList.setTaskItemList(taskItemList);

                model.updateDriverTaskList(driverTaskList);
                Toast.makeText(DriverMapping.this, "uploaded", Toast.LENGTH_SHORT).show();
            }
        });
        previous_driver_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO
                if(driverUidMap.isEmpty()) {
                    Toast.makeText(DriverMapping.this, "no dispatch drivers", Toast.LENGTH_SHORT).show();
                } else if (currentTextViewHash != null){
                    if (driverUidMap.lowerKey(currentTextViewHash) != null) {
                        currentTextViewHash = driverUidMap.lowerKey(currentTextViewHash);
                        model.setCurrentDriverHash(currentTextViewHash);
                    }
                }
                Toast.makeText(DriverMapping.this, "previous driver", Toast.LENGTH_SHORT).show();
            }
        });
       next_driver_fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(driverUidMap.isEmpty()) {
                   Toast.makeText(DriverMapping.this, "no dispatch drivers", Toast.LENGTH_SHORT).show();
               } else if (currentTextViewHash != null){
                   if (driverUidMap.higherKey(currentTextViewHash) != null) {
                       currentTextViewHash = driverUidMap.higherKey(currentTextViewHash);
                       model.setCurrentDriverHash(currentTextViewHash);
                   }
               }
               // TODO
               Toast.makeText(DriverMapping.this, "next driver", Toast.LENGTH_SHORT).show();
           }
       });
    }

    // add map icons
    private void addIcons() {

        model = ViewModelProviders.of(this).get(MapViewModel.class);
        model.setDispatchKey(dispatchKey);

        // TODO: refactor to one generalized method for observing
        // halfway there, just need to use MappableObjects within the View Model
        observeTextView();
        observeDrivers();
        observeCommunities();
        observeDonors();
    }

    // Route Line Setup
    private void addRouteLine() {
        // setup icon click listeners on Marker for route line
        mMap.setOnMarkerClickListener(this);

        // set polyline -- aka routeLine -- features
        polylineOptions = new PolylineOptions()
                .color(Color.BLUE)
                .geodesic(true);

        // initialize the routeline (won't show until 2 or more points)
        routeLine = mMap.addPolyline(polylineOptions);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // get latlng
        if(marker.getTag().toString() != "DRIVER") {

            if(marker.getTag().toString() == "DONOR") {
                taskItem = taskItemBuilder.setAmountToLoad(30f)
                        .setLocationName("A DONOR")
                        .setLocationType(TaskItem.LocationType.DONOR)
                        .setTaskType(TaskItem.TaskType.LOAD)
                        .setLocationLatLng(marker.getPosition())
                        .createTaskItem();
            } else if (marker.getTag().toString() == "COMMUNITY") {
                taskItem = taskItemBuilder.setAmountToLoad(30f)
                        .setLocationName("A COMMUNITY")
                        .setLocationType(TaskItem.LocationType.COMMUNITY)
                        .setTaskType(TaskItem.TaskType.UNLOAD)
                        .setLocationLatLng(marker.getPosition())
                        .createTaskItem();
            }

            taskItemList.add(taskItem);

            waypoint = marker.getPosition();
            waypoints.add(waypoint);
            routeLine.setPoints(waypoints);
        }
        return true;
    }

    // End Route Line setup methods


    private void observeTextView() {
        model.getCurrentDriverHash().observe(this, currentLiveDriverHash -> {
            if(currentLiveDriverHash != null && !driverUidMap.isEmpty()) {
                driverNameTV.setText(driverUidMap.get(currentLiveDriverHash).getName());
            }
        });
    }

    // Start Observation methods
    private void observeDrivers() {
        model.getDrivers().observe(this, dispatchDrivers -> {
            Marker mapMarker;

            LatLng driverLatLng = null;
            String driverUid;

            DispatchDriver dispatchDriver;

            //remove stale markers
            for (String iconUid : driverUidMap.keySet()) {
                if(!dispatchDrivers.containsKey(iconUid)) {

                    // remove from driver Uid Map
                    driverUidMap.remove(iconUid);

                    // remove icon
                    mapMarker = iconDriverUidMap.get(iconUid);
                    mapMarker.remove();

                    // remove from icon hashmap
                    iconDriverUidMap.remove(iconUid);

                }
            }
                
            //add new markers, and update existing markers
            for (Map.Entry<String, DispatchDriver> stringDispatchDriverEntry : dispatchDrivers.entrySet()) {
                stringDispatchDriverEntry.getKey();

                // get uid
                driverUid = stringDispatchDriverEntry.getKey();
                dispatchDriver = stringDispatchDriverEntry.getValue();
                //driverUid = dispatchDriver.getUid().toString();

                // place marker for first time if not in list
                if(!driverUidMap.containsKey(driverUid)) {
                    driverUidMap.put(driverUid, dispatchDriver);
                    mapMarker = addMarker(driverLatLng, dispatchDriver);
                    mapMarker.setTag("DRIVER");
                    iconDriverUidMap.put(driverUid, mapMarker);


                    //uncomment for live placement notification
                    //Toast.makeText(this, "placed marker for first time for " + dispatchDriver.getName(), Toast.LENGTH_SHORT).show();

                    // update marker if possible
                } else {

                    driverUidMap.put(driverUid, dispatchDriver);
                    mapMarker = iconDriverUidMap.get(driverUid);

                    mapMarker = updateMarker(driverLatLng, mapMarker, dispatchDriver);

                    iconDriverUidMap.put(driverUid, mapMarker);

                    //uncomment for live update notification
                    //Toast.makeText(this, "moved marker for " + dispatchDriver.getName(), Toast.LENGTH_SHORT).show();
                }
            }

            if (!driverUidMap.isEmpty() && currentTextViewHash == null) {
                currentTextViewHash = driverUidMap.firstKey();
                model.setCurrentDriverHash(currentTextViewHash);
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

                if(!communityUidMap.containsKey(communityUid)) {
                    communityUidMap.put(communityUid, dispatchCommunity);
                    mapMarker = addMarker(communityLatLng, dispatchCommunity);
                    mapMarker.setTag("COMMUNITY");
                    iconCommunityUidMap.put(communityUid, mapMarker);

                    //Toast.makeText(this, "placed marker for first time", Toast.LENGTH_SHORT).show();
                } else {
                    communityUidMap.put(communityUid, dispatchCommunity);
                    mapMarker = iconCommunityUidMap.get(communityUid);

                    mapMarker = updateMarker(communityLatLng, mapMarker, dispatchCommunity);

                    iconCommunityUidMap.put(communityUid, mapMarker);
                    //Toast.makeText(this, "moved marker for", Toast.LENGTH_SHORT).show();
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

                if(!donorUidMap.containsKey(donorUid)) {
                    donorUidMap.put(donorUid, dispatchDonor);
                    mapMarker = addMarker(donorLatLng, dispatchDonor);
                    mapMarker.setTag("DONOR");
                    iconDonorUidMap.put(donorUid, mapMarker);

                    //Toast.makeText(this, "placed marker for first time", Toast.LENGTH_SHORT).show();
                } else {
                    donorUidMap.put(donorUid,dispatchDonor);
                    mapMarker = iconDonorUidMap.get(donorUid);

                    mapMarker = updateMarker(donorLatLng, mapMarker, dispatchDonor);

                    iconDonorUidMap.put(donorUid, mapMarker);
                    //Toast.makeText(this, "moved marker for", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // end observer methods


    // start marker update methods
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

    // end marker update methods

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
