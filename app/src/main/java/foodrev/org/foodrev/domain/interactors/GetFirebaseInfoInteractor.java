package foodrev.org.foodrev.domain.interactors;

import foodrev.org.foodrev.domain.infos.DriverInfo;
import foodrev.org.foodrev.domain.infos.PopulateInfos;
import foodrev.org.foodrev.domain.interactors.base.Interactor;

/**
 * Created by darver on 2/8/17.
 */

public interface GetFirebaseInfoInteractor extends Interactor {
    interface Callback {
        void retrievePopulateInfos(PopulateInfos populateInfos);
        void onCommunityCenterInfoUpdated();
        void onCareInfoUpdated();
        void onDonationCenterInfoUpdated();
        void onDriverInfoUpdated(DriverInfo driverInfo);
        void onDataReceiveFailed();
    }




}