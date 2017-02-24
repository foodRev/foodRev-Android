package foodrev.org.foodrev.domain.interactors.impl;

import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;

import foodrev.org.foodrev.App;
import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;
import foodrev.org.foodrev.domain.infos.PopulateInfos;
import foodrev.org.foodrev.domain.interactors.GetFirebaseInfoInteractor;
import foodrev.org.foodrev.domain.interactors.base.AbstractInteractor;

/**
 * Created by darver on 2/8/17.
 */

public class GetFirebaseInfoInteractorImpl extends AbstractInteractor implements GetFirebaseInfoInteractor {

    private static final String TAG = "GetFirebaseInfoInteractorImpl";
    private GetFirebaseInfoInteractor.Callback mCallback;
    private FirebaseDatabase mFirebaseDatabase;
    private PopulateInfos mPopulateInfos;
    private Context mContext;

    public GetFirebaseInfoInteractorImpl(Executor executor,
                                         MainThread mainThread,
                                         GetFirebaseInfoInteractor.Callback callback,
                                         FirebaseDatabase firebaseDatabase,
                                         Context context) {
        super(executor, mainThread);
        mCallback = callback;
        mFirebaseDatabase = firebaseDatabase;
        mContext = context;
    }


    @Override
    public void run() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {

                // TODO: make callback to return mPopulateInfos
                mPopulateInfos = new PopulateInfos(mFirebaseDatabase, mCallback);
            }
        });
    }



}
