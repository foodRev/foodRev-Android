package foodrev.org.foodrev.domain.interactors.impl;

import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;
import foodrev.org.foodrev.domain.interactors.GetFirebaseInfoInteractor;
import foodrev.org.foodrev.domain.interactors.base.AbstractInteractor;

/**
 * Created by darver on 2/8/17.
 */

public class GetFirebaseInfoInteractorImpl extends AbstractInteractor implements GetFirebaseInfoInteractor {

    private static final String TAG = "GetFirebaseInteractorInteractorImpl";
    private GetFirebaseInfoInteractor.Callback mCallback;


    public GetFirebaseInfoInteractorImpl(Executor executor, MainThread mainThread, GetFirebaseInfoInteractor.Callback callback) {
        super(executor, mainThread);
        mCallback = callback;
    }

    public void run() {

    }
}
