package foodrev.org.foodrev.presentation.presenters;

import foodrev.org.foodrev.domain.infos.CareInfo;
import foodrev.org.foodrev.domain.infos.CommunityCenterInfo;
import foodrev.org.foodrev.domain.infos.DonationCenterInfo;
import foodrev.org.foodrev.domain.infos.DriverInfo;
import foodrev.org.foodrev.domain.infos.models.DonationCenter;
import foodrev.org.foodrev.presentation.presenters.base.BasePresenter;
import foodrev.org.foodrev.presentation.ui.BaseView;

public interface MainPresenter extends BasePresenter<MainPresenter.View> {

    interface View extends BaseView {
        void signOut();
        void goToSignInActivity();
        void goToDetailItemActivity();
        void switchToPopulatedDataView();
        void showToastTest(String driverName);

        void refreshCareInfos(CareInfo careInfo);
        void refreshCommunityCenterInfos(CommunityCenterInfo communityCenterInfo);
        void refreshDonationCenterInfos(DonationCenterInfo donationCenterInfo);
        void refreshDriverInfos(DriverInfo driverInfo);
    }

    // TODO: Add your presenter methods

    void signOut();

}
