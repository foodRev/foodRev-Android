package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.DriverMode;

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
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchRoute;
import foodrev.org.foodrev.domain.models.dispatchModels.driverInstructionModels.DriverTaskListItem;
import foodrev.org.foodrev.domain.models.dispatchModels.driverInstructionModels.DriverTaskListItemBuilder;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverDelegation.RouteListAdapter;

public class DriverTaskList extends AppCompatActivity {

    // prior Intent
    Intent dispatchCreateIntent;
    String dispatchKey;

    // Firebase Root Object
    private FirebaseDatabase firebaseDatabase;
    // Dispatch Root
    private DatabaseReference dispatchRoot; //driving/unloading/loading
    // Donor at Root
    private DatabaseReference taskRoot; //driving/unloading/loading

    // donor list to be tied to rv
    ArrayList<DriverTaskListItem> driverTaskListItems = new ArrayList<>();

    // donor adapter
    DriverTaskListAdapter driverTaskListAdapter;
    // NOTE: Section Complete
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // hardcoded dispatch key
        dispatchKey = "-KrauVDSgeB1pFojrI8-";


        // init recyclerview
        RecyclerView rvDriverTaskListItems = (RecyclerView) findViewById(R.id.rv_driver_task_list);

        // create adapter and pass in the data
        driverTaskListAdapter = new DriverTaskListAdapter(this, driverTaskListItems);

        // attach the adapter to the rv and populate items
        rvDriverTaskListItems.setAdapter(driverTaskListAdapter);

        // set layout manager to position items
        rvDriverTaskListItems.setLayoutManager(new LinearLayoutManager(this));

        //setup Firebase must come after adapter is set up
        // and initialize donor array
        setupFirebase();
        getExistingList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupFirebase() {

        firebaseDatabase = FirebaseDatabase.getInstance();

        // donor Root
        dispatchRoot = firebaseDatabase.getReference("/DISPATCHES");

    }

    private void getExistingList() {
        // init or re-init the chosenItems array
        dispatchRoot.child(dispatchKey)
                .child("DRIVERS")
                .child("-Kglr_sYcYReA0352RTx")
                .child("TASK_LIST")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get existing list into the chosen items array
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    float amountToLoad = Float.parseFloat(snapshot.child("amountToLoad").getValue().toString());
                    float amountToUnload = Float.parseFloat(snapshot.child("amountToUnload").getValue().toString());

                    double latitude = Double.parseDouble(
                            snapshot.child("locationLatLng")
                                    .child("latitude").getValue().toString());
                    double longitude = Double.parseDouble(
                            snapshot.child("locationLatLng")
                                    .child("longitude").getValue().toString());

                    String taskType = snapshot.child("taskType").getValue().toString();
                    String locationType = snapshot.child("locationType").getValue().toString();
                    String locationName = snapshot.child("locationName").getValue().toString();

                    DriverTaskListItemBuilder driverTaskListItemBuilder = new DriverTaskListItemBuilder();

                    DriverTaskListItem driverTaskListItem = driverTaskListItemBuilder
                            .setAmountToLoad(amountToLoad)
                            .setAmountToUnload(amountToUnload)
                            .setLatitude(latitude)
                            .setLongitude(longitude)
                            .setTaskType(taskType)
                            .setLocationType(locationType)
                            .setLocationName(locationName)
                            .createDriverTaskListItem();

                    driverTaskListItems.add(0, driverTaskListItem);

                    // update the UI
                    driverTaskListAdapter.notifyItemInserted(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DispatchDonorSelect", "onCancelled: " + databaseError.getMessage() );
            }
        });
    }
}
