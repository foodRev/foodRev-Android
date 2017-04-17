package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DonorCommunityPairCreation.CommunityAllocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchCommunity;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;

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
    private DatabaseReference donorCommunityPairRoot;
    private HashMap<String, DispatchCommunity> priorCommunityDelegation;
    private float totalFoodCurrentlyAllocatedFromDonor = 0;

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
                float allocatedFoodTotal;
                float allocatedFoodFromListedDonor;

                // increment food allocation for this dispatch
                allocatedFoodTotal = dispatchCommunity.getAllocatedFood();
                allocatedFoodTotal++;
                dispatchCommunity.setAllocatedFood(allocatedFoodTotal);

                // increment food allocated from the given donor
                allocatedFoodFromListedDonor = dispatchCommunity.getAllocatedFromListedDonor();
                allocatedFoodFromListedDonor++;
                dispatchCommunity.setAllocatedFromListedDonor(allocatedFoodFromListedDonor);

                totalFoodCurrentlyAllocatedFromDonor++;
                donorAllocatedFoodView.setText(String.valueOf(totalFoodCurrentlyAllocatedFromDonor));

                /** TODO investigate effects of refactoring code to be aware of position
                 /*  this will allow us to notify only the item is changed only at the given position
                 */
                communityAllocationListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void decrementCommunityAllocationCount() {
        for (DispatchCommunity dispatchCommunity : dispatchCommunities) {
            if(dispatchCommunity.isSelected()) {
                float allocatedFoodTotal;
                float allocatedFoodFromListedDonor;

                // increment food allocation for this dispatch
                allocatedFoodTotal = dispatchCommunity.getAllocatedFood();
                allocatedFoodTotal--;
                dispatchCommunity.setAllocatedFood(allocatedFoodTotal);

                // increment food allocated from the given donor
                allocatedFoodFromListedDonor = dispatchCommunity.getAllocatedFromListedDonor();
                allocatedFoodFromListedDonor--;
                dispatchCommunity.setAllocatedFromListedDonor(allocatedFoodFromListedDonor);

                totalFoodCurrentlyAllocatedFromDonor--;
                donorAllocatedFoodView.setText(String.valueOf(totalFoodCurrentlyAllocatedFromDonor));


                /** TODO investigate effects of refactoring code to be aware of position
                 /*  this will allow us to notify only the item is changed only at the given position
                 */
                communityAllocationListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateAllocationPairs() {
        for (DispatchCommunity dispatchCommunity : dispatchCommunities) {
            if (dispatchCommunity.getAllocatedFromListedDonor() > 0) {
                donorCommunityPairRoot
                        .child(donorKey + ";" + dispatchCommunity.getCommunityUid())
                        .child("donorName").setValue(donorName);
                donorCommunityPairRoot
                        .child(donorKey + ";" + dispatchCommunity.getCommunityUid())
                        .child("communityName").setValue(dispatchCommunity.getCommunityName());
                donorCommunityPairRoot
                        .child(donorKey + ";" + dispatchCommunity.getCommunityUid())
                        .child("foodAllocation").setValue(dispatchCommunity.getAllocatedFromListedDonor());
            } else {
                donorCommunityPairRoot
                        .child(donorKey + ";" + dispatchCommunity.getCommunityUid())
                        .setValue(null);
            }
        }
    }

    private void populateDonorInformationPanel() {
        donorNameView = (TextView) findViewById(R.id.donor_community_pair_donor_name);
        donorNameView.setText(donorName);

        donorTotalFoodView = (TextView) findViewById(R.id.donor_community_pair_total_food);
        donorTotalFoodView.setText(donorTotalFood);

        donorAllocatedFoodView = (TextView) findViewById(R.id.donor_community_pair_allocated_food);
        donorAllocatedFoodView.setText(String.valueOf(totalFoodCurrentlyAllocatedFromDonor));
    }

    private void setupFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        // dispatch Root
        dispatchRoot = firebaseDatabase.getReference("/DISPATCHES");

        donorCommunityPairRoot = dispatchRoot
                .child(dispatchKey)
                .child("DONOR_COMMUNITY_PAIRS")
                .getRef();

        // community Root
        communityRoot = firebaseDatabase.getReference("/COMMUNITIES");
    }

    private void populateValues(){
        dispatchRoot.child(dispatchKey).child("COMMUNITIES").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String communityUid;
                    DispatchCommunity dispatchCommunity;

                    communityUid = snapshot.getKey().toString();
                    dispatchCommunity = new DispatchCommunity(
                            communityUid,
                            snapshot.child("communityName").getValue().toString(),
                            Float.parseFloat(snapshot.child("foodDonationCapacity").getValue().toString()),
                            false);

                    if(priorCommunityDelegation.containsKey(communityUid)) {
                        dispatchCommunity.setAllocatedFood(priorCommunityDelegation.get(communityUid).getAllocatedFood());
                        dispatchCommunity.setAllocatedFromListedDonor(priorCommunityDelegation.get(communityUid).getAllocatedFromListedDonor());
                    }

                    dispatchCommunities.add(0, dispatchCommunity);

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

    // TODO: consider factoring this out into an Abstract Select Class
    private void getExistingList() {

        // store values in temp dispatch community object, indexed by communityKey
        priorCommunityDelegation = new HashMap<>();

        donorCommunityPairRoot.addListenerForSingleValueEvent(new ValueEventListener() {

              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                  DispatchCommunity communityHolder;
                  float allocationTotal;
                  float allocationFromListedDonor;

                  for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                      String communityKeyHalf = snapshot.getKey().toString().split(";")[1];
                      String donorKeyHalf = snapshot.getKey().toString().split(";")[0];

                      // check if maps to this community
                      if (priorCommunityDelegation.containsKey(communityKeyHalf)) {
                          // pass object by reference
                          communityHolder = priorCommunityDelegation.get(communityKeyHalf);
                          allocationFromListedDonor = Float.parseFloat(snapshot.child("foodAllocation").getValue().toString());
                          //add this allocation to total
                          allocationTotal = communityHolder.getAllocatedFood() + allocationFromListedDonor;
                          communityHolder.setAllocatedFood(allocationTotal);

                          // check if donor is the one being allocated for currently
                          if (donorKeyHalf.equals(donorKey)) {
                              communityHolder.setAllocatedFromListedDonor(allocationFromListedDonor);

                              //increment donor allocation
                              totalFoodCurrentlyAllocatedFromDonor += allocationFromListedDonor;
                          }
                      } else {
                          // add community to hashmap if not already in list

                          communityHolder = new DispatchCommunity();

                          allocationTotal = Float.parseFloat(snapshot.child("foodAllocation").getValue().toString());
                          communityHolder.setAllocatedFood(allocationTotal);

                          if (donorKeyHalf.equals(donorKey)) {
                              allocationFromListedDonor = allocationTotal;
                              communityHolder.setAllocatedFromListedDonor(allocationFromListedDonor);

                              //increment donor allocation
                              totalFoodCurrentlyAllocatedFromDonor += allocationFromListedDonor;
                          }
                          priorCommunityDelegation.put(communityKeyHalf,communityHolder);
                      }
                  }
                  populateValues();
                  populateDonorInformationPanel();
              }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {
                      // TODO create tags for logging errors instead of hardcoding
                      Log.e("DispatchCommunitySelect", "onCancelled: " + databaseError.getMessage());
                  }
              }
        );


    }

    @Override
    public void onBackPressed() {
        updateAllocationPairs();
        super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

