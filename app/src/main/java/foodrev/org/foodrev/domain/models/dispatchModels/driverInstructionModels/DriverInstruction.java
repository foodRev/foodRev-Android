package foodrev.org.foodrev.domain.models.dispatchModels.driverInstructionModels;

/**
 * Created by dastechlabs on 3/18/17.
 */

abstract class DriverInstruction {
    public enum Status {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED
    }

    public Status actionStatus;
    public String humanReadableDescription;

}
