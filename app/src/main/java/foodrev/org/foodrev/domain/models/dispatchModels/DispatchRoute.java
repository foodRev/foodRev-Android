package foodrev.org.foodrev.domain.models.dispatchModels;

/**
 * Created by dastechlabs on 5/27/17.
 */

public class DispatchRoute {

    // keys
    private String dispatchKey;
    private String donorCommunityKey;

    // names
    private String donorName;
    private String communityName;

    // driver assigment based on trunk capacity
    private float carsRequired;
    private float carsAssigned;

    public DispatchRoute(String dispatchKey, String donorCommunityKey, String donorName, String communityName, float carsRequired) {
        this.dispatchKey = dispatchKey;
        this.donorCommunityKey = donorCommunityKey;
        this.donorName = donorName;
        this.communityName = communityName;
        this.carsRequired = carsRequired;
    }

    public String getDispatchKey() {
        return dispatchKey;
    }

    public void setDispatchKey(String dispatchKey) {
        this.dispatchKey = dispatchKey;
    }

    public String getDonorCommunityKey() {
        return donorCommunityKey;
    }

    public void setDonorCommunityKey(String donorCommunityKey) {
        this.donorCommunityKey = donorCommunityKey;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public float getCarsRequired() {
        return carsRequired;
    }

    public void setCarsRequired(float carsRequired) {
        this.carsRequired = carsRequired;
    }

    public float getCarsAssigned() {
        return carsAssigned;
    }

    public void setCarsAssigned(float carsAssigned) {
        this.carsAssigned = carsAssigned;
    }
}
