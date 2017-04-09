package foodrev.org.foodrev.domain.models.dispatchModels.driverInstructionModels;

import foodrev.org.foodrev.domain.models.dispatchModels.driverInstructionModels.DriverInstruction;

/**
 * Created by dastechlabs on 3/18/17.
 */

public class TravelAction extends DriverInstruction {

    public TravelAction(String humanReadableDescription, Status actionStatus) {
        this.humanReadableDescription = humanReadableDescription;
        this.actionStatus = actionStatus;
    }

}
