package foodrev.org.foodrev.domain.infos.models;

import android.graphics.Bitmap;

import foodrev.org.foodrev.domain.infos.DownloadBitmapTask;
import foodrev.org.foodrev.domain.infos.PostDownloadBitmap;


public class Destination implements PostDownloadBitmap {
    private String mName = null;
    private String mAddress = null;
    private String mImageURL = null;
    private String mPhoneNumber = null;
    protected Bitmap mImage = null;

    public Destination() {
    }

    public String getName() {
        return mName;
    }

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
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public void copy(Destination otherDestination) {
        this.mAddress = otherDestination.mAddress;
        this.mImageURL = otherDestination.mImageURL;
        this.mName = otherDestination.mName;
        this.mPhoneNumber = otherDestination.mPhoneNumber;
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

}
