package foodrev.org.foodrev.presentation.presenters;

import foodrev.org.foodrev.presentation.presenters.base.BasePresenter;
import foodrev.org.foodrev.presentation.ui.BaseView;

/**
 * Created by darver on 1/25/17.
 */

public interface SignInPresenter extends BasePresenter {
    interface View extends BaseView {
        void displayLoading();

    }
    // TODO: add presenter methods
}
