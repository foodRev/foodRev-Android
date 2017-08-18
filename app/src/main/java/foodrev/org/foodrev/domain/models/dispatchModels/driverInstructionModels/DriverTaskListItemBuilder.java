package foodrev.org.foodrev.domain.models.dispatchModels.driverInstructionModels;

public class DriverTaskListItemBuilder {
    private float amountToLoad = 0f;
    private float amountToUnload = 0f;
    private double latitude;
    private double longitude;
    private String locationName;
    private String locationType;
    private String taskType;

    public DriverTaskListItemBuilder setAmountToLoad(float amountToLoad) {
        this.amountToLoad = amountToLoad;
        return this;
    }

    public DriverTaskListItemBuilder setAmountToUnload(float amountToUnload) {
        this.amountToUnload = amountToUnload;
        return this;
    }

    public DriverTaskListItemBuilder setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public DriverTaskListItemBuilder setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public DriverTaskListItemBuilder setLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public DriverTaskListItemBuilder setLocationType(String locationType) {
        this.locationType = locationType;
        return this;
    }

    public DriverTaskListItemBuilder setTaskType(String taskType) {
        this.taskType = taskType;
        return this;
    }

    public DriverTaskListItem createDriverTaskListItem() {
        return new DriverTaskListItem(amountToLoad, amountToUnload, latitude, longitude, locationName, locationType, taskType);
    }
}