package foodrev.org.foodrev.domain.models.dispatchModels;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dastechlabs on 3/18/17.
 */

public class DispatchDriver {

    public ArrayList<String> driverInstructions;

    private String driverName;
    private int vehicleFoodCapacity;
    private boolean isSelected = false;

    public ArrayList<String> getDriverInstructions() {
        return driverInstructions;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("driverName",this.driverName);
        result.put("vehicleFoodCapacity",this.vehicleFoodCapacity);
        return result;
    }

    // getters and setters
    public void setDriverInstructions(ArrayList<String> driverInstructions) {
        this.driverInstructions = driverInstructions;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getVehicleFoodCapacity() {
        return vehicleFoodCapacity;
    }

    public void setVehicleFoodCapacity(int vehicleFoodCapacity) {
        this.vehicleFoodCapacity = vehicleFoodCapacity;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public DispatchDriver (String driverName, int vehicleFoodCapacity, boolean isSelected) {
        this.driverName = driverName;
        this.vehicleFoodCapacity = vehicleFoodCapacity;
        this.isSelected = isSelected;
    }


}
