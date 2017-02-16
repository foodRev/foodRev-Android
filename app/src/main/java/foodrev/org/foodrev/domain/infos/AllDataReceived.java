package foodrev.org.foodrev.domain.infos;

import foodrev.org.foodrev.domain.interactors.GetFirebaseInfoInteractor;

/**
 * Created by darver on 2/13/17.
 */

public class AllDataReceived {
    private boolean mCaresReceived;
    private boolean mCommunityCentersReceived;
    private boolean mDonationCentersReceived;
    private boolean mDriversReceived;
    private GetFirebaseInfoInteractor.Callback mCallback;

    public AllDataReceived(GetFirebaseInfoInteractor.Callback callback) {
        mCallback = callback;
        mCaresReceived = false;
        mCommunityCentersReceived = false;
        mDonationCentersReceived = false;
        mDriversReceived = false;
    }

    private void checkAllReceived() {
        if (mCaresReceived && mCommunityCentersReceived && mDonationCentersReceived && mDriversReceived) {
            mCallback.onDataReceived();
        }
    }

    public void receivedCares() {
        mCaresReceived = true;
        checkAllReceived();
    }

    public void receivedCommunityCenters() {
        mCommunityCentersReceived = true;
        checkAllReceived();
    }

    public void receivedDonationCenters() {
        mDonationCentersReceived = true;
        checkAllReceived();
    }

    public void receivedDrivers() {
        mDriversReceived = true;
        checkAllReceived();
    }


}

