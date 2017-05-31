package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverDelegation;

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
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchRoute;
import foodrev.org.foodrev.domain.models.dispatchModels.DonorToCommunityPair;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DonorCommunityPairCreation.DonorCommunityPairSelectAdapter;

public class RouteList extends AppCompatActivity {

    // prior Intent
    Intent dispatchCreateIntent;
    String dispatchKey;

    // Firebase Root Object
    private FirebaseDatabase firebaseDatabase;
    // Dispatch Root
    private DatabaseReference dispatchRoot; //driving/unloading/loading
    // Donor at Root
    private DatabaseReference donorRoot; //driving/unloading/loading

    // donor list to be tied to rv
    ArrayList<DispatchRoute> dispatchRoutes = new ArrayList<>();

    // donor adapter
    RouteListAdapter routeListAdapter;

    // NOTE: Section Complete
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get dispatch key
        dispatchCreateIntent = getIntent();
        dispatchKey = dispatchCreateIntent.getStringExtra("dispatch_key");

        // init recyclerview
        RecyclerView rvDispatchRoutes = (RecyclerView) findViewById(R.id.rvRouteSelect);

        // create adapter and pass in the data
        routeListAdapter = new RouteListAdapter(this, dispatchRoutes);

        // attach the adapter to the rv and populate items
        rvDispatchRoutes.setAdapter(routeListAdapter);

        // set layout manager to position items
        rvDispatchRoutes.setLayoutManager(new LinearLayoutManager(this));

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
        dispatchRoot.child(dispatchKey).child("DONOR_COMMUNITY_PAIRS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get existing list into the chosen items array
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                   String donorName;
                   String communityName;

                   String donorCommunityKey;
                   float foodDonations;

                   donorName = snapshot.child("donorName").getValue().toString();
                   communityName = snapshot.child("communityName").getValue().toString();
                   foodDonations = Float.parseFloat(snapshot.child("foodAllocation").getValue().toString());
                   donorCommunityKey = snapshot.getKey().toString();

                    DispatchRoute dispatchRoute = new DispatchRoute(
                            dispatchKey,
                            donorCommunityKey,
                            donorName,
                            communityName,
                            foodDonations
                    );

                    dispatchRoutes.add(0, dispatchRoute);

                    // update the UI
                    routeListAdapter.notifyItemInserted(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DispatchDonorSelect", "onCancelled: " + databaseError.getMessage() );
            }
        });
    }
}
