package foodrev.org.foodrev.domain.infos.models;

import android.graphics.Bitmap;

import foodrev.org.foodrev.domain.infos.DestinationInfo;
import foodrev.org.foodrev.domain.infos.DownloadBitmapTask;
import foodrev.org.foodrev.domain.infos.PostDownloadBitmap;


public class CommunityCenter extends Destination implements PostDownloadBitmap {
    public static CommunityCenter getNewCommunityCenter() {
        CommunityCenter communityCenter = new CommunityCenter();
        communityCenter.setName("Name");
        communityCenter.setImageURL("http://abc.com/img.png");
        communityCenter.setPhoneNumber("000-000-0000");
        communityCenter.setAddress("Address");
        return communityCenter;
    }


}
