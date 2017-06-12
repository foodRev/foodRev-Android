package foodrev.org.foodrev.domain.models.dispatchModels.Builders;

import foodrev.org.foodrev.domain.models.dispatchModels.DispatchCommunity;

public class DispatchCommunityBuilder {
    private String uid;
    private String name;
    private float foodDonationCapacity;
    private boolean isSelected;
    private double latitude;
    private double longitude;

    public DispatchCommunityBuilder setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public DispatchCommunityBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public DispatchCommunityBuilder setFoodDonationCapacity(float foodDonationCapacity) {
        this.foodDonationCapacity = foodDonationCapacity;
        return this;
    }

    public DispatchCommunityBuilder setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
        return this;
    }

    public DispatchCommunityBuilder setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public DispatchCommunityBuilder setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public DispatchCommunity createDispatchCommunity() {
        return new DispatchCommunity(uid, name, foodDonationCapacity, latitude, longitude, isSelected);
    }

    public DispatchCommunity createDispatchCommunityWithLatLng() {
        return new DispatchCommunity(uid, name, foodDonationCapacity, latitude, longitude);
    }
}