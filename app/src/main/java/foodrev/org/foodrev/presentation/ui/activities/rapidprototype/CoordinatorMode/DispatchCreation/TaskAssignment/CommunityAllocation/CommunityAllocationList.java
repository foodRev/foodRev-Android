package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.CommunityAllocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchCommunity;

public class CommunityAllocationList extends AppCompatActivity {

    private String dispatchKey;
    private Intent priorIntent;
    private String donorKey;
    private String donorName;
    private String donorTotalFood;
    private String donorAllocatedFood;

    // Firebase Root Object
    private FirebaseDatabase firebaseDatabase;
    // Dispatch Root
    private DatabaseReference dispatchRoot; //driving/unloading/loading
    // Donor at Root
    private DatabaseReference communityRoot; //driving/unloading/loading

    // Community List to be tied to rv
    ArrayList<DispatchCommunity> dispatchCommunities = new ArrayList<>();

    // donor adapter
    CommunityAllocationListAdapter communityAllocationListAdapter;
    private ArrayList<String> chosenItems;
    private ValueEventListener communityListener;
    private TextView donorNameView;
    private TextView donorTotalFoodView;
    private TextView donorAllocatedFoodView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_allocation_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // TODO re-implement via serializable or parcelable

        // retrieve dispatch and donor info
        priorIntent = getIntent();
        dispatchKey = priorIntent.getStringExtra("dispatch_key");
        donorKey = priorIntent.getStringExtra("donor_key");
        donorName = priorIntent.getStringExtra("donor_name");
        donorTotalFood = priorIntent.getStringExtra("donor_total_food");
        donorAllocatedFood = priorIntent.getStringExtra("donor_allocated_food");

        // populate values in text views
        populateDonorInformationPanel();

        // init recyclerview
        RecyclerView rvDispatchCommunities = (RecyclerView) findViewById(R.id.rvCommunitySelect);

        // create adapter and pass in data
        communityAllocationListAdapter = new CommunityAllocationListAdapter(this, dispatchCommunities);

        // attach the adapter to the rv and populate items
        rvDispatchCommunities.setAdapter(communityAllocationListAdapter);

        // setup Firebase -- this must come after adapter setup
        // initialize the community array in onResume
        setupFirebase();
        getExistingList();

        // TODO(gskielian): add second fab and create +/- option for food units
        FloatingActionButton fabPlus = (FloatingActionButton) findViewById(R.id.food_allocation_adjust_plus);
        fabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementCommunityAllocationCount();
            }
        });

        FloatingActionButton fabMinus = (FloatingActionButton) findViewById(R.id.food_allocation_adjust_minus);
        fabMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrementCommunityAllocationCount();
            }
        });
    }
    private void incrementCommunityAllocationCount() {
        for (DispatchCommunity dispatchCommunity : dispatchCommunities) {
            if(dispatchCommunity.isSelected()) {
                float allocatedFood;

                allocatedFood = dispatchCommunity.getAllocatedFood();

                allocatedFood++;

                dispatchCommunity.setAllocatedFood(allocatedFood);
                Toast.makeText(this, dispatchCommunity.getCommunityName() + " increased to " + String.valueOf(allocatedFood) , Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void decrementCommunityAllocationCount() {
        for (DispatchCommunity dispatchCommunity : dispatchCommunities) {
            if(dispatchCommunity.isSelected()) {
                float allocatedFood;

                allocatedFood = dispatchCommunity.getAllocatedFood();

                allocatedFood--;

                dispatchCommunity.setAllocatedFood(allocatedFood);
                Toast.makeText(this, dispatchCommunity.getCommunityName() + " decreased to " + String.valueOf(allocatedFood) , Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void populateDonorInformationPanel() {
        donorNameView = (TextView) findViewById(R.id.donor_community_pair_donor_name);
        donorNameView.setText(donorName);

        donorTotalFoodView = (TextView) findViewById(R.id.donor_community_pair_total_food);
        donorTotalFoodView.setText(donorTotalFood);

        donorAllocatedFoodView = (TextView) findViewById(R.id.donor_community_pair_allocated_food);
        donorAllocatedFoodView.setText(donorAllocatedFood);
    }

    private void setupFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        // community Root
        dispatchRoot = firebaseDatabase.getReference("/DISPATCHES");

        // community Root
        communityRoot = firebaseDatabase.getReference("/COMMUNITIES");
    }

    // TODO: consider factoring this out into an Abstract Select Class
    private void getExistingList() {
        dispatchRoot.child(dispatchKey).child("COMMUNITIES").addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    dispatchCommunities.add( 0, new DispatchCommunity(
                            snapshot.getKey().toString(),
                            snapshot.child("communityName").getValue().toString(),
                            Float.parseFloat(snapshot.child("foodDonationCapacity").getValue().toString()),
                            false));

                    // update the UI
                    communityAllocationListAdapter.notifyItemInserted(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO create tags for logging errors instead of hardcoding
                Log.e("DispatchCommunitySelect", "onCancelled: " + databaseError.getMessage());

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

