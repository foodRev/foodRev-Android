package foodrev.org.foodrev.domain.models;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;

import org.foodrev.www.foodrev_android_coordinator_app.Interfaces.PostDownloadBitmap;
import org.foodrev.www.foodrev_android_coordinator_app.Tasks.DownloadBitmapTask;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by abhishekchugh on 3/5/16.
 */
public class Driver implements PostDownloadBitmap{
    public final static String NAME_KEY = "name";
    public final static String PHONE_NUMBER_KEY = "phone_number";
    public final static String CAR_TYPE_KEY = "car_type";
    public final static String ADDRESS_KEY = "address";
    public final static String EMAIL_KEY = "email";
    public final static String PICURL_KEY = "pic_url";

    private String mDriverID = null;
    private String mName = null;
    private String mPhoneNumber = null;
    private String mCarType = null;
    private String mAddress = null;
    private String mEmail = null;
    private String mPicUrl = null;
    private ArrayList<Integer> mCares = null;
    private Bitmap mPicture = null;

    public String getName() {
        return mName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getCarType() {
        return mCarType;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getDriverID() {
        return mDriverID;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPicUrl() {
        return mPicUrl;
    }

    public Driver(){
        mName = "Unknown name";
        mPhoneNumber = "(123) 456-7890";
        mAddress = "Unknown Address";
        mEmail = "invalid@email.com";
        mPicUrl = "http://images.com/bogus_img.jpg";
        mCares = new ArrayList<>();
    }

    public ArrayList<Integer> getCaresList() {
        return mCares;
    }

    public void setCaresList(ArrayList<Integer> caresList) {
        mCares = caresList;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public void setCarType(String carType) {
        mCarType = carType;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public void setDriverID(String driverId) {
        mDriverID = driverId;
    }

    public String getDisplayString(){
        return getName() + "-" + getDriverID();
    }
    public void setEmail(String email) {
        mEmail = email;
    }

    public Bitmap getPicture() {
        return mPicture;
    }

    public void setPicUrl(String picUrl) {
        if(mPicture == null || !mPicUrl.equals(picUrl)) {
            mPicUrl = picUrl;
            mPicture = null;
            DownloadBitmapTask task = new DownloadBitmapTask(mPicUrl, this);
            task.execute();
        }
    }

    @Override
    public void DownloadBitmapDone(Bitmap bm) {
        mPicture = bm;
    }

    public void WriteUserInfoToSnapshot(DatabaseReference driverUserInfoRef) {

        driverUserInfoRef.child(NAME_KEY).setValue(mName);
        driverUserInfoRef.child(PHONE_NUMBER_KEY).setValue(mPhoneNumber);
        driverUserInfoRef.child(CAR_TYPE_KEY).setValue(mCarType);
        driverUserInfoRef.child(ADDRESS_KEY).setValue(mAddress);
        driverUserInfoRef.child(EMAIL_KEY).setValue(mEmail);
        driverUserInfoRef.child(PICURL_KEY).setValue(mPicUrl);
    }

    public void WriteCareListToSnapshot(DatabaseReference driverCaresRef) {
        HashSet<Integer> caresSet = new HashSet<>();

        for (Integer careNum = 0; careNum < mCares.size();careNum++) {
            caresSet.add(mCares.get(careNum));
            Log.i(careNum.toString(), mCares.get(careNum).toString());
        }
        driverCaresRef.setValue(caresSet);
    }

    public void updateUserInfo(Driver driver) {
        assert (driver.mDriverID.equals(mDriverID));
        mName = driver.mName;
        mPhoneNumber = driver.mPhoneNumber;
        mCarType = driver.mCarType;
        mAddress = driver.mAddress;
        mEmail = driver.mEmail;
        mPicUrl = driver.mPicUrl;
    }

    public static String[] getCarTypeStringArray() {
        return new String[]{"", "Standard", "Compact", "Sedan", "Station Wagon", "Truck", "Van", "Bicycle", "SUV", "Other", "Unknown"};
    }

    public void deleteCare(Integer id) {
        if (mCares.contains(id)) {
            mCares.remove(id);
        }
    }

    public void addCare(Integer id) {
        if(!mCares.contains(id)) {
            mCares.add(id);
        }
    }

}
