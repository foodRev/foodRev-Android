package foodrev.org.foodrev.domain.interactors;

import foodrev.org.foodrev.domain.infos.CareInfo;
import foodrev.org.foodrev.domain.infos.CommunityCenterInfo;
import foodrev.org.foodrev.domain.infos.DonationCenterInfo;
import foodrev.org.foodrev.domain.infos.DriverInfo;
import foodrev.org.foodrev.domain.infos.PopulateInfos;
import foodrev.org.foodrev.domain.interactors.base.Interactor;

/**
 * Created by darver on 2/8/17.
 */

public interface GetFirebaseInfoInteractor extends Interactor {
    interface Callback {
        void onCommunityCenterInfoUpdated(CommunityCenterInfo communityCenterInfo);
        void onCareInfoUpdated(CareInfo careInfo);
        void onDonationCenterInfoUpdated(DonationCenterInfo donationCenterInfo);
        void onDriverInfoUpdated(DriverInfo driverInfo);
        void onDataReceiveFailed();
    }




}