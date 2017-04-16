package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DonorCommunityPairCreation;


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
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;


public class DonorCommunityPairSelect extends AppCompatActivity {
    // for pre-selection highlight
    private ArrayList<String> chosenItems;
    private ValueEventListener communityListener;

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
    ArrayList<DispatchDonor> dispatchDonors = new ArrayList<>();

    // donor adapter
    DonorCommunityPairSelectAdapter donorCommunityPairSelectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_community_pair_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get dispatch key
        dispatchCreateIntent = getIntent();
        dispatchKey = dispatchCreateIntent.getStringExtra("dispatch_key");


        // init recyclerview
        RecyclerView rvDispatchDonors = (RecyclerView) findViewById(R.id.rvDonorSelect);

        // create adapter and pass in the data
        donorCommunityPairSelectAdapter = new DonorCommunityPairSelectAdapter(this, dispatchDonors);

        // attach the adapter to the rv and populate items
        rvDispatchDonors.setAdapter(donorCommunityPairSelectAdapter);

        // set layout manager to position items
        rvDispatchDonors.setLayoutManager(new LinearLayoutManager(this));

        //setup Firebase must come after adapter is set up
        // and initialize donor array
        setupFirebase();
        getExistingList();

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
        Toast.makeText(DonorCommunityPairSelect.this, "Donor update sent to cloud", Toast.LENGTH_SHORT).show();

        // list for individual drivers and characteristics
        HashMap<String,Object> characteristicList;

        // complete updated list to be pushed, this way only one update
        Map<String,Object> listUpdate = new HashMap<>();

        //
        for (DispatchDonor dispatchDonor : dispatchDonors) {
            if (dispatchDonor.isSelected()) {
                // init or re-init inner hashMap
                characteristicList = new HashMap<>();
                characteristicList.put("carsOfFood",dispatchDonor.getCarsOfFood());
                characteristicList.put("donorName",dispatchDonor.getDonorName());
                listUpdate.put(dispatchDonor.getDonorUid(),characteristicList);
            }
        }

        dispatchRoot.child(dispatchKey)
                .child("DONORS")
                .setValue(listUpdate);
    }

    private void setupFirebase() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        // donor Root
        dispatchRoot = firebaseDatabase.getReference("/DISPATCHES");

        // donor Root
        donorRoot = firebaseDatabase.getReference("/DONORS");

        // note: this will also do the initial population of the list as well donorRoot.addChildEventListener(new ChildEventListener() {
    }

    private void getExistingList() {
        // init or re-init the chosenItems array
        dispatchRoot.child(dispatchKey).child("DONORS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get existing list into the chosen items array
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                   dispatchDonors.add( 0, new DispatchDonor(
                            snapshot.getKey().toString(),
                            snapshot.child("donorName").getValue().toString(),
                            Float.parseFloat(snapshot.child("carsOfFood").getValue().toString()),
                            false));

                    // update the UI
                    donorCommunityPairSelectAdapter.notifyItemInserted(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DispatchDonorSelect", "onCancelled: " + databaseError.getMessage() );
            }
        });
    }




}
