package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.DriverSelect;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDriver;
import foodrev.org.foodrev.domain.models.dispatchModels.Builders.DispatchDriverBuilder;

public class DispatchDriverSelect extends AppCompatActivity {

    // highlight pre-selected items
    private ArrayList<String> chosenItems;
    private ValueEventListener driverListener;


    // prior Intent
    Intent dispatchCreateIntent;
    String dispatchKey;

    // Firebase
    private FirebaseDatabase firebaseDatabase;
    // Dispatch Root
    private DatabaseReference dispatchRoot; //driving/unloading/loading
    // Driver root list
    private DatabaseReference driverRoot; //driving/unloading/loading

    // driver array
    ArrayList<DispatchDriver> dispatchDrivers = new ArrayList<>();

    // driver adapter
    DriverSelectAdapter driverSelectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_driver_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get dispatch key
        dispatchCreateIntent = getIntent();
        dispatchKey = dispatchCreateIntent.getStringExtra("dispatch_key");


        // init recyclerView
        RecyclerView rvDispatchDrivers = (RecyclerView) findViewById(R.id.rvDriverSelect);

        // create adapter and pass in the array to bind it
        driverSelectAdapter = new DriverSelectAdapter(this, dispatchDrivers);

        // attach the adapter to the rv and populate it
        rvDispatchDrivers.setAdapter(driverSelectAdapter);

        // set layout manager to position the items
        rvDispatchDrivers.setLayoutManager(new LinearLayoutManager(this));

        // setup firebase
        // note 1: requires the rv to be setup
        // note 2: this will initialize the driver array
        setupFirebase();
        getExistingList();
        populateList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendListUpdate();
            }
        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void sendListUpdate() {
        Toast.makeText(DispatchDriverSelect.this, "Driver update sent to cloud", Toast.LENGTH_SHORT).show();

        // list for individual drivers and characteristics
        HashMap<String,Object> characteristicList;

        // complete updated list to be pushed, this way only one update
        Map<String,Object> listUpdate = new HashMap<>();

        //
        for (DispatchDriver dispatchDriver : dispatchDrivers) {
            if (dispatchDriver.isSelected()) {
                // init or re-init inner hashMap
                characteristicList = new HashMap<>();
                characteristicList.put("vehicleFoodCapacity",dispatchDriver.getVehicleFoodCapacity());
                characteristicList.put("currentAmountOfFoodCarrying",0);
                characteristicList.put("driverName",dispatchDriver.getName());
                listUpdate.put(dispatchDriver.getUid(),characteristicList);
            }
        }

        dispatchRoot.child(dispatchKey)
                .child("DRIVERS")
                .setValue(listUpdate);
    }

    private void setupFirebase() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        // dispatch Root
        dispatchRoot = firebaseDatabase.getReference("/DISPATCHES");

        // driver Root
        driverRoot = firebaseDatabase.getReference("/DRIVERS");
    }

    private void getExistingList() {
        chosenItems = new ArrayList<>();
        dispatchRoot.child(dispatchKey).child("DRIVERS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    chosenItems.add(snapshot.getKey().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DispatchDriverSelect", "onCancelled: " + databaseError.getMessage());
            }
        });

    }

    private void populateList() {
        // note: this will also do the initial population of the list as well
        // update the client-side model
// update the UI
        driverRoot.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String itemKey = snapshot.getKey().toString();
                    Boolean isAlreadySelected = false;

                    for (String chosenItem : chosenItems) {
                        if(itemKey.equals(chosenItem)) {
                            isAlreadySelected = true;
                            break;
                        }
                    }

                    // update the client-side model

                    dispatchDrivers.add(0, new DispatchDriverBuilder()
                            .setUid(snapshot.getKey().toString())
                            .setName(snapshot.child("driverName").getValue().toString())
                            .setVehicleFoodCapacity(Float.parseFloat(snapshot.child("vehicleFoodCapacity").getValue().toString()))
                            .setIsSelected(isAlreadySelected)
                            .createDispatchDriver());

                    // update the UI
                    driverSelectAdapter.notifyItemInserted(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO create tags for logging errors instead of hardcoding
                Log.e("DispatchDriverSelect", "onCancelled: " + databaseError.getMessage());
            }

        });
    }

}
