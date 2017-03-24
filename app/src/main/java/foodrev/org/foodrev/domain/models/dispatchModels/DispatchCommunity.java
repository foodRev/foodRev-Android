package foodrev.org.foodrev.domain.models.dispatchModels;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dastechlabs on 3/18/17.
 */

public class DispatchCommunity {
    private String communityName;
    private int foodDonationCapacity;
    private boolean isSelected = false;
    //TODO
    //address
    //gps
    //poc
    //etc

    // constructor
    public DispatchCommunity(String communityName, int foodDonationCapacity, boolean isSelected) {
        this.communityName = communityName;
        this.foodDonationCapacity = foodDonationCapacity;
        this.isSelected = isSelected;
    }

    // getters and setters
    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public int getFoodDonationCapacity() {
        return foodDonationCapacity;
    }

    public void setFoodDonationCapacity(int foodDonationCapacity) {
        this.foodDonationCapacity = foodDonationCapacity;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
