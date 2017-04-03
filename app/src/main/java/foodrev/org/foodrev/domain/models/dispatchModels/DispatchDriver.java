package foodrev.org.foodrev.domain.models.dispatchModels;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dastechlabs on 3/18/17.
 */

public class DispatchDriver {

    private String driverUid;

    private ArrayList<String> driverInstructions;

    private String driverName;
    private int vehicleFoodCapacity;
    private boolean isSelected = false;

    public String getDriverUid() {
        return driverUid;
    }

    public void setDriverUid(String driverUid) {
        this.driverUid = driverUid;
    }

    // constructor
    public DispatchDriver(String driverName, int vehicleFoodCapacity) {
        this.driverName = driverName;
        this.vehicleFoodCapacity = vehicleFoodCapacity;
    }

    public DispatchDriver(String driverUid, String driverName, int vehicleFoodCapacity, boolean isSelected) {
        this.driverUid = driverUid;
        this.driverName = driverName;
        this.vehicleFoodCapacity = vehicleFoodCapacity;
        this.isSelected = isSelected;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("driverName",this.driverName);
        result.put("vehicleFoodCapacity",this.vehicleFoodCapacity);
        return result;
    }

    // getters and setters

    public ArrayList<String> getDriverInstructions() {
        return driverInstructions;
    }

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



}
