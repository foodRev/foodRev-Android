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
}
