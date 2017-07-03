package foodrev.org.foodrev.domain.models.dispatchModels;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.Interfaces.MappableObject;

/**
 * Created by dastechlabs on 3/18/17.
 */

public class DispatchCommunity implements MappableObject {
    private String uid;
    private String name;

    private String dispatchkey;

    private float foodDonationCapacity;

    private float allocatedFood;

    private float allocatedFromListedDonor;

    private boolean isSelected = false;

    private double latitude;
    private double longitude;

    public int getIconId() {
        return iconId;
    }

    private int iconId = R.drawable.ic_community_destination;
    //TODO
    //address
    //poc
    //etc

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    // default constructor
    public DispatchCommunity() {}

    // constructor
    public DispatchCommunity(String uid, String name, float foodDonationCapacity, boolean isSelected) {
        this.uid = uid;
        this.name = name;
        this.foodDonationCapacity = foodDonationCapacity;
        this.isSelected = isSelected;
    }

    public DispatchCommunity(String uid, String name, float foodDonationCapacity, double latitude, double longitude, boolean isSelected) {
        this.uid = uid;
        this.name = name;
        this.foodDonationCapacity = foodDonationCapacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isSelected = isSelected;
    }


    // add firebase entity constructor
    public DispatchCommunity(String name, float foodDonationCapacity, double latitude, double longitude) {
        this.name = name;
        this.foodDonationCapacity = foodDonationCapacity;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // add constructor for map view
    public DispatchCommunity(String uid, String name, float foodDonationCapacity, double latitude, double longitude) {
        this.uid = uid;
        this.name = name;
        this.foodDonationCapacity = foodDonationCapacity;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("name",this.getName());
        result.put("foodDonationCapacity",this.getFoodDonationCapacity());
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

    public float getFoodDonationCapacity() {
        return foodDonationCapacity;
    }

    public void setFoodDonationCapacity(float foodDonationCapacity) {
        this.foodDonationCapacity = foodDonationCapacity;
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

    public String getDispatchkey() {
        return dispatchkey;
    }

    public void setDispatchkey(String dispatchkey) {
        this.dispatchkey = dispatchkey;
    }

    public float getAllocatedFood() {
        return allocatedFood;
    }

    public void setAllocatedFood(float allocatedFood) {
        this.allocatedFood = allocatedFood;
    }

    public float getAllocatedFromListedDonor() {
        return allocatedFromListedDonor;
    }

    public void setAllocatedFromListedDonor(float allocatedFromListedDonor) {
        this.allocatedFromListedDonor = allocatedFromListedDonor;
    }
}
