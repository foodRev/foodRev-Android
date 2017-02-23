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

public abstract class AbstractInfo implements Serializable{
    private FirebaseDatabase mFirebaseDatabaseInstance;
    private ReentrantReadWriteLock mLock = null;    // protects all data fields above.
    private BaseListener mListener = null;
    private GetFirebaseInfoInteractorImpl.Callback mCallback;

    public AbstractInfo(FirebaseDatabase firebaseDatabase, GetFirebaseInfoInteractorImpl.Callback callback) {
        mLock = new ReentrantReadWriteLock(true);
        mFirebaseDatabaseInstance = firebaseDatabase;
        mListener = new BaseListener(this);
        mCallback = callback;
    }

    protected abstract void updateData(DataSnapshot snapshot);

    protected abstract void updateError(String error);

    protected class BaseListener implements ValueEventListener {
        AbstractInfo mParent;

        public BaseListener(AbstractInfo parent) {
            mParent = parent;
        }

        @Override
        public void onDataChange(DataSnapshot snapshot) {
            mParent.updateData(snapshot);
        }

        @Override
        public void onCancelled(DatabaseError error) {
            mParent.updateError(error.getMessage());
        }
    }


}
