package foodrev.org.foodrev.domain.interactors.impl;

import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;
import foodrev.org.foodrev.domain.interactors.SignInInteractor;
import foodrev.org.foodrev.domain.interactors.base.AbstractInteractor;

/**
 * Created by darver on 1/26/17.
 */

public class MainInteractorImpl extends AbstractInteractor implements SignInInteractor {

    private static final String TAG = "MainInteractorImpl";
    private Callback mCallback;


    public MainInteractorImpl(Executor executor, MainThread mainThread, Callback callback) {
        super(executor, mainThread);
        mCallback = callback;
    }

    public void run() {

    }
}
