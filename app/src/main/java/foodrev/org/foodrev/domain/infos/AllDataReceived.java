package foodrev.org.foodrev.domain.infos;

/**
 * Created by darver on 2/13/17.
 */

public class AllDataReceived {
    private boolean caresReceived;
    private boolean communityCentersReceived;
    private boolean donationCentersReceived;
    private boolean driversReceived;
    private AllDataReceivedListener mAllDataReceivedListener;

    public AllDataReceived(AllDataReceivedListener listener) {
        this.mAllDataReceivedListener = listener;
        this.caresReceived = false;
        this.communityCentersReceived = false;
        this.donationCentersReceived = false;
        this.driversReceived = false;
    }

    private void checkAllReceived() {
        if (caresReceived && communityCentersReceived && donationCentersReceived && driversReceived) {
            mAllDataReceivedListener.onAllDataReceived();
        }
    }

    public void receivedCares() {
        caresReceived = true;
        checkAllReceived();
    }

    public void receivedCommunityCenters() {
        communityCentersReceived = true;
        checkAllReceived();
    }

    public void receivedDonationCenters() {
        donationCentersReceived = true;
        checkAllReceived();
    }

    public void receivedDrivers() {
        driversReceived = true;
        checkAllReceived();
    }


}

