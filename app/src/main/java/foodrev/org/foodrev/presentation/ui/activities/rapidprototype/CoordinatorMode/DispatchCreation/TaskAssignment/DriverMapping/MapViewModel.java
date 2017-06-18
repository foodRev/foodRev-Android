package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import foodrev.org.foodrev.domain.models.dispatchModels.Builders.DispatchDonorBuilder;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchCommunity;
import foodrev.org.foodrev.domain.models.dispatchModels.Builders.DispatchCommunityBuilder;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDriver;
import foodrev.org.foodrev.domain.models.dispatchModels.Builders.DispatchDriverBuilder;

import static android.content.ContentValues.TAG;

/**
 * Created by foodRev on 5/29/17.
 */

public class MapViewModel extends ViewModel {

    // for holding listeners
    HashMap<String, ValueEventListener> driverListeners = new HashMap<>();

    // necessary to figure out which dispatch
    private String dispatchKey;

    // Firebase References
    private DatabaseReference rootReference;
    private DatabaseReference dispatchRootReference;

    // Live Data Donors
    private MutableLiveData<ArrayList<DispatchDonor>> dispatchDonors;
    private ArrayList<DispatchDonor> dispatchDonorsArray = new ArrayList<>();

    // Live Data Drivers
    private MutableLiveData<HashMap<String,DispatchDriver>> dispatchDrivers;
    private HashMap<String,DispatchDriver> hashMapDispatchDrivers = new HashMap<>();
    private ArrayList<String> dispatchDriverIds = new ArrayList<>();

    // Live Data Communities
    private MutableLiveData<ArrayList<DispatchCommunity>> dispatchCommunities;
    private ArrayList<DispatchCommunity> dispatchCommunitiesArray = new ArrayList<>();

    // live monitoring of donors
    public LiveData<ArrayList<DispatchDonor>> getDonors() {
        if(dispatchDonors == null) {
            dispatchDonors = new MutableLiveData<>();
            loadDispatchDonors();
        }
        return dispatchDonors;
    }
    // live monitoring of drivers
    public LiveData<HashMap<String,DispatchDriver>> getDrivers() {
        if(dispatchDrivers == null) {
            dispatchDrivers = new MutableLiveData<>();
            loadDispatchDrivers();
        }
        return dispatchDrivers;
    }
    // live monitoring of donors
    public LiveData<ArrayList<DispatchCommunity>> getCommunities() {
        if(dispatchCommunities == null) {
            dispatchCommunities = new MutableLiveData<>();
            loadDispatchCommunities();
        }
        return dispatchCommunities;
    }

