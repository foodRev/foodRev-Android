package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.DriverMode;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import foodrev.org.foodrev.R;

/**
 * Created by darver on 3/25/17.
 */

public class DriverModeRecyclerViewAdapter extends RecyclerView.Adapter<DriverModeRecyclerViewAdapter.ViewHolder> {
    private String[] mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.driver_view);
        }
    }

    //provide suitable constructor, must change with dataset
    public DriverModeRecyclerViewAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    //create new views (used by layout manager)
    @Override
    public DriverModeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //create new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.driver_view, parent, false);
        //set view's size margins padding and layout parameters

        //... TODO Above

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }


    //replace contents of a view (this is invoked by layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get element from your dataset here
        // replace contents of the view with that element
        holder.mTextView.setText(mDataset[position]);
    }

    //returns size of dataset (invoked by layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}