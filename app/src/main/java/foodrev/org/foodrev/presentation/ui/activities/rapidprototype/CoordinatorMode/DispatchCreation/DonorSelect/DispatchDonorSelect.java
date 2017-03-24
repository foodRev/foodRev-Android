package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.DonorSelect;

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
import foodrev.org.foodrev.domain.models.dispatchModels.Dispatch;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;

import static foodrev.org.foodrev.domain.models.dispatchModels.Dispatch.DispatchStatus.NEED_TO_PLAN;
import static java.security.AccessController.getContext;

public class DispatchDonorSelect extends AppCompatActivity {

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

        // manual setting for testing
//        dispatchDonors.add(new DispatchDonor("Donor M", 2, false));
//        dispatchDonors.add(new DispatchDonor("Donor P", 4, true));
//        dispatchDonors.add(new DispatchDonor("Donor T", 3, true));
//        dispatchDonors.add(new DispatchDonor("Donor W", 2, true));
//        dispatchDonors.add(new DispatchDonor("Donor S", 3, true));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DispatchDonorSelect.this, "Donor update sent to cloud", Toast.LENGTH_SHORT).show();

                for (DispatchDonor dispatchDonor : dispatchDonors) {
                    if (dispatchDonor.isSelected()) {
                        dispatchRoot.child(dispatchKey)
                                .child("DONORS")
                                .child(dispatchDonor.getDonorName()) //todo replace with unique id, which can then act as a pointer to other fields
                                .child("CARS_OF_FOOD") //todo replace with unique id, which can then act as a pointer to other fields
                                .setValue(dispatchDonor.getCarsOfFood());
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

        // donor Root
        donorRoot = firebaseDatabase.getReference("/DONORS");

        // note: this will also do the initial population of the list as well
        donorRoot.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                // update the client-side model
                dispatchDonors.add( 0, new DispatchDonor(
                        dataSnapshot.getKey().toString(),
                        Integer.parseInt(dataSnapshot.child("CARS_OF_FOOD").getValue().toString()),
                        false));

                // update the UI
                donorSelectAdapter.notifyItemInserted(0);

            }

            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(DispatchDonorSelect.this, "child changed", Toast.LENGTH_SHORT).show();
            }

            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Toast.makeText(DispatchDonorSelect.this, "child removed", Toast.LENGTH_SHORT).show();
            }

            public void onCancelled(DatabaseError e) {
            }
        });
    }


}
