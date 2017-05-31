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
import java.util.List;

import foodrev.org.foodrev.domain.models.dispatchModels.Builders.DispatchDonorBuilder;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;

/**
 * Created by foodRev on 5/29/17.
 */

public class MapViewModel extends ViewModel {

    // necessary to figure out which dispatch
    private String dispatchKey;

    // Firebase References
    private DatabaseReference dispatchRootReference;

    // Live Data
    private MutableLiveData<ArrayList<DispatchDonor>> dispatchDonors;
    private ArrayList<DispatchDonor> dispatchDonorsArray = new ArrayList<>();

    // live monitoring of donors
    public LiveData<ArrayList<DispatchDonor>> getDonors() {
        if(dispatchDonors == null) {
            dispatchDonors = new MutableLiveData<>();
            loadDispatchDonors();
        }
        return dispatchDonors;
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
