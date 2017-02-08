package foodrev.org.foodrev.domain.models;

import android.graphics.Bitmap;

import org.foodrev.www.foodrev_android_coordinator_app.Interfaces.PostDownloadBitmap;
import org.foodrev.www.foodrev_android_coordinator_app.Tasks.DownloadBitmapTask;

public class CommunityCenter implements PostDownloadBitmap {
    private String mName = null;
    private String mAddress = null;
    private String mImageURL = null;
    private String mPhoneNumber = null;
    private Bitmap mImage = null;

    public CommunityCenter() {
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

    public void copy(CommunityCenter otherCommunityCenter) {
        this.mAddress = otherCommunityCenter.mAddress;
        this.mImageURL = otherCommunityCenter.mImageURL;
        this.mName = otherCommunityCenter.mName;
        this.mPhoneNumber = otherCommunityCenter.mPhoneNumber;
    }

    public static CommunityCenter getNewCommunityCenter() {
        CommunityCenter communityCenter = new CommunityCenter();
        communityCenter.setName("Name");
        communityCenter.setImageURL("http://abc.com/img.png");
        communityCenter.setPhoneNumber("000-000-0000");
        communityCenter.setAddress("Address");
        return communityCenter;
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
