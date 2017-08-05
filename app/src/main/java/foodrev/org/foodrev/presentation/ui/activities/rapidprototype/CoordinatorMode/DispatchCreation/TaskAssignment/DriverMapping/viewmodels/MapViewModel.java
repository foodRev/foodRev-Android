package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

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
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata.DispatchCommunityLiveData;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata.DispatchDonorLiveData;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata.DispatchDriverLiveData;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata.DriverTaskList;

/**
 * Created by Gregory Kielian on 5/29/17.
 */

public class MapViewModel extends ViewModel {

    // necessary to figure out which dispatch
    private String dispatchKey;

    // current view hash
    private String currentDriverHash = null;
    private MutableLiveData<String> currentLiveDriverHash;

    // live monitoring of donors
    public MutableLiveData<String> getCurrentDriverHash() {
        if(currentLiveDriverHash == null) {
            currentLiveDriverHash = new MutableLiveData<>();
            currentLiveDriverHash.setValue(currentDriverHash);
        }
        return currentLiveDriverHash;
    }

    public void setCurrentDriverHash(String driverHash) {
        currentDriverHash = driverHash;
        currentLiveDriverHash.setValue(currentDriverHash);
    }

    // Firebase References
    private DatabaseReference dispatchRootReference;

    // Live Data Donors
   // TODO refactor to:
    private DispatchDonorLiveData dispatchDonorLiveData;

    // TODO Delete
    private MutableLiveData<ArrayList<DispatchDonor>> dispatchDonors;
    private ArrayList<DispatchDonor> dispatchDonorsArray = new ArrayList<>();

    // Live Data Drivers
    private DispatchDriverLiveData dispatchDriverLiveData;

    // Live Data Communities
    // TODO refactor to
    private DispatchCommunityLiveData dispatchCommunityLiveData;

    // TODO Delete
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
        return dispatchDriverLiveData.getDispatchDrivers();
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
        setupViewModelDeps();
        setupViewModelReferences();
    }

    public void setupViewModelReferences() {
        dispatchRootReference = FirebaseDatabase.getInstance().getReference("/DISPATCHES/" + dispatchKey);
    }

    public void setupViewModelDeps() {
        dispatchDriverLiveData = new DispatchDriverLiveData(dispatchKey);
        dispatchDonorLiveData = new DispatchDonorLiveData(dispatchKey, "DONORS");
        dispatchCommunityLiveData = new DispatchCommunityLiveData(dispatchKey, "COMMUNITIES");
    }

    public void updateDriverTaskList(DriverTaskList driverTaskList) {
        dispatchRootReference.child("DRIVERS")
                .child(driverTaskList.getDriverHashId())
                .child("TASK_LIST")
                .setValue(driverTaskList.getTaskItemList());
    }
}
