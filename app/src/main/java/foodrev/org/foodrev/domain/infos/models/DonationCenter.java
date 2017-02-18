// Auto-generated file, DO NOT EDIT.
package foodrev.org.foodrev.domain.infos.models;

import android.graphics.Bitmap;

import foodrev.org.foodrev.domain.infos.DownloadBitmapTask;
import foodrev.org.foodrev.domain.infos.PostDownloadBitmap;


public class DonationCenter extends Destination implements PostDownloadBitmap {
    public static DonationCenter getNewDonationCenter() {
        DonationCenter donationCenter = new DonationCenter();
        donationCenter.setName("Name");
        donationCenter.setImageURL("http://abc.com/img.png");
        donationCenter.setPhoneNumber("000-000-0000");
        donationCenter.setAddress("Address");
        return donationCenter;
    }

}
