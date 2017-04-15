package foodrev.org.foodrev.domain.infos.models;

/**
 * Created by darver on 4/14/17.
 */

public class DriverTasks {
    public String sourceKey;
    public String destinationKey;
    public int progress;

    public DriverTasks() {

    }

    public DriverTasks(String sourceKey, String destinationKey, int progress) {
        this.sourceKey = sourceKey;
        this.destinationKey = destinationKey;
        this.progress = progress;
    }
}
