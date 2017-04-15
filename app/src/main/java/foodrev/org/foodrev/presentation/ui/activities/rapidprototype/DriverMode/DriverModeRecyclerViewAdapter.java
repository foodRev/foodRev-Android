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
    private java.util.ArrayList<DriverTask> mDataset;
//    private String mDriverName = "Phillip";
    private String mTaskType;
    private String mDonationSource;
    private String mDonationDestination;
    private int mStepNum = 0;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.driver_view);
        }
    }

    //provide suitable constructor, must change with dataset
    public DriverModeRecyclerViewAdapter(java.util.ArrayList<DriverTask> myDataset) {
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
        mStepNum = position+1;

        DriverTask task = mDataset.get(position);
        mTaskType = task.taskType;
        mDonationSource = task.donationSource;
        mDonationDestination = task.donationDestination;

        String taskString = String.format("Step %d: %s ", mStepNum, mTaskType);
        switch (mTaskType) {
            case "Drive":
                taskString += String.format("to %s",
                        mDonationSource.isEmpty() ? mDonationDestination : mDonationSource);
                break;
            case "Load":
                taskString += String.format("from %s", mDonationSource);
                break;
            case "Unload":
                taskString += String.format("to %s", mDonationDestination);
                break;
            default:
        }

        holder.mTextView.setText(taskString);
    }

    //returns size of dataset (invoked by layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}