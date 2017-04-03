package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.CommunitySelect;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchCommunity;

import static foodrev.org.foodrev.R.id.fab;

public class DispatchCommunitySelect extends AppCompatActivity {

    // prior Intent
    Intent dispatchCreateIntent;
    String dispatchKey;

    // Firebase Root Object
    private FirebaseDatabase firebaseDatabase;
    // Dispatch Root
    private DatabaseReference dispatchRoot; //driving/unloading/loading
    // Donor at Root
    private DatabaseReference communityRoot; //driving/unloading/loading

    // Community List to be tied to rv
    ArrayList<DispatchCommunity> dispatchCommunities = new ArrayList<>();

    // donor adapter
    CommunitySelectAdapter communitySelectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_community_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get dispatch key
        dispatchCreateIntent = getIntent();
        dispatchKey = dispatchCreateIntent.getStringExtra("dispatch_key");


        // init recyclerview
        RecyclerView rvDispatchCommunities = (RecyclerView) findViewById(R.id.rvCommunitySelect);

        // create adapter and pass in data
        communitySelectAdapter = new CommunitySelectAdapter(this, dispatchCommunities);

        // attach the adapter to the rv and populate items
        rvDispatchCommunities.setAdapter(communitySelectAdapter);

        // setup Firbase -- this must come after adapter setup
        // this will also initialize the community array
        setupFirebase();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DispatchCommunitySelect.this, "Community update sent to cloud", Toast.LENGTH_SHORT).show();

                for (DispatchCommunity dispatchCommunity : dispatchCommunities) {
                    if (dispatchCommunity.isSelected()) {
                        //TODO replace with hashmap update strategy for all communities at once instead of one at a time
                        dispatchRoot.child(dispatchKey)
                                .child("COMMUNITIES")
                                .child(dispatchCommunity.getCommunityUid())
                                .child("foodDonationCapacity")
                                .setValue(dispatchCommunity.getFoodDonationCapacity());
                        dispatchRoot.child(dispatchKey)
                                .child("COMMUNITIES")
                                .child(dispatchCommunity.getCommunityUid())
                                .child("communityName")
                                .setValue(dispatchCommunity.getCommunityName());
                    }
                }
            }
        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupFirebase() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        // community Root
        dispatchRoot = firebaseDatabase.getReference("/DISPATCHES");

        // community Root
        communityRoot = firebaseDatabase.getReference("/COMMUNITIES");

        // note: this will also do the initial population of the list as well
        communityRoot.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                // update the client-side model
                dispatchCommunities.add( 0, new DispatchCommunity(
                        dataSnapshot.getKey().toString(),
                        dataSnapshot.child("communityName").getValue().toString(),
                        Integer.parseInt(dataSnapshot.child("foodDonationCapacity").getValue().toString()),
                        false));

                // update the UI
                communitySelectAdapter.notifyItemInserted(0);

            }

            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(DispatchCommunitySelect.this, "child changed", Toast.LENGTH_SHORT).show();
            }

            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Toast.makeText(DispatchCommunitySelect.this, "child removed", Toast.LENGTH_SHORT).show();
            }

            public void onCancelled(DatabaseError e) {
            }
        });
    }
}
