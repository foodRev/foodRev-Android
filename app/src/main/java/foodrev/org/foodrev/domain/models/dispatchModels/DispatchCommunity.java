package foodrev.org.foodrev.domain.models.dispatchModels;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dastechlabs on 3/18/17.
 */

public class DispatchCommunity {
    private String communityUid;


    private String dispatchkey;

    private String communityName;
    private float foodDonationCapacity;


    private float allocatedFood;
    private boolean isSelected = false;

    private double latitude;
    private double longitude;

    //TODO
    //address
    //poc
    //etc

    public String getCommunityUid() {
        return communityUid;
    }

    public void setCommunityUid(String communityUid) {
        this.communityUid = communityUid;
    }

    // constructor
    public DispatchCommunity(String communityUid, String communityName, float foodDonationCapacity, boolean isSelected) {
        this.communityUid = communityUid;
        this.communityName = communityName;
        this.foodDonationCapacity = foodDonationCapacity;
        this.isSelected = isSelected;
    }

    // add firebase entity constructor
    public DispatchCommunity(String communityName, float foodDonationCapacity, double latitude, double longitude) {
        this.communityName = communityName;
        this.foodDonationCapacity = foodDonationCapacity;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("communityName",this.getCommunityName());
        result.put("foodDonationCapacity",this.getFoodDonationCapacity());
        result.put("latitude",this.getLatitude());
        result.put("longitude",this.getLongitude());
        return result;
    }

    // getters and setters
    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
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
}
