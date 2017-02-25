package foodrev.org.foodrev.domain.infos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.InputStream;

import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.MyItemRecyclerViewAdapter;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.json.MyJsonAdapter;

/**
 * Created by heavenhuang on 10/1/15.
 */

// TODO: implement as interactor or remove android dependencies
public class DownloadBitmapTask extends AsyncTask<Void, Void, Bitmap> {
    private String mURI = null;
    private PostDownloadBitmap mPostDownloadBitmap = null;
    private MyItemRecyclerViewAdapter.ViewHolder mHolder;

    public DownloadBitmapTask(MyItemRecyclerViewAdapter.ViewHolder holder, String uri, PostDownloadBitmap postTaskBitmap) {
        this.mHolder = holder;
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
        if (bm != null) {
            mHolder.mItemImage.setImageBitmap(bm);
        }
    }
};
