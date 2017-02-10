// Changed from drive app, needs to have one listener for all Centers.
package foodrev.org.foodrev.domain.infos;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import foodrev.org.foodrev.domain.models.CommunityCenter;

public class CommunityCenterInfo {
    private FirebaseDatabase mFirebaseDatabaseInstance;
    private HashMap<Integer, CommunityCenter> mCommunityCenters = null;
    private CommunityCenterListener mListener = null;
    private ReentrantReadWriteLock mLock = null;    // protects all data fields above.

    private UIObject mUIObject = null;

    public CommunityCenterInfo(FirebaseDatabase firebaseDatabase) {
        mLock = new ReentrantReadWriteLock(true);
        mCommunityCenters = new HashMap<>();
        mFirebaseDatabaseInstance = firebaseDatabase;
        DatabaseReference ref = firebaseDatabase.getReference("community_centers/");
        mListener = new CommunityCenterListener(this);
        ref.addValueEventListener(mListener);
    }

    public CommunityCenter getCommunityCenter(Integer id) {
        Log.i("dbging", "getCommunityCenter: " + id);

        mLock.readLock().lock();
        if (!mCommunityCenters.containsKey(id)) {
            mLock.readLock().unlock();
            return null;
        } else {
            CommunityCenter cc = mCommunityCenters.get(id);
            mLock.readLock().unlock();
            return cc;
        }
    }

    public void registorUI(UIObject ui) {
        mUIObject = ui;
    }

    private void updateData(DataSnapshot snapshot) {
        Log.i("dbging", "in CommunityCenterInfo.updateData: <" + snapshot.getKey() + ", " + snapshot.getValue() + ">");
        if (snapshot.getValue() == null) {
            Log.e("dbging", "in CommunityCenterInfo.updateData, the update is null.");
            return;
        }

        mLock.writeLock().lock();
        // Assumption: CommunityCenter ids are in order (No missing ids).
        for (Integer id = 0; ; id++) {
            DataSnapshot communityCenterSnapshot = snapshot.child(Long.toString(id));
            if (communityCenterSnapshot.getValue() == null) {
                break;
            }
            CommunityCenter cc = new CommunityCenter();
            if (!updateCommunityCenter(cc, communityCenterSnapshot)) {
                Log.e("dbging", "in CommunityCenterInfo.updateData, this update has wrong format.");
            } else {
                mCommunityCenters.put(id, cc);
            }

        }
        mLock.writeLock().unlock();

        if (mUIObject != null) {
            mUIObject.Refresh();
        }
    }

    private boolean updateCommunityCenter(CommunityCenter communityCenter, DataSnapshot snapshot) {
        if (!mLock.writeLock().isHeldByCurrentThread()) {
            throw new RuntimeException("updateCommunityCenter doesn't have the write lock!");
        }

        DataSnapshot c1 = snapshot.child("address");
        DataSnapshot c2 = snapshot.child("image_url");
        DataSnapshot c3 = snapshot.child("name");
        DataSnapshot c4 = snapshot.child("phone_number");
        if (c1 == null || c2 == null || c3 == null || c4 == null) {
            Log.w("dbging", "in CommunityCenterInfo.updateCommunityCenter, some community center's child is null.");
            return false;
        }

        // Parse address.
        communityCenter.setAddress(c1.getValue(String.class));

        // Parse image_url.
        communityCenter.setImageURL(c2.getValue(String.class));

        // Parse name.
        communityCenter.setName(c3.getValue(String.class));

        // Parse phone_number.
        communityCenter.setPhoneNumber(c4.getValue(String.class));

        // Parse succeeds.
        return true;
    }

    private void updateError(String errorMessage) {
        Log.e("dbging", "in CommunityCenterInfo.updateError: " + errorMessage);
    }

    public int getNumCommunityCenter() {
        return mCommunityCenters.size();
    }

    public void setCommunityCenter(CommunityCenter communityCenter, int mCommunityCenterId) {
        mCommunityCenters.put(mCommunityCenterId, communityCenter);
    }

    private class CommunityCenterListener implements ValueEventListener {
        CommunityCenterInfo mParent;

        public CommunityCenterListener(CommunityCenterInfo parent) {
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
