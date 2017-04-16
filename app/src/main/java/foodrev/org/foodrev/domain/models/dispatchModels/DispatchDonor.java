package foodrev.org.foodrev.domain.models.dispatchModels;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dastechlabs on 3/18/17.
 */

public class DispatchDonor {

    private String donorUid;
    private String donorName;
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


    // constructor
    public DispatchDonor (String donorUid, String donorName, float carsOfFood, boolean isSelected) {
        this.donorUid = donorUid;
        this.donorName = donorName;
        this.carsOfFood = carsOfFood;
        this.isSelected = isSelected;
    }

    public DispatchDonor(String donorName, float carsOfFood, double latitude, double longitude) {
        this.donorName = donorName;
        this.carsOfFood = carsOfFood;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("donorName",this.getDonorName());
        result.put("carsOfFood",this.getCarsOfFood());
        result.put("latitude",this.getLatitude());
        result.put("longitude",this.getLongitude());
        return result;
    }

    // getters and setters
    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
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

    public String getDonorUid() {
        return donorUid;
    }

    public void setDonorUid(String donorUid) {
        this.donorUid = donorUid;
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
