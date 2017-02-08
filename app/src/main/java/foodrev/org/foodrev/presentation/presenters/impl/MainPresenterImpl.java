package foodrev.org.foodrev.presentation.presenters.impl;

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
}
