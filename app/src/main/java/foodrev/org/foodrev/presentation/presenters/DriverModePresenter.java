package foodrev.org.foodrev.presentation.presenters;

import foodrev.org.foodrev.presentation.presenters.base.BasePresenter;
import foodrev.org.foodrev.presentation.ui.BaseView;

/**
 * Created by darver on 3/24/17.
 */

public interface DriverModePresenter extends BasePresenter<DriverModePresenter.View> {
    interface View extends BaseView {
//        void signOut();
            void goToSignInActivity();
//            void goToDetailItemActivity();
    }

    void signOut();


}