    private void loadDispatchDonors() {

        dispatchRootReference.child("DONORS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // reset
                dispatchDonorsArray.clear();
                dispatchDonors.setValue(dispatchDonorsArray);

                // database related uid
                String donorUid;

                // donor human readable name
                String donorName;

                // food donation amount measured in "cars of food"
                float carsOfFood;

                // gps
                double latitude;
                double longitude;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    donorUid = snapshot.getKey().toString();
                    donorName = snapshot.child("donorName").getValue().toString();
                    carsOfFood = Float.parseFloat(snapshot.child("carsOfFood").getValue().toString());
                    latitude = Double.parseDouble(snapshot.child("latitude").getValue().toString());
                    longitude = Double.parseDouble(snapshot.child("longitude").getValue().toString());

                    // add dispatch donor to arraylist
                    dispatchDonorsArray.add(0, new DispatchDonorBuilder()
                            .setDonorUid(donorUid)
                            .setDonorName(donorName)
                            .setCarsOfFood(carsOfFood)
                            .setLatitude(latitude)
                            .setLongitude(longitude)
                            .createDispatchDonorWithLatLng());
                }

                // update liveData
                dispatchDonors.setValue(dispatchDonorsArray);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MapViewModel", "onCancelled: " + databaseError.toString());
            }
        });
    }


    private void removeDriverListener(String driverHashId) {

        // remove the listeners here
        rootReference.removeEventListener(driverListeners.get(driverHashId));
        driverListeners.remove(driverHashId);

    }

    private void addDriverListener(String driverHashId) {

        // TODO: refactor to add a new listener for each driver
        ValueEventListener valueEventListener = rootReference.child("DRIVERS").child(driverHashId).addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot dataSnapshot) {
                             // database related uid
                             String driverUid;

                             // driver name
                             String driverName;

                             // TODO: add food capacity
                             // food donation amount measured in "cars of food"
                             float vehicleFoodCapacity;
                             float currentAmountOfFoodCarrying;

                             // gps
                             double latitude;
                             double longitude;

                             driverName = dataSnapshot.child("driverName").getValue().toString();
                             latitude = Double.parseDouble(dataSnapshot.child("latitude").getValue().toString());
                             longitude = Double.parseDouble(dataSnapshot.child("longitude").getValue().toString());

                             // put this object into the array with the hash
                             if (hashMapDispatchDrivers.containsKey(driverHashId)) {

                                 DispatchDriver dispatchDriver = hashMapDispatchDrivers.get(driverHashId);
                                 dispatchDriver.setName(driverName);
                                 dispatchDriver.setLatitude(latitude);
                                 dispatchDriver.setLongitude(longitude);

                                 // TODO: experiment if this is necessary, as we should be affecting via reference
                                 hashMapDispatchDrivers.put(driverHashId,dispatchDriver);
                                 dispatchDrivers.setValue(hashMapDispatchDrivers);

                                 } else {

                                 // add dispatch driver to hashmap
                                 hashMapDispatchDrivers.put(driverHashId, new DispatchDriverBuilder()
                                         .setUid(driverHashId)
                                         .setName(driverName)
                                         .setLatitude(latitude)
                                         .setLongitude(longitude)
                                         .createDispatchDriverWithLatLng());
                                 dispatchDrivers.setValue(hashMapDispatchDrivers);
                                 Log.d(TAG, "hashmap: " + hashMapDispatchDrivers.toString());
                             }

                         }

                         @Override
                         public void onCancelled(DatabaseError databaseError) {

                         }
                     }
        );
        // save the value event listeners in a hashtable with the driverHashId as key
        driverListeners.put(driverHashId, valueEventListener);

        Log.d(TAG, "addDriverListener: " + driverListeners.toString());
    }

    private void loadDispatchDrivers() {


        //TODO: keep a list of existing driver hashes, use this only to monitor the addition
        //TODO: and removal of driver hash ids to this list
        dispatchRootReference.child("DRIVERS").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    String snapshotKey = dataSnapshot.getKey().toString();
                    if (!hashMapDispatchDrivers.containsKey(snapshotKey)) {
                        addDriverListener(snapshotKey); //this also does the job of adding key to list
                    }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                //loop to hashmap, verify that each key is still in list
                //else remove the key

                String snapshotKey = null;

                snapshotKey = dataSnapshot.getKey().toString();

                if(hashMapDispatchDrivers.containsKey(snapshotKey)) {

                        //remove listener
                        removeDriverListener(snapshotKey); //this also does the job of adding key to list
                        //remove from hashmap
                        hashMapDispatchDrivers.remove(snapshotKey);
                        //update dispatch drivers
                        dispatchDrivers.setValue(hashMapDispatchDrivers);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void loadDispatchCommunities() {

        dispatchRootReference.child("COMMUNITIES").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // reset
                dispatchCommunitiesArray.clear();
                dispatchCommunities.setValue(dispatchCommunitiesArray);

                // database related uid
                String communityUid;

                // community human readable name
                String communityName;

                // food donation amount measured in "cars of food"
                float foodDonationCapacity;

                // gps
                double latitude;
                double longitude;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    communityUid = snapshot.getKey().toString();
                    communityName = snapshot.child("communityName").getValue().toString();
                    foodDonationCapacity = Float.parseFloat(snapshot.child("foodDonationCapacity").getValue().toString());
                    latitude = Double.parseDouble(snapshot.child("latitude").getValue().toString());
                    longitude = Double.parseDouble(snapshot.child("longitude").getValue().toString());

                    // add dispatch community to arraylist
                    dispatchCommunitiesArray.add(0, new DispatchCommunityBuilder()
                            .setUid(communityUid)
                            .setName(communityName)
                            .setFoodDonationCapacity(foodDonationCapacity)
                            .setLatitude(latitude)
                            .setLongitude(longitude)
                            .createDispatchCommunityWithLatLng());
                }

                // update liveData
                dispatchCommunities.setValue(dispatchCommunitiesArray);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MapViewModel", "onCancelled: " + databaseError.toString());
            }
        });
    }

    // Getters and setters
    public String getDispatchKey() {
        return dispatchKey;
    }

    public void setDispatchKey(String dispatchKey) {
        this.dispatchKey = dispatchKey;
    }

    public void setupReferences() {
        rootReference = FirebaseDatabase.getInstance().getReference("/");
        dispatchRootReference = FirebaseDatabase.getInstance().getReference("/DISPATCHES/" + dispatchKey);
    }
}
