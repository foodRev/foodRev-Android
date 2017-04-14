package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.DonorSelect;

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
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.Dispatch;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;

import static foodrev.org.foodrev.domain.models.dispatchModels.Dispatch.DispatchStatus.NEED_TO_PLAN;
import static java.security.AccessController.getContext;

public class DispatchDonorSelect extends AppCompatActivity {

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
    DonorSelectAdapter donorSelectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_donor_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get dispatch key
        dispatchCreateIntent = getIntent();
        dispatchKey = dispatchCreateIntent.getStringExtra("dispatch_key");


        // init recyclerview
        RecyclerView rvDispatchDonors = (RecyclerView) findViewById(R.id.rvDonorSelect);

        // create adapter and pass in the data
        donorSelectAdapter = new DonorSelectAdapter(this, dispatchDonors);

        // attach the adapter to the rv and populate items
        rvDispatchDonors.setAdapter(donorSelectAdapter);

        // set layout manager to position items
        rvDispatchDonors.setLayoutManager(new LinearLayoutManager(this));

        //setup Firebase must come after adapter is set up
        // and initialize donor array
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
        Toast.makeText(DispatchDonorSelect.this, "Donor update sent to cloud", Toast.LENGTH_SHORT).show();

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
        chosenItems = new ArrayList<>();
        dispatchRoot.child(dispatchKey).child("DONORS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get existing list into the chosen items array
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   chosenItems.add(snapshot.getKey().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DispatchDonorSelect", "onCancelled: " + databaseError.getMessage() );
            }
        });
    }

        private void populateList() {
            // note: this will also do the initial population of the list as well
            // update the client-side model
// update the UI
            donorRoot.addListenerForSingleValueEvent(new ValueEventListener() {

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

                        dispatchDonors.add( 0, new DispatchDonor(
                                snapshot.getKey().toString(),
                                snapshot.child("donorName").getValue().toString(),
                                Float.parseFloat(snapshot.child("carsOfFood").getValue().toString()),
                                isAlreadySelected));

                        // update the UI
                        donorSelectAdapter.notifyItemInserted(0);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // TODO create tags for logging errors instead of hardcoding
                    Log.e("DispatchDonorSelect", "onCancelled: " + databaseError.getMessage());

                }


            });
    }



}
