package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverDelegation.DriverAssignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDriver;

public class DriverAssignmentActivity extends AppCompatActivity {

    // prior Intent
    Intent dispatchCreateIntent;
    String dispatchKey;

    // Firebase
    private FirebaseDatabase firebaseDatabase;
    // Dispatch Root
    private DatabaseReference dispatchRoot;

    // driver array
    ArrayList<DispatchDriver> dispatchDrivers = new ArrayList<>();

    // driver adapter
    DriverAssignmentAdapter driverAssignmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_assignment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get dispatch key
        dispatchCreateIntent = getIntent();
        dispatchKey = dispatchCreateIntent.getStringExtra("dispatch_key");


        setupRecyclerView();

        // setup firebase
        setupFirebase();

        // populate list with only selected drivers
        populateList();


        // TODO add onclick listeners to each of the drivers in the list
        // such that a new screen appears with tasklist

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupRecyclerView() {
        //TODO shares same structure as dispatch driver select, would prefer to reuse the codebase
        // init recyclerView
        RecyclerView rvDispatchDrivers = (RecyclerView) findViewById(R.id.rvDriverSelect);

        // create adapter and pass in the array to bind it
        driverAssignmentAdapter = new DriverAssignmentAdapter(this, dispatchDrivers);

        // attach adapter to rv to populate items
        rvDispatchDrivers.setAdapter(driverAssignmentAdapter);

        // set layout manager to position the items
        rvDispatchDrivers.setLayoutManager(new LinearLayoutManager(this));

    }

    private void sendListUpdate() {}

    private void setupFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();

        // dispatch root
        dispatchRoot = firebaseDatabase.getReference("/DISPATCHES");
    }

    private void getExistingList() {}

    private void populateList() {
        // note: this will also do the initial population of the list as well
        // update the client-side model
// update the UI
        dispatchRoot.child(dispatchKey).child("DRIVERS").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String itemKey = snapshot.getKey().toString();

                    // update the client-side model

                    dispatchDrivers.add(0, new DispatchDriver(
                            snapshot.getKey().toString(),
                            snapshot.child("driverName").getValue().toString(),
                            Float.parseFloat(snapshot.child("vehicleFoodCapacity").getValue().toString()),
                            false));

                    // update the UI
                    driverAssignmentAdapter.notifyItemInserted(0);
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
