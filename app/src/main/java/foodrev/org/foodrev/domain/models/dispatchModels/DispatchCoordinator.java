package foodrev.org.foodrev.domain.models.dispatchModels;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dastechlabs on 3/18/17.
 */

public class DispatchCoordinator {
    private String coordinatorUid;
    private String coordinatorOauth;
    private String coordinatorName;

    public String getCoordinatorUid() {
        return coordinatorUid;
    }

    public void setCoordinatorUid(String coordinatorUid) {
        this.coordinatorUid = coordinatorUid;
    }

    // constructor
    public DispatchCoordinator(String coordinatorUid, String coordinatorName, String coordinatorOauth) {
        this.coordinatorUid = coordinatorUid;
        this.coordinatorName = coordinatorName;
        this.coordinatorOauth = coordinatorOauth;
    }

    // add firebase entity constructor
    public DispatchCoordinator(String coordinatorName, String coordinatorOauth) {
        this.coordinatorUid = coordinatorUid;
        this.coordinatorName = coordinatorName;
        this.coordinatorOauth = coordinatorOauth;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("coordinatorName",this.coordinatorName);
        result.put("coordinatorOauth",this.coordinatorOauth);
        return result;
    }
    public String getCoordinatorOauth() {
        return coordinatorOauth;
    }

    public void setCoordinatorOauth(String coordinatorOauth) {
        this.coordinatorOauth = coordinatorOauth;
    }

    public String getCoordinatorName() {
        return coordinatorName;
    }

    public void setCoordinatorName(String coordinatorName) {
        this.coordinatorName = coordinatorName;
    }


}
