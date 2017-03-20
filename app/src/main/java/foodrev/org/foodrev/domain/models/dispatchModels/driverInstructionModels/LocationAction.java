package foodrev.org.foodrev.domain.models.dispatchModels.driverInstructionModels;

/**
 * Created by dastechlabs on 3/18/17.
 */

public class LocationAction extends DriverInstruction{

    public LocationAction(String humanReadableDescription, Status actionStatus) {
        this.humanReadableDescription = humanReadableDescription;
        this.actionStatus = actionStatus;
    }

}
