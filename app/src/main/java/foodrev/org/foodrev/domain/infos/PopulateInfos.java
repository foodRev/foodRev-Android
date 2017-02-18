package foodrev.org.foodrev.domain.infos;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import foodrev.org.foodrev.domain.interactors.GetFirebaseInfoInteractor;
import foodrev.org.foodrev.domain.interactors.impl.GetFirebaseInfoInteractorImpl;

/**
 * Created by darver on 2/9/17.
 */

public class PopulateInfos {

    private CareInfo mCareInfo;
    private CommunityCenterInfo mCommunityCenterInfo;
    private DonationCenterInfo mDonationCenterInfo;
    private DriverInfo mDriverInfo;
    private GetFirebaseInfoInteractor.Callback mCallback;


    public PopulateInfos(FirebaseDatabase db, GetFirebaseInfoInteractorImpl.Callback callback) {
        mCallback = callback;
        mDriverInfo = new DriverInfo(db, callback);
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
