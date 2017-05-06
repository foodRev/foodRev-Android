package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.DriverMode;

/**
 * Created by george on 4/14/17.
 */

public class DriverTask {
    String taskType;
    String donationSource;
    String donationDestination;

    DriverTask(String taskType, String donationSource, String donationDestination) {
        this.taskType = taskType;
        this.donationSource = donationSource;
        this.donationDestination = donationDestination;
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
}
