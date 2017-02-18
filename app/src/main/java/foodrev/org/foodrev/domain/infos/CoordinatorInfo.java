package foodrev.org.foodrev.domain.infos;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import foodrev.org.foodrev.domain.infos.models.Coordinator;

public class CoordinatorInfo {
    private FirebaseDatabase mFirebaseDatabaseInstance;
    private HashMap<Integer, Coordinator> mCoordinators = null;
    private HashMap<Integer, CoordinatorInfoListener> mListeners = null;
    private HashMap<Integer, DatabaseReference> mRefs = null;
    private ReentrantReadWriteLock mLock = null;    // protects all data fields above.

    private UIObject mUIObject = null;

    public CoordinatorInfo(FirebaseDatabase firebaseDatabase) {
        mFirebaseDatabaseInstance = firebaseDatabase;
        mLock = new ReentrantReadWriteLock(true);
        mCoordinators = new HashMap<>();
        mListeners = new HashMap<>();
        mRefs = new HashMap<>();
    }

    public void addCoordinatorID(Integer id) {
        Log.i("dbging", "addCoordinatorID: " + id);

        mLock.writeLock().lock();
        if (mCoordinators.containsKey(id)) {
            mLock.writeLock().unlock();
            return;
        }

        DatabaseReference ref = mFirebaseDatabaseInstance.getReference("coordinators/" + id);
        CoordinatorInfoListener listener = new CoordinatorInfoListener(this);
        ref.addValueEventListener(listener);
        mListeners.put(id, listener);
        mRefs.put(id, ref);
        mLock.writeLock().unlock();
    }

    public Coordinator getCoordinator(Integer coordinatorID) {
        Log.i("dbging", "in CoordinatorInfo.getCoordinator: " + coordinatorID);

        mLock.readLock().lock();
        if (!mCoordinators.containsKey(coordinatorID)) {
            mLock.readLock().unlock();
            return null;
        } else {
            Coordinator coordinator = mCoordinators.get(coordinatorID);
            mLock.readLock().unlock();
            return coordinator;
        }
    }

    public void registorUI(UIObject ui) {
        mUIObject = ui;
    }

    private void updateData(DataSnapshot snapshot) {
        Log.i("dbging", "in CoordinatorInfo.updateData: <" + snapshot.getKey() + ", " + snapshot.getValue() + ">");

        Integer id = Integer.valueOf(snapshot.getKey());
        mLock.writeLock().lock();
        if (snapshot.getValue() == null) {
            Log.e("dbging", "in CoordinatorInfo.updateData, the update is null.");
        } else {
            Coordinator c = new Coordinator();
            if (!updateCoordinator(c, snapshot)) {
                Log.e("dbging", "in CoordinatorInfo.updateData, this update has wrong format.");
            } else {
                mCoordinators.put(id, c);
            }
        }
        mLock.writeLock().unlock();

        if (mUIObject != null) {
            mUIObject.Refresh();
        }
    }

    private boolean updateCoordinator(Coordinator coordinator, DataSnapshot snapshot) {
        if (!mLock.writeLock().isHeldByCurrentThread()) {
            throw new RuntimeException("updateCoordinator doesn't have the write lock!");
        }

        DataSnapshot c1 = snapshot.child("name");
        DataSnapshot c2 = snapshot.child("phone_number");
        if (c1 == null || c2 == null) {
            Log.w("dbging", "in CoordinatorInfo.updateCoordinator, some coordinator's child is null.");
            return false;
        }

        // Parse name.
        coordinator.setName(c1.getValue(String.class));

        // Parse phone_number.
        coordinator.setPhoneNumber(c2.getValue(String.class));

        // Parse succeeds.
        return true;
    }

    private void updateError(String errorMessage) {
        Log.e("dbging", "in donationCenterInfo.updateError: " + errorMessage);
    }

    private class CoordinatorInfoListener implements ValueEventListener {
        CoordinatorInfo mParent;

        public CoordinatorInfoListener(CoordinatorInfo parent) {
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
