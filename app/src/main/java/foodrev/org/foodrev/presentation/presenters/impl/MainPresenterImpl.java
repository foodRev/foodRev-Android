package foodrev.org.foodrev.presentation.presenters.impl;

import com.google.firebase.auth.FirebaseAuth;

import foodrev.org.foodrev.domain.interactors.SampleInteractor;
import foodrev.org.foodrev.presentation.presenters.MainPresenter;


public class MainPresenterImpl implements MainPresenter, SampleInteractor.Callback {

    private MainPresenter.View mView;

    @Override
    public void attachView(View view) {
        mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void resume() {
        // populate firebase stuff
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
        // Firebase sign out
        if(mView != null) {
            FirebaseAuth.getInstance().signOut();
            mView.goToSignInActivity();
        }
    }


}
