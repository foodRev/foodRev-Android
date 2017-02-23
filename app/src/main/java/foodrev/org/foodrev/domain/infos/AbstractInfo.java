package foodrev.org.foodrev.domain.infos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.Collections;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import foodrev.org.foodrev.domain.infos.models.AbstractModel;
import foodrev.org.foodrev.domain.interactors.impl.GetFirebaseInfoInteractorImpl;

/**
 * Created by darver on 2/23/17.
 */

public abstract class AbstractInfo implements Serializable {

    public static final String DRIVER_TITLE = "Drivers";
    public static final String DONOR_TITLE = "Donors";
    public static final String COMMUNITY_CENTER_TITLE = "Community Centers";
    public static final String CARE_TITLE = "Cares";

    protected FirebaseDatabase mFirebaseDatabaseInstance = null;
    protected ReentrantReadWriteLock mLock = null;    // protects all data fields above.
    protected GetFirebaseInfoInteractorImpl.Callback mCallback = null;
    protected InfoUpdateListener mListener = null;
    protected String mInfoType;

    public String infoType;

    public AbstractInfo(FirebaseDatabase firebaseDatabase, GetFirebaseInfoInteractorImpl.Callback callback) {
        mLock = new ReentrantReadWriteLock(true);
        mFirebaseDatabaseInstance = firebaseDatabase;
        mListener = new InfoUpdateListener(this);
        mCallback = callback;
    }

    protected abstract void updateData(DataSnapshot snapshot);
    protected abstract void updateError(String error);
    protected abstract AbstractModel get(int position);
    protected abstract int size();
}
