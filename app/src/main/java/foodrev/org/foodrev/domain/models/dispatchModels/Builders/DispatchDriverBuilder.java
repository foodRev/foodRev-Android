package foodrev.org.foodrev.domain.models.dispatchModels.Builders;

import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDriver;

public class DispatchDriverBuilder {
    private String name;
    private String uid;
    private float vehicleFoodCapacity;
    private float currentAmountOfFoodCarrying;
    private boolean isSelected;
    private double latitude;
    private double longitude;

    public DispatchDriverBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public DispatchDriverBuilder setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public DispatchDriverBuilder setVehicleFoodCapacity(float vehicleFoodCapacity) {
        this.vehicleFoodCapacity = vehicleFoodCapacity;
        return this;
    }

    public DispatchDriverBuilder setCurrentAmountOfFoodCarrying(float currentAmountOfFoodCarrying) {
        this.currentAmountOfFoodCarrying = currentAmountOfFoodCarrying;
        return this;
    }

    public DispatchDriverBuilder setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
        return this;
    }

    public DispatchDriverBuilder setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public DispatchDriverBuilder setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public DispatchDriver createDispatchDriver() {
        return new DispatchDriver(uid, name, vehicleFoodCapacity, isSelected);
    }

    public DispatchDriver createDispatchDriverWithLatLng() {
        return new DispatchDriver(uid, name, vehicleFoodCapacity, currentAmountOfFoodCarrying, latitude, longitude);
    }
}