package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata;

import java.util.List;

/**
 * Created by dastechlabs on 8/1/17.
 */

public class DriverTaskList {

    //Dispatch Id
    private String dispatchId;

    //Driver Hash Id
    private String driverHashId;

    //Task Item List
    private List<TaskItem> TaskItemList;

    public DriverTaskList(String dispatchId, String driverHashId,
                          List<TaskItem> taskItemList) {
        this.dispatchId = dispatchId;
        this.driverHashId = driverHashId;
        TaskItemList = taskItemList;
    }

    // toString
    @Override
    public String toString() {
        return "DriverTaskList{" +
                "dispatchId='" + dispatchId + '\'' +
                ", driverHashId='" + driverHashId + '\'' +
                ", TaskItemList=" + TaskItemList +
                '}';
    }

    // getters and setters
    public String getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(String dispatchId) {
        this.dispatchId = dispatchId;
    }

    public String getDriverHashId() {
        return driverHashId;
    }

    public void setDriverHashId(String driverHashId) {
        this.driverHashId = driverHashId;
    }

    public List<TaskItem> getTaskItemList() {
        return TaskItemList;
    }

    public void setTaskItemList(List<TaskItem> taskItemList) {
        TaskItemList = taskItemList;
    }
}
