package foodrev.org.foodrev.domain.models.dispatchModels;

/**
 * Created by Gregory Kielian on 4/12/17.
 */

public class DonorToCommunityPair {

    // purpose of this class is to hold donor and community pairs
    // used to populate a
    // which then are assigned to drivers

    // this will need to be added to the schema
    // implementation
    // child of the DispatchDonor, communityDelegation
    // communityDelegation will have a hash of communities
    //
    private DispatchDonor dispatchDonor;
    private DispatchCommunity dispatchCommunity;
    private float trunksOfFood;
    private String dispatchKey;

    public DonorToCommunityPair(DispatchDonor dispatchDonor, DispatchCommunity dispatchCommunity, float trunksOfFood) {
        this.dispatchDonor = dispatchDonor;
        this.dispatchCommunity = dispatchCommunity;
        this.trunksOfFood = trunksOfFood;
    }

    // at first these pairs will only have Donors Communities,
    // and knowledge of total unallocated food of the Donor
    // then these will populate a list where one can assign drivers
    // to these specifying the amount of food per driver
    // the default should be one trunkful
    // TODO refactor food amounts to be floats


    public DispatchDonor getDispatchDonor() {
        return dispatchDonor;
    }

    public void setDispatchDonor(DispatchDonor dispatchDonor) {
        this.dispatchDonor = dispatchDonor;
    }

    public DispatchCommunity getDispatchCommunity() {
        return dispatchCommunity;
    }

    public void setDispatchCommunity(DispatchCommunity dispatchCommunity) {
        this.dispatchCommunity = dispatchCommunity;
    }

    public float getTrunksOfFood() {
        return trunksOfFood;
    }

    public void setTrunksOfFood(float trunksOfFood) {
        this.trunksOfFood = trunksOfFood;
    }

    public String getDispatchKey() {
        return dispatchKey;
    }

    public void setDispatchKey(String dispatchKey) {
        this.dispatchKey = dispatchKey;
    }
}