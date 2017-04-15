package foodrev.org.foodrev.presentation.presenters;

import foodrev.org.foodrev.presentation.presenters.base.BasePresenter;
import foodrev.org.foodrev.presentation.ui.BaseView;

/**
 * Created by darver on 4/14/17.
 */

public interface CreateDriverPresenter extends BasePresenter<CreateDriverPresenter.View>{
    interface View extends BaseView {

    }

    void goBack();
    void submitForm();
}
