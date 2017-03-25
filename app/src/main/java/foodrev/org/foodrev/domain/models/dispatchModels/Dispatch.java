package foodrev.org.foodrev.domain.models.dispatchModels;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static foodrev.org.foodrev.domain.models.dispatchModels.Dispatch.DispatchStatus.NEED_TO_PLAN;

/**
 * Created by dastechlabs on 3/18/17.
 */

public class Dispatch {

    public enum DispatchStatus {
        NEED_TO_PLAN,
        PLAN_PREPARED,
    };

    private String dispatchId = ""; // the firebase hash

    private String dispatchDate = "";

    private String dispatchStartTime = "";
    private String dispatchEndTime = "";

    ArrayList<DispatchDriver> dispatchDrivers;
    ArrayList<DispatchCommunity> dispatchCommunities;
    ArrayList<DispatchCoordinator> dispatchCoordinators;
    ArrayList<DispatchDonor> dispatchDonors;

    private DispatchStatus dispatchStatus = NEED_TO_PLAN;


    public Dispatch(String dispatchDate, String dispatchStartTime, DispatchStatus dispatchStatus) {
        this.dispatchDate = dispatchDate;
        this.dispatchStartTime = dispatchStartTime;
        this.dispatchStatus = dispatchStatus;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("dispatchDate","dispatchDate");
        result.put("dispatchStatus", NEED_TO_PLAN.toString());
        return result;
    }


    //getters and setters

    public String getDispatchStartTime() {
        return dispatchStartTime;
    }

    public void setDispatchStartTime(String dispatchStartTime) {
        this.dispatchStartTime = dispatchStartTime;
    }

    public String getDispatchEndTime() {
        return dispatchEndTime;
    }

    public void setDispatchEndTime(String dispatchEndTime) {
        this.dispatchEndTime = dispatchEndTime;
    }

    public String getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(String dispatchId) {
        this.dispatchId = dispatchId;
    }

    public String getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(String dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public DispatchStatus getDispatchStatus() {
        return dispatchStatus;
    }

    public void setDispatchStatus(DispatchStatus dispatchStatus) {
        this.dispatchStatus = dispatchStatus;
    }

    public ArrayList<DispatchDriver> getDispatchDrivers() {
        return dispatchDrivers;
    }

    public void setDispatchDrivers(ArrayList<DispatchDriver> dispatchDrivers) {
        this.dispatchDrivers = dispatchDrivers;
    }

    public ArrayList<DispatchCommunity> getDispatchCommunities() {
        return dispatchCommunities;
    }

    public void setDispatchCommunities(ArrayList<DispatchCommunity> dispatchCommunities) {
        this.dispatchCommunities = dispatchCommunities;
    }

    public ArrayList<DispatchCoordinator> getDispatchCoordinators() {
        return dispatchCoordinators;
    }

    public void setDispatchCoordinators(ArrayList<DispatchCoordinator> dispatchCoordinators) {
        this.dispatchCoordinators = dispatchCoordinators;
    }

    public ArrayList<DispatchDonor> getDispatchDonors() {
        return dispatchDonors;
    }

    public void setDispatchDonors(ArrayList<DispatchDonor> dispatchDonors) {
        this.dispatchDonors = dispatchDonors;
    }
}
