package foodrev.org.foodrev.domain.models.dispatchModels;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.Interfaces.MappableObject;

/**
 * Created by dastechlabs on 3/18/17.
 */

public class DispatchDriver implements MappableObject {

    private String uid;
    private String name;


    private String dispatchKey;

    private ArrayList<String> driverInstructions;

    private float vehicleFoodCapacity;


    private float currentAmountOfFoodCarrying;
    private int totalTripsAssignedInDispatch;
    private int tripsAssignedForThisRoute;

    private boolean isSelected = false;

    private double latitude;
    private double longitude;

    public int getIconId() {
        return iconId;
    }

    private int iconId = R.drawable.driver_icon;

    // constructor
    public DispatchDriver(String name, float vehicleFoodCapacity) {
        this.name = name;
        this.vehicleFoodCapacity = vehicleFoodCapacity;
    }

    public DispatchDriver(String uid, String name, float vehicleFoodCapacity, double latitude, double longitude, boolean isSelected) {
        this.uid = uid;
        this.name = name;
        this.vehicleFoodCapacity = vehicleFoodCapacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isSelected = isSelected;
    }

    // constructor for driver map
    public DispatchDriver(String uid, String name, float currentAmountOfFoodCarrying, float vehicleFoodCapacity, double latitude, double longitude) {
        this.uid = uid;
        this.name = name;
        this.currentAmountOfFoodCarrying = currentAmountOfFoodCarrying;
        this.vehicleFoodCapacity = vehicleFoodCapacity;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("name",this.name);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getVehicleFoodCapacity() {
        return vehicleFoodCapacity;
    }

    public void setVehicleFoodCapacity(float vehicleFoodCapacity) {
        this.vehicleFoodCapacity = vehicleFoodCapacity;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public String getDispatchKey() {
        return dispatchKey;
    }

    public void setDispatchKey(String dispatchKey) {
        this.dispatchKey = dispatchKey;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getTotalTripsAssignedInDispatch() {
        return totalTripsAssignedInDispatch;
    }

    public int getTripsAssignedForThisRoute() {
        return tripsAssignedForThisRoute;
    }

    public void setTripsAssignedForThisRoute(int tripsAssignedForThisRoute) {
        this.tripsAssignedForThisRoute = tripsAssignedForThisRoute;
    }

    public void setTotalTripsAssignedInDispatch(int totalTripsAssignedInDispatch) {
        this.totalTripsAssignedInDispatch = totalTripsAssignedInDispatch;
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

    public float getCurrentAmountOfFoodCarrying() {
        return currentAmountOfFoodCarrying;
    }

    public void setCurrentAmountOfFoodCarrying(float currentAmountOfFoodCarrying) {
        this.currentAmountOfFoodCarrying = currentAmountOfFoodCarrying;
    }
}
