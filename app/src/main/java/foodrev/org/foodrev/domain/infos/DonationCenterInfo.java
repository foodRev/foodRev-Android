// Auto-generated file, DO NOT EDIT.
// Changed from drive app, needs to have one listener for all Centers.
package foodrev.org.foodrev.domain.infos;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.foodrev.www.foodrev_android_coordinator_app.Interfaces.UIObject;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DonationCenterInfo {
    private HashMap<Integer, DonationCenter> mDonationCenters = null;
    private DonationCenterListener mListener = null;
    private ReentrantReadWriteLock mLock = null;    // protects all data fields above.

// Auto-generated file, DO NOT EDIT.
    private UIObject mUIObject = null;

    public DonationCenterInfo() {
        mLock = new ReentrantReadWriteLock(true);

        mDonationCenters = new HashMap<>();
        DatabaseReference ref = Root.getDatabase().getReference("donation_centers/");
        mListener = new DonationCenterListener(this);
        ref.addValueEventListener(mListener);
    }

    public DonationCenter getDonationCenter(Integer id) {
        Log.i("dbging", "getDonationCenter: " + id);

        mLock.readLock().lock();
        if (!mDonationCenters.containsKey(id)) {
            mLock.readLock().unlock();
            return null;
        } else {
            DonationCenter dc = mDonationCenters.get(id);
// Auto-generated file, DO NOT EDIT.
            mLock.readLock().unlock();
            return dc;
        }
    }

    public void registorUI(UIObject ui) {
        mUIObject = ui;
    }

    private void updateData(DataSnapshot snapshot) {
        Log.i("dbging", "in DonationCenterInfo.updateData: <" + snapshot.getKey() + ", " + snapshot.getValue() + ">");
        if (snapshot.getValue() == null) {
            Log.e("dbging", "in DonationCenterInfo.updateData, the update is null.");
            return;
        }

        mLock.writeLock().lock();
        // Assumption: DonationCenter ids are in order (No missing ids).
        for (Integer id = 0; ; id++) {
            DataSnapshot donationCenterSnapshot = snapshot.child(Long.toString(id));
// Auto-generated file, DO NOT EDIT.
            if (donationCenterSnapshot.getValue() == null) {
                break;
            }
            DonationCenter dc = new DonationCenter();
            if (!updateDonationCenter(dc, donationCenterSnapshot)) {
                Log.e("dbging", "in DonationCenterInfo.updateData, this update has wrong format.");
            } else {
                mDonationCenters.put(id, dc);
            }

        }
        mLock.writeLock().unlock();

        if (mUIObject != null) {
            mUIObject.Refresh();
        }
    }

    private boolean updateDonationCenter(DonationCenter donationCenter, DataSnapshot snapshot) {
        if (!mLock.writeLock().isHeldByCurrentThread()) {
// Auto-generated file, DO NOT EDIT.
            throw new RuntimeException("updateDonationCenter doesn't have the write lock!");
        }

        DataSnapshot c1 = snapshot.child("address");
        DataSnapshot c2 = snapshot.child("image_url");
        DataSnapshot c3 = snapshot.child("name");
        DataSnapshot c4 = snapshot.child("phone_number");
        if (c1 == null || c2 == null || c3 == null || c4 == null) {
            Log.w("dbging", "in DonationCenterInfo.updateDonationCenter, some donation center's child is null.");
            return false;
        }

        // Parse address.
        donationCenter.setAddress(c1.getValue(String.class));

        // Parse image_url.
        donationCenter.setImageURL(c2.getValue(String.class));

        // Parse name.
        donationCenter.setName(c3.getValue(String.class));
// Auto-generated file, DO NOT EDIT.

        // Parse phone_number.
        donationCenter.setPhoneNumber(c4.getValue(String.class));

        // Parse sudceeds.
        return true;
    }

    private void updateError(String errorMessage) {
        Log.e("dbging", "in DonationCenterInfo.updateError: " + errorMessage);
    }

    public int getNumDonationCenter() {
        return mDonationCenters.size();
    }

    public void setDonationCenter(DonationCenter donationCenter, int mDonationCenterId) {
        mDonationCenters.put(mDonationCenterId, donationCenter);
    }

// Auto-generated file, DO NOT EDIT.
    private class DonationCenterListener implements ValueEventListener {
        DonationCenterInfo mParent;

        public DonationCenterListener(DonationCenterInfo parent) {
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
