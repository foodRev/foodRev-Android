package foodrev.org.foodrev.presentation.presenters.impl;

import com.google.firebase.auth.FirebaseAuth;

import foodrev.org.foodrev.presentation.presenters.DriverModePresenter;

/**
 * Created by darver on 3/24/17.
 */

public class DriverModePresenterImpl implements DriverModePresenter {

    private DriverModePresenter.View mView;

    @Override
    public void attachView(DriverModePresenter.View view) {
        mView = view;
    }

    @Override
    public void detachView() {

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

    @Override
    public void signOut() {
        if(mView != null) {
            FirebaseAuth.getInstance().signOut();
            mView.goToSignInActivity();
        }

    }
}
