package foodrev.org.foodrev.domain.models.dispatchModels;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dastechlabs on 3/18/17.
 */

public class DispatchDonor {
    private String donorName;
    private int carsOfFood;
    private boolean isSelected = false;
    //TODO
    //address
    //gps
    //poc
    //etc


    public DispatchDonor (String donorName, int carsOfFood, boolean isSelected) {
        this.donorName = donorName;
        this.carsOfFood = carsOfFood;
        this.isSelected = isSelected;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("donorName",this.donorName);
        result.put("donorCarsOfFood",this.carsOfFood);
        return result;
    }

    // getters and setters
    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public int getCarsOfFood() {
        return carsOfFood;
    }

    public void setCarsOfFood(int carsOfFood) {
        this.carsOfFood = carsOfFood;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
