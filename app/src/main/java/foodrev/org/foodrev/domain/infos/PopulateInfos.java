package foodrev.org.foodrev.domain.infos;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import foodrev.org.foodrev.domain.interactors.GetFirebaseInfoInteractor;

/**
 * Created by darver on 2/9/17.
 */

public class PopulateInfos {

    private CareInfo mCareInfo;
    private CommunityCenterInfo mCommunityCenterInfo;
    private DonationCenterInfo mDonationCenterInfo;
    private DriverInfo mDriverInfo;


    public PopulateInfos(FirebaseDatabase db, AllDataReceived allDataReceived) {
        mDriverInfo = new DriverInfo(db, allDataReceived);
        mCareInfo = new CareInfo(db, allDataReceived, mDriverInfo);
        mCommunityCenterInfo = new CommunityCenterInfo(db, allDataReceived);
        mDonationCenterInfo = new DonationCenterInfo(db, allDataReceived);
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
