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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.Dispatch;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;

public class DispatchDonorSelect extends AppCompatActivity {

    // prior Intent
    Intent dispatchCreateIntent;
    String dispatchKey;


    // Firebase
    private FirebaseDatabase firebaseDatabase;
    // Dispatch Root
    private DatabaseReference dispatchRoot; //driving/unloading/loading

    ArrayList<DispatchDonor> dispatchDonors = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_donor_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get dispatch key
        dispatchCreateIntent = getIntent();
        dispatchKey = dispatchCreateIntent.getStringExtra("dispatch_key");


        //setup Firebase
        setupFirebase();


        // init recyclerview
        RecyclerView rvDispatchDonors = (RecyclerView) findViewById(R.id.rvDonorSelect);

        // initialize donor array
        dispatchDonors.add(new DispatchDonor("Donor M", 10, false));
        dispatchDonors.add(new DispatchDonor("Donor P", 2, true));
        dispatchDonors.add(new DispatchDonor("Donor L", 2, true));
        dispatchDonors.add(new DispatchDonor("Donor T", 2, true));

        // create adapter and pass in the data
        DonorSelectAdapter donorSelectAdapter = new DonorSelectAdapter(this,dispatchDonors);

        // attach the adapter to the rv and populate items
        rvDispatchDonors.setAdapter(donorSelectAdapter);

        // set layout manager to position items
        rvDispatchDonors.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DispatchDonorSelect.this, "Donor update sent to cloud", Toast.LENGTH_SHORT).show();

                for (DispatchDonor dispatchDonor : dispatchDonors) {
                    dispatchRoot.child(dispatchKey)
                            .child("DONORS")
                            .child(dispatchDonor.getDonorName()) //todo replace with unique id, which can then act as a pointer to other fields
                            .child("CARS_OF_FOOD") //todo replace with unique id, which can then act as a pointer to other fields
                            .setValue(dispatchDonor.getCarsOfFood());
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupFirebase() {

        firebaseDatabase = FirebaseDatabase.getInstance();

        //dispatch Root
        dispatchRoot = firebaseDatabase.getReference("/DISPATCH");
    }


}
