package foodrev.org.foodrev.domain.models;

import java.util.ArrayList;
import java.util.HashMap;

public class DriverBuilder {
    private ArrayList<String> vehiclesDriveable;
    private String currentVehicle;
    private int currentTrunkAreaSquareFeet;
    private int currentTrunkVolumeCubicFeet;
    private int currentUnitCapacity;
    private ArrayList<String> typesOfFoodAssigned;
    private int currentUnitsAssigned;
    private int currentPercentageCapacityAssigned;
    private ArrayList<String> typesOfFoodCarrying;
    private HashMap<String, Integer> currentUnitsCarrying;
    private int currentPercentageCapacityCarrying;
    private int cumulativeDipatches;
    private HashMap<String, Integer> totalFoodDeliveredToday;
    private HashMap<String, Integer> totalFoodDeliveredCumulative;
    private double milesTravelledToday;
    private double cumulativeMilesTravelled;
    private int currentStatus;
    private ArrayList<String> plannerSteps;
    private double currentLongitude;
    private double currentLatitude;
    private double startingLongitude;
    private double startingLatitude;
    private double targetFinalLongitude;
    private double targetFinalLatitude;

    public DriverBuilder setVehiclesDriveable(ArrayList<String> vehiclesDriveable) {
        this.vehiclesDriveable = vehiclesDriveable;
        return this;
    }

    public DriverBuilder setCurrentVehicle(String currentVehicle) {
        this.currentVehicle = currentVehicle;
        return this;
    }

    public DriverBuilder setCurrentTrunkAreaSquareFeet(int currentTrunkAreaSquareFeet) {
        this.currentTrunkAreaSquareFeet = currentTrunkAreaSquareFeet;
        return this;
    }

    public DriverBuilder setCurrentTrunkVolumeCubicFeet(int currentTrunkVolumeCubicFeet) {
        this.currentTrunkVolumeCubicFeet = currentTrunkVolumeCubicFeet;
        return this;
    }

    public DriverBuilder setCurrentUnitCapacity(int currentUnitCapacity) {
        this.currentUnitCapacity = currentUnitCapacity;
        return this;
    }

    public DriverBuilder setTypesOfFoodAssigned(ArrayList<String> typesOfFoodAssigned) {
        this.typesOfFoodAssigned = typesOfFoodAssigned;
        return this;
    }

    public DriverBuilder setCurrentUnitsAssigned(int currentUnitsAssigned) {
        this.currentUnitsAssigned = currentUnitsAssigned;
        return this;
    }

    public DriverBuilder setCurrentPercentageCapacityAssigned(int currentPercentageCapacityAssigned) {
        this.currentPercentageCapacityAssigned = currentPercentageCapacityAssigned;
        return this;
    }

    public DriverBuilder setTypesOfFoodCarrying(ArrayList<String> typesOfFoodCarrying) {
        this.typesOfFoodCarrying = typesOfFoodCarrying;
        return this;
    }

    public DriverBuilder setCurrentUnitsCarrying(HashMap<String, Integer> currentUnitsCarrying) {
        this.currentUnitsCarrying = currentUnitsCarrying;
        return this;
    }

    public DriverBuilder setCurrentPercentageCapacityCarrying(int currentPercentageCapacityCarrying) {
        this.currentPercentageCapacityCarrying = currentPercentageCapacityCarrying;
        return this;
    }

    public DriverBuilder setCumulativeDipatches(int cumulativeDipatches) {
        this.cumulativeDipatches = cumulativeDipatches;
        return this;
    }

    public DriverBuilder setTotalFoodDeliveredToday(HashMap<String, Integer> totalFoodDeliveredToday) {
        this.totalFoodDeliveredToday = totalFoodDeliveredToday;
        return this;
    }

    public DriverBuilder setTotalFoodDeliveredCumulative(HashMap<String, Integer> totalFoodDeliveredCumulative) {
        this.totalFoodDeliveredCumulative = totalFoodDeliveredCumulative;
        return this;
    }

    public DriverBuilder setMilesTravelledToday(double milesTravelledToday) {
        this.milesTravelledToday = milesTravelledToday;
        return this;
    }

    public DriverBuilder setCumulativeMilesTravelled(double cumulativeMilesTravelled) {
        this.cumulativeMilesTravelled = cumulativeMilesTravelled;
        return this;
    }

    public DriverBuilder setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
        return this;
    }

    public DriverBuilder setPlannerSteps(ArrayList<String> plannerSteps) {
        this.plannerSteps = plannerSteps;
        return this;
    }

    public DriverBuilder setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
        return this;
    }

    public DriverBuilder setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
        return this;
    }

    public DriverBuilder setStartingLongitude(double startingLongitude) {
        this.startingLongitude = startingLongitude;
        return this;
    }

    public DriverBuilder setStartingLatitude(double startingLatitude) {
        this.startingLatitude = startingLatitude;
        return this;
    }

    public DriverBuilder setTargetFinalLongitude(double targetFinalLongitude) {
        this.targetFinalLongitude = targetFinalLongitude;
        return this;
    }

    public DriverBuilder setTargetFinalLatitude(double targetFinalLatitude) {
        this.targetFinalLatitude = targetFinalLatitude;
        return this;
    }

    public Driver createDriver() {
        return new Driver(vehiclesDriveable, currentVehicle, currentTrunkAreaSquareFeet, currentTrunkVolumeCubicFeet, currentUnitCapacity, typesOfFoodAssigned, currentUnitsAssigned, currentPercentageCapacityAssigned, typesOfFoodCarrying, currentUnitsCarrying, currentPercentageCapacityCarrying, cumulativeDipatches, totalFoodDeliveredToday, totalFoodDeliveredCumulative, milesTravelledToday, cumulativeMilesTravelled, currentStatus, plannerSteps, currentLongitude, currentLatitude, startingLongitude, startingLatitude, targetFinalLongitude, targetFinalLatitude);
    }
}