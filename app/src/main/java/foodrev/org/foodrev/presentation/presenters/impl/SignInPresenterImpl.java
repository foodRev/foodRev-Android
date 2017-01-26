package foodrev.org.foodrev.presentation.presenters.impl;

import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;
import foodrev.org.foodrev.presentation.presenters.SignInPresenter;
import foodrev.org.foodrev.presentation.presenters.base.AbstractPresenter;

/**
 * Created by darver on 1/25/17.
 */

public class SignInPresenterImpl extends AbstractPresenter implements SignInPresenter{

    private SignInPresenter.View mView;

    public SignInPresenterImpl(Executor executor,
                             MainThread mainThread,
                             SignInPresenter.View view) {
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