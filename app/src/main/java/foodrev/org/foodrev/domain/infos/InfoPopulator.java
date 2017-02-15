package foodrev.org.foodrev.domain.infos;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import foodrev.org.foodrev.domain.interactors.GetFirebaseInfoInteractor;

/**
 * Created by darver on 2/9/17.
 */

public class InfoPopulator {

    private CareInfo mCareInfo;
    private CommunityCenterInfo mCommunityCenterInfo;
    private DonationCenterInfo mDonationCenterInfo;
    private DriverInfo mDriverInfo;
    private AllDataReceived mAllDataReceived;

    public InfoPopulator(FirebaseDatabase db, AllDataReceivedListener listener) {
        mAllDataReceived = new AllDataReceived(listener);
        mDriverInfo = new DriverInfo(db);
        mCareInfo = new CareInfo(db, mDriverInfo);
        mCommunityCenterInfo = new CommunityCenterInfo(db);
        mDonationCenterInfo = new DonationCenterInfo(db);
    }

    public CareInfo getCareInfo() {
        return mCareInfo;
    }

    public CommunityCenterInfo getCommunityCenterInfo() {
        return mCommunityCenterInfo;
    }

    public DonationCenterInfo getDonationCenterInfo() {
        return mDonationCenterInfo;
    }

    public DriverInfo getDriverInfo() {
        return mDriverInfo;
    }

}
