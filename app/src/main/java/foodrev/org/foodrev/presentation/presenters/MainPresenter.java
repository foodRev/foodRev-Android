package foodrev.org.foodrev.presentation.presenters;

import foodrev.org.foodrev.presentation.presenters.base.BasePresenter;
import foodrev.org.foodrev.presentation.ui.BaseView;

public interface MainPresenter extends BasePresenter<MainPresenter.View> {

    interface View extends BaseView {
        // TODO: Add your view methods
        void signOut();
        void goToSignInActivity();
        void goToDetailItemActivity();
        void switchToPopulatedDataView();
    }

    // TODO: Add your presenter methods

    void signOut();

}
