package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata;

import android.arch.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;

import java.util.AbstractMap;
import java.util.HashMap;

import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.Interfaces.MappableObject;

/**
 * Created by Gregory Kielian on 7/9/17.
 */

public class DispatchDonorLiveData extends AbstractMapObject {

    public DispatchDonorLiveData(String dispatchKey, String firebasePath) {
        super(dispatchKey, firebasePath);
    }

    @Override
    public LiveData<HashMap<String, MappableObject>> getMappableObjects() {
        return null;
    }

    @Override
    public void updateMapObjectStats(DataSnapshot dataSnapshot, String mapObjectHashId) {

    }

    @Override
    public void initializeMapObjectStats(DataSnapshot dataSnapshot, String mapObjectHashId) {

    }

}
