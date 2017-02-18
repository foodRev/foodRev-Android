
package foodrev.org.foodrev.domain.infos;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import foodrev.org.foodrev.domain.infos.models.Destination;

public class DestinationInfo {
    private FirebaseDatabase mFirebaseDatabaseInstance;
    private HashMap<Integer, Destination> mDestinations = null;
    private ReentrantReadWriteLock mLock = null;    // protects all data fields above.
    private UIObject mUIObject = null;

    protected DestinationListener mListener = null;


    public DestinationInfo(FirebaseDatabase firebaseDatabase) {
        mLock = new ReentrantReadWriteLock(true);
        mFirebaseDatabaseInstance = firebaseDatabase;
        mDestinations = new HashMap<>();
        mListener = new DestinationListener(this);
    }

    public Destination getDestination(Integer id) {
        Log.i("dbging", "getDestination: " + id);

        mLock.readLock().lock();
        if (!mDestinations.containsKey(id)) {
            mLock.readLock().unlock();
            return null;
        } else {
            Destination cc = mDestinations.get(id);
            mLock.readLock().unlock();
            return cc;
        }
    }

    public void registerUI(UIObject ui) {
        mUIObject = ui;
    }

    protected void updateData(DataSnapshot snapshot) {
        Log.i("dbging", "in DestinationInfo.updateData: <" + snapshot.getKey() + ", " + snapshot.getValue() + ">");
        if (snapshot.getValue() == null) {
            Log.e("dbging", "in DestinationInfo.updateData, the update is null.");
            return;
        }

        mLock.writeLock().lock();
        // Assumption: Destination ids are in order (No missing ids).
        for (Integer id = 0; ; id++) {
            DataSnapshot destinationSnapshot = snapshot.child(Long.toString(id));
            if (destinationSnapshot.getValue() == null) {
                break;
            }
            Destination d = new Destination();
            if (!updateDestination(d, destinationSnapshot)) {
                Log.e("dbging", "in DestinationInfo.updateData, this update has wrong format.");
            } else {
                mDestinations.put(id, d);
            }

        }
        mLock.writeLock().unlock();
        if (mUIObject != null) {
            mUIObject.Refresh();
        }
    }

    private boolean updateDestination(Destination destination, DataSnapshot snapshot) {
        if (!mLock.writeLock().isHeldByCurrentThread()) {
            throw new RuntimeException("updateDestination doesn't have the write lock!");
        }

        DataSnapshot c1 = snapshot.child("address");
        DataSnapshot c2 = snapshot.child("image_url");
        DataSnapshot c3 = snapshot.child("name");
        DataSnapshot c4 = snapshot.child("phone_number");
        if (c1 == null || c2 == null || c3 == null || c4 == null) {
            Log.w("dbging", "in DestinationInfo.updateDestination, some destination's child is null.");
            return false;
        }

        // Parse address.
        destination.setAddress(c1.getValue(String.class));

        // Parse image_url.
        destination.setImageURL(c2.getValue(String.class));

        // Parse name.
        destination.setName(c3.getValue(String.class));

        // Parse phone_number.
        destination.setPhoneNumber(c4.getValue(String.class));

        // Parse succeeds.
        return true;
    }

    private void updateError(String errorMessage) {
        Log.e("dbging", "in DestinationInfo.updateError: " + errorMessage);
    }

    public int getNumDestination() {
        return mDestinations.size();
    }

    public void setDestination(Destination destination, int mDestinationId) {
        mDestinations.put(mDestinationId, destination);
    }

    private class DestinationListener implements ValueEventListener {
        DestinationInfo mParent;

        public DestinationListener(DestinationInfo parent) {
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
