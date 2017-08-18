package foodrev.org.foodrev.domain.models.dispatchModels.driverInstructionModels;

/**
 * Created by dastechlabs on 8/18/17.
 */

public class DriverTaskListItem {

    private float amountToLoad;
    private float amountToUnload;
    private double latitude;
    private double longitude;
    private String locationName;
    private String locationType;
    private String taskType;

    public DriverTaskListItem(float amountToLoad, float amountToUnload, double latitude,
                              double longitude, String locationName, String locationType, String taskType) {

        this.amountToLoad = amountToLoad;
        this.amountToUnload = amountToUnload;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
        this.locationType = locationType;
        this.taskType = taskType;
    }

    //getters and setters
    public float getAmountToLoad() {
        return amountToLoad;
    }

    public void setAmountToLoad(float amountToLoad) {
        this.amountToLoad = amountToLoad;
    }

    public float getAmountToUnload() {
        return amountToUnload;
    }

    public void setAmountToUnload(float amountToUnload) {
        this.amountToUnload = amountToUnload;
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }


}
