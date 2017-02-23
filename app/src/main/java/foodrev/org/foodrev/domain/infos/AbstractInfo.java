package foodrev.org.foodrev.domain.infos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import foodrev.org.foodrev.domain.interactors.impl.GetFirebaseInfoInteractorImpl;

/**
 * Created by darver on 2/23/17.
 */

public abstract class AbstractInfo implements Serializable {
    protected FirebaseDatabase mFirebaseDatabaseInstance = null;
    protected ReentrantReadWriteLock mLock = null;    // protects all data fields above.
    protected GetFirebaseInfoInteractorImpl.Callback mCallback = null;
    protected InfoUpdateListener mListener = null;

    public AbstractInfo(FirebaseDatabase firebaseDatabase, GetFirebaseInfoInteractorImpl.Callback callback) {
        mLock = new ReentrantReadWriteLock(true);
        mFirebaseDatabaseInstance = firebaseDatabase;
        mListener = new InfoUpdateListener(this);
        mCallback = callback;
    }

    protected abstract void updateData(DataSnapshot snapshot);
    protected abstract void updateError(String error);
}
