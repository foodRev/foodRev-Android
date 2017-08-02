package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata;

import com.google.android.gms.maps.model.LatLng;

public class TaskItemBuilder {
    private TaskItem.TaskType taskType;
    private TaskItem.LocationType locationType;
    private LatLng locationLatLng;
    private String locationName;
    private Float amountToLoad = 0f;
    private Float amountToUnload = 0f;

    public TaskItemBuilder setTaskType(TaskItem.TaskType taskType) {
        this.taskType = taskType;
        return this;
    }

    public TaskItemBuilder setLocationType(TaskItem.LocationType locationType) {
        this.locationType = locationType;
        return this;
    }

    public TaskItemBuilder setLocationLatLng(LatLng locationLatLng) {
        this.locationLatLng = locationLatLng;
        return this;
    }

    public TaskItemBuilder setLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public TaskItemBuilder setAmountToLoad(Float amountToLoad) {
        this.amountToLoad = amountToLoad;
        return this;
    }

    public TaskItemBuilder setAmountToUnload(Float amountToUnload) {
        this.amountToUnload = amountToUnload;
        return this;
    }

    public TaskItem createTaskItem() {
        return new TaskItem(taskType, locationType, locationLatLng, locationName, amountToLoad, amountToUnload);
    }
}