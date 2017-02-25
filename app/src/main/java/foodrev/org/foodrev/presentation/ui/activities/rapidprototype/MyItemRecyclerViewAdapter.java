package foodrev.org.foodrev.presentation.ui.activities.rapidprototype;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import foodrev.org.foodrev.App;
import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.infos.AbstractInfo;
import foodrev.org.foodrev.domain.infos.DownloadBitmapTask;
import foodrev.org.foodrev.domain.infos.DriverInfo;
import foodrev.org.foodrev.domain.infos.PostDownloadBitmap;
import foodrev.org.foodrev.domain.infos.models.AbstractModel;
import foodrev.org.foodrev.domain.infos.models.Care;
import foodrev.org.foodrev.domain.infos.models.CommunityCenter;
import foodrev.org.foodrev.domain.infos.models.Destination;
import foodrev.org.foodrev.domain.infos.models.DonationCenter;
import foodrev.org.foodrev.domain.infos.models.Driver;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.ItemFragment.OnListFragmentInteractionListener;
//import java.util.List;
//
//import foodrev.org.rapidprototype.ItemFragment.OnListFragmentInteractionListener;
//import foodrev.org.rapidprototype.dummy.DummyContent.DummyItem;
//import foodrev.org.rapidprototype.dummy.DummyContentDriver.DummyItemDriver;

/**
 * {@link RecyclerView.Adapter} that can display a {@link AbstractModel} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements PostDownloadBitmap {

    private final AbstractInfo mValues;
    private final OnListFragmentInteractionListener mListener;

    Bitmap mPicture;
    ViewHolder mHolder;

//    public MyItemRecyclerViewAdapter(List<? extends BaseModel> items,
//                                     OnListFragmentInteractionListener listener) {
//        mValues = items;
//        mListener = listener;
//    }

    public MyItemRecyclerViewAdapter(AbstractInfo items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final ViewHolder holder = (ViewHolder) viewHolder;
        mHolder = holder;
        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);

        AbstractModel data = mValues.get(position);

        //TODO: download and cache bitmaps at initialization of model objects
        if (data instanceof Driver) {
            Driver driverItem = (Driver) data;

            DownloadBitmapTask task = new DownloadBitmapTask(holder,driverItem.getPicUrl(),this);

            task.execute();

            holder.mContentView.setText(driverItem.getName());
        } else if (data instanceof Destination) {

            Destination destinationItem = (Destination) data;

            DownloadBitmapTask task = new DownloadBitmapTask(holder,destinationItem.getImageURL(),this);

            task.execute();
            holder.mContentView.setText(((Destination) data).getName());
        } else if (data instanceof Care) {

            Care careItem = (Care) data;

            App app = (App) holder.mView.getContext().getApplicationContext();

            Driver associatedDriver = app.getDriverInfo().getDriver(careItem.getDriverID());

            DownloadBitmapTask task = new DownloadBitmapTask(holder,associatedDriver.getPicUrl(),this);

            task.execute();
            holder.mContentView.setText(((Care) data).getCareTitle());
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

//    @Override
//    public int getItemViewType(int position) {
//        // Just as an example, return 0 or 2 depending on position
//        // Note that unlike in ListView adapters, types don't have to be contiguous
//        return position % 2 * 2;
//    }

    @Override
    public int getItemCount() {
        return mValues != null ? mValues.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
//        public final TextView mIdView;
        public final ImageView mItemImage;
        public final TextView mContentView;
        public AbstractModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.id);
            mItemImage = (ImageView) view.findViewById(R.id.item_image);
            mContentView = (TextView) view.findViewById(R.id.dummy_content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    @Override
    public void DownloadBitmapDone(Bitmap bm) {
        mPicture = bm;
    }
}