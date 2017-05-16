package foodrev.org.foodrev.presentation.presenters;

import foodrev.org.foodrev.presentation.presenters.base.BasePresenter;
import foodrev.org.foodrev.presentation.ui.BaseView;

/**
 * Created by darver on 5/15/17.
 */

public interface UserTypePresenter extends BasePresenter<UserTypePresenter.View> {
    interface View extends BaseView {
        void goToCoordinatorMode();
        void goToDriverMode();
    }
    void coordinatorSelected();
    void driverSelected();
}
