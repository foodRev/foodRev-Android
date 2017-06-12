package foodrev.org.foodrev.domain.models.dispatchModels;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.MappableObject;

/**
 * Created by dastechlabs on 3/18/17.
 */

public class DispatchDonor implements MappableObject {

    private String uid;
    private String name;
    private float carsOfFood;


    private String dispatchKey;

    private float allocatedCarsOfFood;
    private boolean isSelected = false;


    //TODO
    //address
    private double latitude;
    private double longitude;
    //poc
    //etc

    public int getIconId() {
        return iconId;
    }

    private int iconId = R.drawable.ic_donor;

    // constructor

    public DispatchDonor(String uid, String name, float carsOfFood, double latitude, double longitude) {
        this.uid = uid;
        this.name = name;
        this.carsOfFood = carsOfFood;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public DispatchDonor (String uid, String name, float carsOfFood, double latitude, double longitude, boolean isSelected) {
        this.uid = uid;
        this.name = name;
        this.carsOfFood = carsOfFood;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isSelected = isSelected;
    }

    public DispatchDonor(String name, float carsOfFood, double latitude, double longitude) {
        this.name = name;
        this.carsOfFood = carsOfFood;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("name",this.getName());
        result.put("carsOfFood",this.getCarsOfFood());
        result.put("latitude",this.getLatitude());
        result.put("longitude",this.getLongitude());
        return result;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCarsOfFood() {
        return carsOfFood;
    }

    public void setCarsOfFood(float carsOfFood) {
        this.carsOfFood = carsOfFood;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public float getAllocatedCarsOfFood() {
        return allocatedCarsOfFood;
    }

    public void setAllocatedCarsOfFood(float allocatedCarsOfFood) {
        this.allocatedCarsOfFood = allocatedCarsOfFood;
    }

    public String getDispatchKey() {
        return dispatchKey;
    }

    public void setDispatchKey(String dispatchKey) {
        this.dispatchKey = dispatchKey;
    }
}
