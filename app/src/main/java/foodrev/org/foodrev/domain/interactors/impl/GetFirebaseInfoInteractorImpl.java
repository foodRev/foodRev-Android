package foodrev.org.foodrev.domain.interactors.impl;

import com.google.firebase.database.FirebaseDatabase;

import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;
import foodrev.org.foodrev.domain.infos.InfoPopulator;
import foodrev.org.foodrev.domain.interactors.GetFirebaseInfoInteractor;
import foodrev.org.foodrev.domain.interactors.base.AbstractInteractor;

/**
 * Created by darver on 2/8/17.
 */

public class GetFirebaseInfoInteractorImpl extends AbstractInteractor implements GetFirebaseInfoInteractor {

    private static final String TAG = "GetFirebaseInteractorInteractorImpl";
    private GetFirebaseInfoInteractor.Callback mCallback;
    private FirebaseDatabase mFirebaseDatabase;


    public GetFirebaseInfoInteractorImpl(Executor executor,
                                         MainThread mainThread,
                                         GetFirebaseInfoInteractor.Callback callback,
                                         FirebaseDatabase firebaseDatabase) {
        super(executor, mainThread);
        mCallback = callback;
        mFirebaseDatabase = firebaseDatabase;
    }


    @Override
    public void run() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                InfoPopulator infoPopulator = new InfoPopulator(mFirebaseDatabase, );
                mCallback.onDataReceived(infoPopulator);
            }
        });
    }

    @Override
    public void populateInfos() {
        //Maybe remove?
    }

}
