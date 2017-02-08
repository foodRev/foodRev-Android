// Auto-generated file, DO NOT EDIT.
package foodrev.org.foodrev.domain.models;

import android.graphics.Bitmap;

import org.foodrev.www.foodrev_android_coordinator_app.Interfaces.PostDownloadBitmap;
import org.foodrev.www.foodrev_android_coordinator_app.Tasks.DownloadBitmapTask;

public class DonationCenter implements PostDownloadBitmap {
    private String mName = null;
    private String mAddress = null;
    private String mImageURL = null;
    private String mPhoneNumber = null;
    private Bitmap mImage = null;

    public DonationCenter() {
    }

    public String getName() {
        return mName;
    }
// Auto-generated file, DO NOT EDIT.

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getImageURL() {
        return mImageURL;
    }


    public String getPhoneNumber() {
        return mPhoneNumber;
// Auto-generated file, DO NOT EDIT.
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public void copy(DonationCenter otherDonationCenter) {
        this.mAddress = otherDonationCenter.mAddress;
        this.mImageURL = otherDonationCenter.mImageURL;
        this.mName = otherDonationCenter.mName;
        this.mPhoneNumber = otherDonationCenter.mPhoneNumber;
    }

    public static DonationCenter getNewDonationCenter() {
        DonationCenter donationCenter = new DonationCenter();
        donationCenter.setName("Name");
        donationCenter.setImageURL("http://abc.com/img.png");
        donationCenter.setPhoneNumber("000-000-0000");
        donationCenter.setAddress("Address");
        return donationCenter;
// Auto-generated file, DO NOT EDIT.
    }

    public Bitmap getPicture() {
        return mImage;
    }

    public void setImageURL(String imageURL) {
        if (mImageURL == null || !mImageURL.equals(imageURL)) {
            mImageURL = imageURL;
            mImage = null;
            DownloadBitmapTask task = new DownloadBitmapTask(mImageURL, this);
            task.execute();
        }
    }

    @Override
    public void DownloadBitmapDone(Bitmap bm) {
        mImage = bm;
    }

// Auto-generated file, DO NOT EDIT.
}
