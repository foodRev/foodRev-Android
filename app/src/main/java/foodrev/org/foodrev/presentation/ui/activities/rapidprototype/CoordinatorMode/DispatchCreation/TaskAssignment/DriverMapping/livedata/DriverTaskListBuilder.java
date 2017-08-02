package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata;

import java.util.List;

public class DriverTaskListBuilder {
    private String dispatchId;
    private String driverHashId;
    private List<TaskItem> taskItemList;

    public DriverTaskListBuilder setDispatchId(String dispatchId) {
        this.dispatchId = dispatchId;
        return this;
    }

    public DriverTaskListBuilder setDriverHashId(String driverHashId) {
        this.driverHashId = driverHashId;
        return this;
    }

    public DriverTaskListBuilder setTaskItemList(List<TaskItem> taskItemList) {
        this.taskItemList = taskItemList;
        return this;
    }

    public DriverTaskList createDriverTaskList() {
        return new DriverTaskList(dispatchId, driverHashId, taskItemList);
    }
}