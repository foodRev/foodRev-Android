package foodrev.org.foodrev.domain.infos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by heavenhuang on 10/1/15.
 */

// TODO: implement as interactor or remove android dependencies
public class DownloadBitmapTask extends AsyncTask<Void, Void, Bitmap> {
    private String mURI = null;
    private PostDownloadBitmap mPostDownloadBitmap = null;

    public DownloadBitmapTask(String uri, PostDownloadBitmap postTaskBitmap) {
        this.mURI = uri;
        this.mPostDownloadBitmap = postTaskBitmap;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            InputStream in = new java.net.URL(mURI).openStream();
            return BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("foodrev", "DownloadBitmapTask error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bm) {
        mPostDownloadBitmap.DownloadBitmapDone(bm);
    }
};
