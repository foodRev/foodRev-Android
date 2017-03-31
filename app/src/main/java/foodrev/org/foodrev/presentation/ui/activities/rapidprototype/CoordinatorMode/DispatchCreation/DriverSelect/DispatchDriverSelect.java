package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.DriverSelect;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDriver;

public class DispatchDriverSelect extends AppCompatActivity {

    // prior Intent
    Intent dispatchCreateIntent;
    String dispatchKey;

    // Firebase
    private FirebaseDatabase firebaseDatabase;
    // Dispatch Root
    private DatabaseReference dispatchRoot; //driving/unloading/loading
    // Donor at Root
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DispatchDriverSelect.this, "Driver update sent to cloud", Toast.LENGTH_SHORT).show();

                for (DispatchDriver dispatchDriver : dispatchDrivers) {
                    if (dispatchDriver.isSelected()) {
                        dispatchRoot.child(dispatchKey)
                                .child("DRIVERS")
                                .child(dispatchDriver.getDriverName()) //todo replace with unique id, which can then act as a pointer to other fields
                                .child("TRUNKS_OF_CAPACITY") //todo replace with unique id, which can then act as a pointer to other fields
                                .setValue(dispatchDriver.getVehicleFoodCapacity());
                    }
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupFirebase() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        // donor Root
        dispatchRoot = firebaseDatabase.getReference("/DISPATCHES");

        // driver Root
        driverRoot = firebaseDatabase.getReference("/DRIVERS");

        // note: this will also do the initial population of the list as well
        driverRoot.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                // update the client-side model
                dispatchDrivers.add( 0, new DispatchDriver(
                        dataSnapshot.getKey().toString(),
                        Integer.parseInt(dataSnapshot.child("TRUNKS_OF_CAPACITY").getValue().toString()),
                        false));

                // update the UI
                driverSelectAdapter.notifyItemInserted(0);

            }

            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(DispatchDriverSelect.this, "child changed", Toast.LENGTH_SHORT).show();
            }

            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Toast.makeText(DispatchDriverSelect.this, "child removed", Toast.LENGTH_SHORT).show();
            }

            public void onCancelled(DatabaseError e) {
            }
        });

    }

}
