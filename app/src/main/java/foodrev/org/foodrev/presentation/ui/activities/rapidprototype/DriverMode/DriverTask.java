package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.DriverMode;

import com.google.firebase.database.Exclude;

/**
 * Created by george on 4/14/17.
 */
public class DriverTask {
    String taskType;
    String donationSource;
    String donationDestination;
    String progress;
    String key;

    public DriverTask(){

    }

    public DriverTask(DriverTask driverTask) {

        super();
        this.taskType = driverTask.taskType;
        this.donationSource = driverTask.donationSource;
        this.donationDestination = driverTask.donationDestination;
        this.progress = driverTask.progress;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    DriverTask(String key, String taskType, String donationSource, String donationDestination, String progress) {
        this.key = key;
        this.taskType = taskType;
        this.donationSource = donationSource;
        this.donationDestination = donationDestination;
        this.progress = progress;

    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getDonationSource() {
        return donationSource;
    }

    public void setDonationSource(String donationSource) {
        this.donationSource = donationSource;
    }

    public String getDonationDestination() {
        return donationDestination;
    }

    public void setDonationDestination(String donationDestination) {
        this.donationDestination = donationDestination;
    }
    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public void setValues(DriverTask updateDriverTask){
        this.taskType = updateDriverTask.taskType;
        this.donationSource = updateDriverTask.donationSource;
        this.donationDestination = updateDriverTask.donationDestination;
        this.progress = updateDriverTask.progress;


    }
}
