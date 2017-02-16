package foodrev.org.foodrev.domain.models;

import android.location.Location;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gregory Kielian on 2/15/17.
 */

public class Driver extends PointOfContact{

    // list of vehicles
    ArrayList<String> vehiclesDriveable;

    // current vehicle intending
    String currentVehicle;

    // current vehicle capacity
    int currentTrunkAreaSquareFeet;
    int currentTrunkVolumeCubicFeet;
    int currentUnitCapacity;

    // assignment
    ArrayList<String> typesOfFoodAssigned;
    int currentUnitsAssigned;
    int currentPercentageCapacityAssigned;

    // currently carrying
    ArrayList<String> typesOfFoodCarrying;
    HashMap<String,Integer> currentUnitsCarrying;
    int currentPercentageCapacityCarrying;

    // total dispatches -- for stats page
    int cumulativeDipatches;

    // food delivered total - item and qnty
    HashMap<String, Integer> totalFoodDeliveredToday;
    HashMap<String, Integer> totalFoodDeliveredCumulative;

    // miles traveled -- incentive for tax purposes
    double milesTravelledToday;
    double cumulativeMilesTravelled;

    // Current Status general
    static final int Driving = 0;
    static final int Loading = 1;
    static final int Unloading = 2;

    int currentStatus;

    // Current Status Granular - pulling these from planner steps
    ArrayList<String> plannerSteps;

    // current GPS Location - necessary for the donor, coordinator, and community center
    double currentLongitude;
    double currentLatitude;

    // presumed starting GPS Location - will assist with the planner to increase driver convenience
    double startingLongitude;
    double startingLatitude;

    // target final GPS Location - general location they are travelling to after the donation
    double targetFinalLongitude;
    double targetFinalLatitude;

    // constructor

    public Driver(ArrayList<String> vehiclesDriveable, String currentVehicle, int currentTrunkAreaSquareFeet, int currentTrunkVolumeCubicFeet, int currentUnitCapacity, ArrayList<String> typesOfFoodAssigned, int currentUnitsAssigned, int currentPercentageCapacityAssigned, ArrayList<String> typesOfFoodCarrying, HashMap<String, Integer> currentUnitsCarrying, int currentPercentageCapacityCarrying, int cumulativeDipatches, HashMap<String, Integer> totalFoodDeliveredToday, HashMap<String, Integer> totalFoodDeliveredCumulative, double milesTravelledToday, double cumulativeMilesTravelled, int currentStatus, ArrayList<String> plannerSteps, double currentLongitude, double currentLatitude, double startingLongitude, double startingLatitude, double targetFinalLongitude, double targetFinalLatitude) {
        this.vehiclesDriveable = vehiclesDriveable;
        this.currentVehicle = currentVehicle;
        this.currentTrunkAreaSquareFeet = currentTrunkAreaSquareFeet;
        this.currentTrunkVolumeCubicFeet = currentTrunkVolumeCubicFeet;
        this.currentUnitCapacity = currentUnitCapacity;
        this.typesOfFoodAssigned = typesOfFoodAssigned;
        this.currentUnitsAssigned = currentUnitsAssigned;
        this.currentPercentageCapacityAssigned = currentPercentageCapacityAssigned;
        this.typesOfFoodCarrying = typesOfFoodCarrying;
        this.currentUnitsCarrying = currentUnitsCarrying;
        this.currentPercentageCapacityCarrying = currentPercentageCapacityCarrying;
        this.cumulativeDipatches = cumulativeDipatches;
        this.totalFoodDeliveredToday = totalFoodDeliveredToday;
        this.totalFoodDeliveredCumulative = totalFoodDeliveredCumulative;
        this.milesTravelledToday = milesTravelledToday;
        this.cumulativeMilesTravelled = cumulativeMilesTravelled;
        this.currentStatus = currentStatus;
        this.plannerSteps = plannerSteps;
        this.currentLongitude = currentLongitude;
        this.currentLatitude = currentLatitude;
        this.startingLongitude = startingLongitude;
        this.startingLatitude = startingLatitude;
        this.targetFinalLongitude = targetFinalLongitude;
        this.targetFinalLatitude = targetFinalLatitude;
    }

    // getters and setters


    public int getCumulativeDipatches() {
        return cumulativeDipatches;
    }

    public void setCumulativeDipatches(int cumulativeDipatches) {
        this.cumulativeDipatches = cumulativeDipatches;
    }

    public HashMap<String, Integer> getTotalFoodDeliveredToday() {
        return totalFoodDeliveredToday;
    }

    public void setTotalFoodDeliveredToday(HashMap<String, Integer> totalFoodDeliveredToday) {
        this.totalFoodDeliveredToday = totalFoodDeliveredToday;
    }

