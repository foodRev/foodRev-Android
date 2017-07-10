package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import foodrev.org.foodrev.domain.models.dispatchModels.Builders.DispatchDriverBuilder;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDriver;

/**
 * Created by dastechlabs on 7/9/17.
 */

@Singleton
public class DispatchDriverLiveData {

    // Live Data Drivers
    private HashMap<String,DispatchDriver> hashMapDispatchDrivers;
    private ArrayList<String> dispatchDriverIds = new ArrayList<>();

    // deliverable
    private MutableLiveData<HashMap<String,DispatchDriver>> dispatchDrivers;

    // for holding listeners
    private HashMap<String, ValueEventListener> driverListeners;

    // Firebase References
    private DatabaseReference rootReference;
    private DatabaseReference dispatchRootReference;

    @Inject
    public DispatchDriverLiveData(String dispatchKey) {
        hashMapDispatchDrivers = new HashMap<>();
        dispatchDriverIds = new ArrayList<>();
        driverListeners = new HashMap<>();

        // set references
        rootReference = FirebaseDatabase.getInstance().getReference("/");
        dispatchRootReference = FirebaseDatabase.getInstance().getReference("/DISPATCHES/" + dispatchKey);
    }


   public LiveData<HashMap<String,DispatchDriver>> getDispatchDrivers() {
        if(dispatchDrivers == null) {
            dispatchDrivers = new MutableLiveData<>();
            loadDispatchDrivers();
        }
        return dispatchDrivers;
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


    /**
     * Adds a new driver listener for each driver
     * @param driverHashId - the driver's hash id
     */
    private void addDriverListener(String driverHashId) {

        ValueEventListener valueEventListener = rootReference
                .child("DRIVERS")
                .child(driverHashId)
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (hashMapDispatchDrivers.containsKey(driverHashId)) {
                                    // update driver lat long and other stats
                                    updateDriverStats(dataSnapshot, driverHashId);
                                } else {
                                    // add driver to hashmap and init lat long
                                    initializeDriverStats(dataSnapshot, driverHashId);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );

        // save the value event listeners in a hashtable with the driverHashId as key
        driverListeners.put(driverHashId, valueEventListener);
    }


    private void updateDriverStats(DataSnapshot dataSnapshot, String driverHashId) {
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

        DispatchDriver dispatchDriver = hashMapDispatchDrivers.get(driverHashId);
        dispatchDriver.setName(driverName);
        dispatchDriver.setLatitude(latitude);
        dispatchDriver.setLongitude(longitude);

        // TODO: experiment if this is necessary, as we should be affecting via reference
        hashMapDispatchDrivers.put(driverHashId,dispatchDriver);
        dispatchDrivers.setValue(hashMapDispatchDrivers);
    }

    private void initializeDriverStats(DataSnapshot dataSnapshot, String driverHashId) {
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


        // add dispatch driver to hashmap
        hashMapDispatchDrivers.put(driverHashId, new DispatchDriverBuilder()
                .setUid(driverHashId)
                .setName(driverName)
                .setLatitude(latitude)
                .setLongitude(longitude)
                .createDispatchDriverWithLatLng());
        dispatchDrivers.setValue(hashMapDispatchDrivers);
    }

    private void removeDriverListener(String driverHashId) {

        // remove the listeners here
        rootReference.removeEventListener(driverListeners.get(driverHashId));
        driverListeners.remove(driverHashId);

    }

}
