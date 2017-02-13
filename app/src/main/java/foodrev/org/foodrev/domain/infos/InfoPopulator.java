package foodrev.org.foodrev.domain.infos;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by darver on 2/9/17.
 */

public class InfoPopulator {

    private CareInfo mCareInfo;
    private CommunityCenterInfo mCommunityCenterInfo;
    private DonationCenterInfo mDonationCenterInfo;
    private DriverInfo mDriverInfo;

    public InfoPopulator(FirebaseDatabase db) {
        mDriverInfo = new DriverInfo(db);
        mCareInfo = new CareInfo(db, mDriverInfo);
        mCommunityCenterInfo = new CommunityCenterInfo(db);
        mDonationCenterInfo = new DonationCenterInfo(db);
        Log.d("InfoPopulator", "Infos Populated");

    }

    public CareInfo getmCareInfo() {
        return mCareInfo;
    }

    public CommunityCenterInfo getmCommunityCenterInfo() {
        return mCommunityCenterInfo;
    }

    public DonationCenterInfo getmDonationCenterInfo() {
        return mDonationCenterInfo;
    }

    public DriverInfo getmDriverInfo() {
        return mDriverInfo;
    }

}
