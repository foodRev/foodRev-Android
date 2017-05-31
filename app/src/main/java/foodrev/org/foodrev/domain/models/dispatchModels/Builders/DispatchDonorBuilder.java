package foodrev.org.foodrev.domain.models.dispatchModels.Builders;

import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;

public class DispatchDonorBuilder {
    private String donorUid;
    private String donorName;
    private float carsOfFood;
    private double latitude;
    private double longitude;
    private boolean isSelected;

    public DispatchDonorBuilder setDonorUid(String donorUid) {
        this.donorUid = donorUid;
        return this;
    }

    public DispatchDonorBuilder setDonorName(String donorName) {
        this.donorName = donorName;
        return this;
    }

    public DispatchDonorBuilder setCarsOfFood(float carsOfFood) {
        this.carsOfFood = carsOfFood;
        return this;
    }

    public DispatchDonorBuilder setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public DispatchDonorBuilder setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public DispatchDonorBuilder setIsSelected(boolean selected) {
        isSelected = selected;
        return this;
    }

    public DispatchDonor createDispatchDonor() {
        return new DispatchDonor(donorUid, donorName, carsOfFood, latitude, longitude, isSelected);
    }

    public DispatchDonor createDispatchDonorWithLatLng() {
        return new DispatchDonor(donorUid, donorName, carsOfFood, latitude, longitude);
    }
}