package foodrev.org.foodrev.domain.infos;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import foodrev.org.foodrev.domain.interactors.GetFirebaseInfoInteractor;
import foodrev.org.foodrev.domain.interactors.impl.GetFirebaseInfoInteractorImpl;
import foodrev.org.foodrev.domain.infos.models.Driver;

public class DriverInfo extends AbstractInfo {
    private FirebaseDatabase mFirebaseDatabaseInstance;
    private ArrayList<Driver> mDrivers = null;
    private InfoUpdateListener mListener = null;
    private ReentrantReadWriteLock mLock = null;    // protects all data fields above.
    private UIObject mUIObject = null;
    private GetFirebaseInfoInteractorImpl.Callback mCallback;


    public DriverInfo(FirebaseDatabase firebaseDatabase, GetFirebaseInfoInteractor.Callback callback) {
        super(firebaseDatabase, callback);
        mDrivers = new ArrayList<>();
        DatabaseReference ref = firebaseDatabase.getReference("driver_app/drivers");
        mListener = new InfoUpdateListener(this);
        ref.addValueEventListener(mListener);
    }

    protected void updateData(DataSnapshot snapshot) {
        Log.i("dbging", "in DriverInfo.updateData: <" + snapshot.getKey() + ", " + snapshot.getValue() + ">");
        if (snapshot.getValue() == null) {
            Log.e("dbging", "in DriverInfo.updateData, the update is null.");
            return;
        }
        mLock.writeLock().lock();
        mDrivers.clear();
        for (DataSnapshot driverSnapshot : snapshot.getChildren()) {

            String driverId = driverSnapshot.getKey();
            if (driverSnapshot.getValue() == null) {
                break;
            }
            Driver driver = new Driver();
            if (!updateDriver(driver, driverSnapshot)) {
                Log.e("dbging", "in DriverInfo.updateData, this update has wrong format.");
            } else {
                mDrivers.add(driver);
            }

        }
        mLock.writeLock().unlock();

        if (mUIObject != null) {
            mUIObject.Refresh();
        }
        if(mCallback != null) {
            mCallback.onDriverInfoUpdated(this);
        }
    }

    private boolean updateDriver(Driver driver, DataSnapshot snapshot) {
        if (!mLock.writeLock().isHeldByCurrentThread()) {
            throw new RuntimeException("updateDriver doesn't have the write lock!");
        }

        DataSnapshot userInfo = snapshot.child("user_info");
        DataSnapshot c1 = userInfo.child(Driver.ADDRESS_KEY);
        DataSnapshot c2 = userInfo.child(Driver.PICURL_KEY);
        DataSnapshot c3 = userInfo.child(Driver.NAME_KEY);
        DataSnapshot c4 = userInfo.child(Driver.PHONE_NUMBER_KEY);
        DataSnapshot c5 = userInfo.child(Driver.CAR_TYPE_KEY);
        DataSnapshot c6 = userInfo.child(Driver.EMAIL_KEY);

        driver.setDriverID(snapshot.getKey());

        if (c1 == null || c2 == null || c3 == null || c4 == null) {
            Log.w("dbging", "in DriverInfo.updateDriver, some community center's child is null.");
            return false;
        }

        // Parse address.
        driver.setAddress(c1.getValue(String.class));

        // Parse image_url.
        driver.setPicUrl(c2.getValue(String.class));

        // Parse name.
        driver.setName(c3.getValue(String.class));

        // Parse phone_number.
        driver.setPhoneNumber(c4.getValue(String.class));

        // Parse car type.
        driver.setCarType(c5.getValue(String.class));

        // Parse phone_number.
        driver.setEmail(c6.getValue(String.class));


        DataSnapshot cares = snapshot.child("cares");
        ArrayList<Integer> caresList = new ArrayList<>();
        for (DataSnapshot care : cares.getChildren()) {
            Integer careId = care.getValue(Integer.class);
            // TODO: handle error
            caresList.add(careId);
        }

        driver.setCaresList(caresList);


        // Parse succeeds.
        return true;
    }