    public HashMap<String, Integer> getTotalFoodDeliveredCumulative() {
        return totalFoodDeliveredCumulative;
    }

    public void setTotalFoodDeliveredCumulative(HashMap<String, Integer> totalFoodDeliveredCumulative) {
        this.totalFoodDeliveredCumulative = totalFoodDeliveredCumulative;
    }

    public ArrayList<String> getVehiclesDriveable() {
        return vehiclesDriveable;
    }

    public void setVehiclesDriveable(ArrayList<String> vehiclesDriveable) {
        this.vehiclesDriveable = vehiclesDriveable;
    }

    public String getCurrentVehicle() {
        return currentVehicle;
    }

    public void setCurrentVehicle(String currentVehicle) {
        this.currentVehicle = currentVehicle;
    }

    public int getCurrentTrunkAreaSquareFeet() {
        return currentTrunkAreaSquareFeet;
    }

    public void setCurrentTrunkAreaSquareFeet(int currentTrunkAreaSquareFeet) {
        this.currentTrunkAreaSquareFeet = currentTrunkAreaSquareFeet;
    }

    public int getCurrentTrunkVolumeCubicFeet() {
        return currentTrunkVolumeCubicFeet;
    }

    public void setCurrentTrunkVolumeCubicFeet(int currentTrunkVolumeCubicFeet) {
        this.currentTrunkVolumeCubicFeet = currentTrunkVolumeCubicFeet;
    }

    public int getCurrentUnitCapacity() {
        return currentUnitCapacity;
    }

    public void setCurrentUnitCapacity(int currentUnitCapacity) {
        this.currentUnitCapacity = currentUnitCapacity;
    }

    public ArrayList<String> getTypesOfFoodAssigned() {
        return typesOfFoodAssigned;
    }

    public void setTypesOfFoodAssigned(ArrayList<String> typesOfFoodAssigned) {
        this.typesOfFoodAssigned = typesOfFoodAssigned;
    }

    public int getCurrentUnitsAssigned() {
        return currentUnitsAssigned;
    }

    public void setCurrentUnitsAssigned(int currentUnitsAssigned) {
        this.currentUnitsAssigned = currentUnitsAssigned;
    }

    public int getCurrentPercentageCapacityAssigned() {
        return currentPercentageCapacityAssigned;
    }

    public void setCurrentPercentageCapacityAssigned(int currentPercentageCapacityAssigned) {
        this.currentPercentageCapacityAssigned = currentPercentageCapacityAssigned;
    }

    public ArrayList<String> getTypesOfFoodCarrying() {
        return typesOfFoodCarrying;
    }

    public void setTypesOfFoodCarrying(ArrayList<String> typesOfFoodCarrying) {
        this.typesOfFoodCarrying = typesOfFoodCarrying;
    }

    public HashMap<String, Integer> getCurrentUnitsCarrying() {
        return currentUnitsCarrying;
    }

    public void setCurrentUnitsCarrying(HashMap<String, Integer> currentUnitsCarrying) {
        this.currentUnitsCarrying = currentUnitsCarrying;
    }

    public int getCurrentPercentageCapacityCarrying() {
        return currentPercentageCapacityCarrying;
    }

    public void setCurrentPercentageCapacityCarrying(int currentPercentageCapacityCarrying) {
        this.currentPercentageCapacityCarrying = currentPercentageCapacityCarrying;
    }

    public double getMilesTravelledToday() {
        return milesTravelledToday;
    }

    public void setMilesTravelledToday(double milesTravelledToday) {
        this.milesTravelledToday = milesTravelledToday;
    }

    public double getCumulativeMilesTravelled() {
        return cumulativeMilesTravelled;
    }

    public void setCumulativeMilesTravelled(double cumulativeMilesTravelled) {
        this.cumulativeMilesTravelled = cumulativeMilesTravelled;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }

    public ArrayList<String> getPlannerSteps() {
        return plannerSteps;
    }

    public void setPlannerSteps(ArrayList<String> plannerSteps) {
        this.plannerSteps = plannerSteps;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public double getStartingLongitude() {
        return startingLongitude;
    }

    public void setStartingLongitude(double startingLongitude) {
        this.startingLongitude = startingLongitude;
    }

    public double getStartingLatitude() {
        return startingLatitude;
    }

    public void setStartingLatitude(double startingLatitude) {
        this.startingLatitude = startingLatitude;
    }

    public double getTargetFinalLongitude() {
        return targetFinalLongitude;
    }

    public void setTargetFinalLongitude(double targetFinalLongitude) {
        this.targetFinalLongitude = targetFinalLongitude;
    }

    public double getTargetFinalLatitude() {
        return targetFinalLatitude;
    }

    public void setTargetFinalLatitude(double targetFinalLatitude) {
        this.targetFinalLatitude = targetFinalLatitude;
    }
}
