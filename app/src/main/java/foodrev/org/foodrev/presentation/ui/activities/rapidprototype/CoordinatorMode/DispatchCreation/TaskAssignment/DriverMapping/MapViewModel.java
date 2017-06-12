package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping;

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

import foodrev.org.foodrev.domain.models.dispatchModels.Builders.DispatchDonorBuilder;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchCommunity;
import foodrev.org.foodrev.domain.models.dispatchModels.Builders.DispatchCommunityBuilder;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDriver;
import foodrev.org.foodrev.domain.models.dispatchModels.Builders.DispatchDriverBuilder;

/**
 * Created by foodRev on 5/29/17.
 */

public class MapViewModel extends ViewModel {

    // necessary to figure out which dispatch
    private String dispatchKey;

    // Firebase References
    private DatabaseReference dispatchRootReference;

    // Live Data Donors
    private MutableLiveData<ArrayList<DispatchDonor>> dispatchDonors;
    private ArrayList<DispatchDonor> dispatchDonorsArray = new ArrayList<>();

    // Live Data Drivers
    private MutableLiveData<ArrayList<DispatchDriver>> dispatchDrivers;
    private ArrayList<DispatchDriver> dispatchDriversArray = new ArrayList<>();

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
    public LiveData<ArrayList<DispatchDriver>> getDrivers() {
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


    private void loadDispatchDrivers() {

        dispatchRootReference.child("DRIVERS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // reset
                dispatchDriversArray.clear();
                dispatchDrivers.setValue(dispatchDriversArray);

                // database related uid
                String driverUid;

                // donor human readable name
                String driverName;

                // food donation amount measured in "cars of food"
                float vehicleFoodCapacity;
                float currentAmountOfFoodCarrying;

                // gps
                double latitude;
                double longitude;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    driverUid = snapshot.getKey().toString();
                    driverName = snapshot.child("driverName").getValue().toString();
                    vehicleFoodCapacity = Float.parseFloat(snapshot.child("vehicleFoodCapacity").getValue().toString());
                    latitude = Double.parseDouble(snapshot.child("latitude").getValue().toString());
                    longitude = Double.parseDouble(snapshot.child("longitude").getValue().toString());

                    // add dispatch driver to arraylist
                    dispatchDriversArray.add(0, new DispatchDriverBuilder()
                            .setUid(driverUid)
                            .setName(driverName)
                            .setVehicleFoodCapacity(vehicleFoodCapacity)
//                            .setCurrentAmountOfFoodCarrying(currentAmountOfFoodCarrying)
                            .setLatitude(latitude)
                            .setLongitude(longitude)
                            .createDispatchDriverWithLatLng());
                }

                // update liveData
                dispatchDrivers.setValue(dispatchDriversArray);
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
    }

    public void setupDispatchRootReference() {
        dispatchRootReference = FirebaseDatabase.getInstance().getReference("/DISPATCHES/" + dispatchKey);
    }

}
