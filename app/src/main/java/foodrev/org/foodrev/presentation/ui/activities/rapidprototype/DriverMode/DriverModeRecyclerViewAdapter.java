package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.DriverMode;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import foodrev.org.foodrev.R;

/**
 * Created by darver on 3/25/17.
 */

public class DriverModeRecyclerViewAdapter extends RecyclerView.Adapter<DriverModeRecyclerViewAdapter.ViewHolder> {
    private ArrayList<DriverTask> mTaskList;
//    private String mDriverName = "Phillip";
    private String mTaskType;
    private String mDonationSource;
    private String mDonationDestination;
    private int mStepNum;

    ArrayList<CheckBox> mCheckBoxes;
    ArrayList<RelativeLayout> mRelativeLayouts;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        CheckBox mCheckBox;
        CardView mCardView;
        RelativeLayout mRelativeLayout;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.driver_view);
            mCheckBox = (CheckBox) v.findViewById(R.id.checkBox);
            mCardView = (CardView) v.findViewById(R.id.json_card_view);
            mRelativeLayout = (RelativeLayout) v.findViewById(R.id.card_content);
        }
    }

    //provide suitable constructor, must change with dataset
    public DriverModeRecyclerViewAdapter(ArrayList<DriverTask> TaskList) {
        mTaskList = TaskList;

        mCheckBoxes = new ArrayList<>();
        mRelativeLayouts = new ArrayList<>();
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mStepNum = position+1;

        DriverTask task = mTaskList.get(position);
        mTaskType = task.taskType;
        mDonationSource = task.donationSource;
        mDonationDestination = task.donationDestination;

        String taskString = String.format("Step %d: %s ", position + 1, mTaskType);
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
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                checkBox.setText(checkBox.isChecked()? "Completed": "");
                
                DriverModeActivity activity = (DriverModeActivity) v.getContext();
                activity.check(position, mCheckBoxes);

                if (holder.mCheckBox.isChecked() && position < mCheckBoxes.size()-1) {
                    holder.mRelativeLayout.setVisibility(View.GONE);

                    mRelativeLayouts.get(position+1).setVisibility(View.VISIBLE);
                }

            }
        });

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mRelativeLayout.setVisibility(holder.mRelativeLayout.getVisibility() == View.VISIBLE ? View.GONE : View
                        .VISIBLE);
                for (int i = 0; i < mRelativeLayouts.size(); i++) {
                    if (holder.mRelativeLayout.getVisibility() == View.VISIBLE && i != position) {
                        mRelativeLayouts.get(i).setVisibility(View.GONE);
                    }
                }
            }
        });

        if (position > 0) {
            holder.mCheckBox.setEnabled(false);
            holder.mRelativeLayout.setVisibility(View.GONE);
        }

        mCheckBoxes.add(holder.mCheckBox);
        mRelativeLayouts.add(holder.mRelativeLayout);
    }

    //returns size of dataset (invoked by layout manager)
    @Override
    public int getItemCount() {
        return mTaskList.size();
    }
}