    public String getNewDriverId(){
        String newDriverId;
        while(true) {
            newDriverId = "manual:" +  (int)(Math.random()*1e6+1);
            if(getDriver(newDriverId) == null)
                break;
        }
        return newDriverId;
    }

    protected void updateError(String errorMessage) {
        Log.e("dbging", "in CommunityCenterInfo.updateError: " + errorMessage);
    }

    public int getNumDriver() {
        return mDrivers.size();
    }

    public Driver getDriver(int i) {
        return mDrivers.get(i);
    }

    public Driver getDriver(String driverId) {
        for (Driver driver : mDrivers) {
            if (driver.getDriverID().equals(driverId)) {
                return driver;
            }
        }
        return null;
    }

    public void setDriverUserInfo(Driver driver, String driverId) {
        for (int i = 0; i < mDrivers.size(); i++) {
            if (mDrivers.get(i).getDriverID().equals(driverId)) {
                Driver currentDriver = mDrivers.get(i);
                mLock.writeLock().lock();
                DatabaseReference driverUserInfoRef = mFirebaseDatabaseInstance.getReference("driver_app/drivers/" + driverId +"/user_info");
                currentDriver.updateUserInfo(driver);
                currentDriver.WriteUserInfoToSnapshot(driverUserInfoRef);
                mDrivers.set(i, currentDriver);
                mLock.writeLock().unlock();
                return;
            }
        }

        // Add new driver.
        mLock.writeLock().lock();
        DatabaseReference driverUserInfoRef = mFirebaseDatabaseInstance.getReference("driver_app/drivers/" + driverId +"/user_info");
        driver.WriteUserInfoToSnapshot(driverUserInfoRef);
        mDrivers.add(driver);
        mLock.writeLock().unlock();
    }

    public static String getDriverIdFromDisplayString(String displayString) {
        return new StringBuilder(new StringBuilder(displayString).reverse().toString().split("-",2)[0]).reverse().toString();
    }
    public String[] getDriverDisplayStringArray() {
        mLock.readLock().lock();
        String[] driverDisplayStrings = new String[mDrivers.size()];

        for(int i=0; i<mDrivers.size();i++) {
            Driver driver =  mDrivers.get(i);
            driverDisplayStrings[i] = driver.getDisplayString();
        }
        mLock.readLock().unlock();
        return driverDisplayStrings;
    }

    public void deleteCare(String driverId, Integer careId) {
        for (int i = 0; i < mDrivers.size(); i++) {
            if (mDrivers.get(i).getDriverID().equals(driverId)) {
                Driver currentDriver = mDrivers.get(i);
                mLock.writeLock().lock();
                DatabaseReference driverCaresRef = mFirebaseDatabaseInstance.getReference("driver_app/drivers/" + driverId +"/cares");
                currentDriver.deleteCare(careId);
                currentDriver.WriteCareListToSnapshot(driverCaresRef);
                mDrivers.set(i, currentDriver);
                mLock.writeLock().unlock();
                return;
            }
        }
        Log.wtf("wtf", "driver not found");

    }

    public void addCare(String driverId, Integer careId) {
        for (int i = 0; i < mDrivers.size(); i++) {
            if (mDrivers.get(i).getDriverID().equals(driverId)) {
                Driver currentDriver = mDrivers.get(i);
                mLock.writeLock().lock();
                DatabaseReference driverCaresRef = mFirebaseDatabaseInstance.getReference("driver_app/drivers/" + driverId +"/cares");
                currentDriver.addCare(careId);
                currentDriver.WriteCareListToSnapshot(driverCaresRef);
                mDrivers.set(i, currentDriver);
                mLock.writeLock().unlock();
                return;
            }
        }
        Log.wtf("wtf", "driver not found");
    }

}
