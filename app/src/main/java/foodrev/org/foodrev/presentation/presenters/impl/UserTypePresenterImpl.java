package foodrev.org.foodrev.presentation.presenters.impl;

import foodrev.org.foodrev.presentation.presenters.UserTypePresenter;

/**
 * Created by darver on 5/15/17.
 */

public class UserTypePresenterImpl implements UserTypePresenter {
    private UserTypePresenter.View mView;

    @Override
    public void attachView(View view) {
        mView = view;
    }

    @Override
    public void coordinatorSelected() {
        // Do some checking to see if the authenticated user is able to be coordinator

        mView.goToCoordinatorMode();
    }

    @Override
    public void driverSelected() {
        // Look for driver associated with auth hash

        mView.goToDriverMode();
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

}
