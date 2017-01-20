package foodrev.org.foodrev.presentation.presenters.impl;

import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;
import foodrev.org.foodrev.domain.interactors.SampleInteractor;
import foodrev.org.foodrev.presentation.presenters.MainPresenter;
import foodrev.org.foodrev.presentation.presenters.base.AbstractPresenter;


public class MainPresenterImpl extends AbstractPresenter implements MainPresenter,
        SampleInteractor.Callback {

    private MainPresenter.View mView;

    public MainPresenterImpl(Executor executor,
                             MainThread mainThread,
                             View view) {
        super(executor, mainThread);
        mView = view;
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
