package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by foodRev on 8/1/17.
 */

// TODO refactor DriverMapping LatLng Waypoints to use TaskItems
public class TaskItem {

    public enum TaskType {
        LOAD,
        UNLOAD
    }

    public enum LocationType {
        DONOR,
        COMMUNITY
    }

    // TaskType
    private TaskType taskType;

    //Location Data
    private LocationType locationType;
    private LatLng locationLatLng;
    private String locationName;

    //Amount to Load/Unload
    private Float amountToLoad;
    private Float amountToUnload;

    public TaskItem(TaskType taskType, LocationType locationType, LatLng locationLatLng,
                    String locationName, Float amountToLoad, Float amountToUnload) {
        this.taskType = taskType;
        this.locationType = locationType;
        this.locationLatLng = locationLatLng;
        this.locationName = locationName;
        this.amountToLoad = amountToLoad;
        this.amountToUnload = amountToUnload;
    }

    // toString
    @Override
    public String toString() {
        return "TaskItem{" +
                "taskType=" + taskType +
                ", locationType=" + locationType +
                ", locationLatLng=" + locationLatLng +
                ", locationName='" + locationName + '\'' +
                ", amountToLoad=" + amountToLoad +
                ", amountToUnload=" + amountToUnload +
                '}';
    }

    // getters and setters
    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public LatLng getLocationLatLng() {
        return locationLatLng;
    }

    public void setLocationLatLng(LatLng locationLatLng) {
        this.locationLatLng = locationLatLng;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Float getAmountToLoad() {
        return amountToLoad;
    }

    public void setAmountToLoad(Float amountToLoad) {
        this.amountToLoad = amountToLoad;
    }

    public Float getAmountToUnload() {
        return amountToUnload;
    }

    public void setAmountToUnload(Float amountToUnload) {
        this.amountToUnload = amountToUnload;
    }
}
