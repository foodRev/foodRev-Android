// Changed from drive app, needs to have one listener for all Centers.
package foodrev.org.foodrev.domain.infos;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import foodrev.org.foodrev.domain.infos.models.Care;
import foodrev.org.foodrev.domain.interactors.impl.GetFirebaseInfoInteractorImpl;

public class CareInfo extends AbstractInfo {
    private DriverInfo mDriverInfo;
    private static final Integer MAXCARES = 10000;
    private HashMap<Integer, Care> mCares = null;
    private UIObject mUIObject = null;

    String infoType = CARE_TITLE;

    public CareInfo(FirebaseDatabase firebaseDatabase, GetFirebaseInfoInteractorImpl.Callback callback, DriverInfo driverInfo) {
        super(firebaseDatabase, callback);
        mDriverInfo = driverInfo;
        mCares = new HashMap<>();
        DatabaseReference caresRef = firebaseDatabase.getReference("cares/");
        mListener = new InfoUpdateListener(this);
        caresRef.addValueEventListener(mListener);
    }

    public void setCare(Care care, Integer id) {
        Log.i("dbging", "setCare: " + id);

        Care prevCare = getCare(id);
        if (prevCare != null) {
            if (!prevCare.getDriverID().equals(care.getDriverID())) {
                mDriverInfo.deleteCare(prevCare.getDriverID(), id);
                mDriverInfo.addCare(care.getDriverID(), id);
            }
        } else {
            mDriverInfo.addCare(care.getDriverID(), id);
        }

        mLock.writeLock().lock();
        DatabaseReference careRef = mFirebaseDatabaseInstance.getReference("cares/list" + id.toString());
        care.WriteToSnapshot(careRef);
        mCares.put(id, care);
        mLock.writeLock().unlock();
    }

    public void insertDateSorted(ArrayList<Care> careList, Care care) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            Date date = format.parse(care.getDate());
            for (int i = 0; i < careList.size(); i++) {
                Care currentCare = careList.get(i);
                Date currentDate = format.parse(currentCare.getDate());
                if (currentDate.after(date)) continue;
                if (currentDate.equals(date) && currentCare.getCareID() < care.getCareID())
                    continue;
                careList.add(i, care);
                return;

            }
            careList.add(care);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Care> getListOfCaresForDriverId(String driverId) {
        ArrayList<Care> careList = new ArrayList<>();

        for (Care care : mCares.values()) {
            if (care.getDriverID().equals(driverId))
                insertDateSorted(careList, care);
        }

        return careList;
    }

    public ArrayList<Care> getListOfCaresForCommunityCenter(Integer communityCenter) {
        ArrayList<Care> careList = new ArrayList<>();

        for (Care care : mCares.values()) {
            boolean has_center = false;
            for (Care.Step step : care.getSteps()) {
                if (step.getCenterID() == communityCenter &&
                        step.getStepType() == Care.StepType.DRIVE_COMMUNITY_CENTER) {
                    has_center = true;
                    break;
                }
            }
            if (has_center) {
                insertDateSorted(careList, care);
            }
        }
        return careList;
    }

    public ArrayList<Care> getListOfCaresForDonationCenter(Integer donationCenter) {
        ArrayList<Care> careList = new ArrayList<>();
        for (Care care : mCares.values()) {
            boolean has_center = false;
            for (Care.Step step : care.getSteps()) {
                if (step.getCenterID().equals(donationCenter) &&
                        step.getStepType() == Care.StepType.DRIVE_DONATION_CENTER) {
                    has_center = true;
                    break;
                }
            }
            if (has_center) {
                insertDateSorted(careList, care);
            }
        }
        return careList;
    }

    public Care get(int index) {
        ArrayList<Integer> careIdArr = new ArrayList<>();
        for (Integer careId : mCares.keySet()) {
            careIdArr.add(careId);
        }
        Collections.sort(careIdArr);
        if (index < careIdArr.size()) {
            return getCare(careIdArr.get(index));
        } else {
            return null;
        }
    }

    public Care getCare(Integer id) {
        Log.i("dbging", "getCare: " + id);

        mLock.readLock().lock();
        if (!mCares.containsKey(id)) {
            mLock.readLock().unlock();
            return null;
        } else {
            Care care = mCares.get(id);
            mLock.readLock().unlock();
            return care;
        }
    }

    public int size() {return mCares.size();};

    public void registorUI(UIObject ui) {
        mUIObject = ui;
    }

    protected void updateData(DataSnapshot snapshot) {
        Log.i("dbging", "in CaresListInfo.updateData: <" + snapshot.getKey() + ", " + snapshot.getValue() + ">");
        if (snapshot.getValue() == null) {
            Log.e("dbging", "in CaresListInfo.updateData, the update is null.");
            return;
        }

        mLock.writeLock().lock();
        for (Integer id = 0; id < MAXCARES; id++) {
            DataSnapshot careSnapshot = snapshot.child(Long.toString(id));
            if (careSnapshot.getValue() == null) {
                continue;
            }
            Care care = new Care();
            //TODO: add CaresWatcher
//            if (!CaresWatcher.updateCare(care, careSnapshot)) {
//                Log.e("dbging", "in CaresListInfo.updateData, this update has wrong format.");
//            } else {
//                mCares.put(id, care);
//            }
        }
        mLock.writeLock().unlock();

        if (mUIObject != null) {
            mUIObject.Refresh();
        }
        mCallback.onCareInfoUpdated(this);
    }

    protected void updateError(String errorMessage) {
        Log.e("dbging", "in CaresListInfo.updateError: " + errorMessage);
    }

    public Integer getNewCareId() {

        int maxCareId = -1;
        mLock.writeLock().lock();
        for (Integer careId : mCares.keySet()) {
            if (careId > maxCareId) {
                maxCareId = careId;
            }
        }
        Integer careId = maxCareId + 1;
        mLock.writeLock().unlock();

        return careId;
    }

    public void deleteCare(Integer careId) {
        mLock.writeLock().lock();
        Care care = getCare(careId);
        if (care != null) {
            if (care.getDriverID()!=null && care.getDriverID()!="") {
                mDriverInfo.deleteCare(care.getDriverID(), careId);
            }
        }

        DatabaseReference careRef = mFirebaseDatabaseInstance.getReference("cares/" + careId);
        // TODO remove reference in the driver care section.
        careRef.removeValue();
        mCares.remove(careId);
        mLock.writeLock().unlock();
    }
}
