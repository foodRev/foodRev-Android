package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.DriverMode;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
    ArrayList<TextView> mTextViews;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        CheckBox mCheckBox;
        CardView mCardView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.driver_view);
            mCheckBox = (CheckBox) v.findViewById(R.id.checkBox);
            mCardView = (CardView) v.findViewById(R.id.json_card_view);
        }
    }

    //provide suitable constructor, must change with dataset
    public DriverModeRecyclerViewAdapter(ArrayList<DriverTask> TaskList) {
        mTaskList = TaskList;

        mCheckBoxes = new ArrayList<>();
        mTextViews = new ArrayList<>();
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

//                mCheckBox.setEnabled(false);
                DriverModeActivity activity = (DriverModeActivity) v.getContext();
                activity.check(position, mCheckBoxes);

                if (holder.mCheckBox.isChecked() && position < mCheckBoxes.size()-1) {
                    holder.mTextView.setVisibility(View.GONE);
                    mCheckBoxes.get(position).setVisibility(View.GONE);
                    mCheckBoxes.get(position+1).setVisibility(View.VISIBLE);

                    mTextViews.get(position+1).setVisibility(View.VISIBLE);
                }

            }
        });

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = holder.mTextView;
                CheckBox checkBox = holder.mCheckBox;

                textView.setVisibility(textView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                checkBox.setVisibility(textView.getVisibility());

                for (int i = 0; i < mTextViews.size(); i++) {
                    if (textView.getVisibility() == View.VISIBLE && i != position) {
                        TextView curTextView = mTextViews.get(i);
                        CheckBox curCheckBox = mCheckBoxes.get(i);

                        curTextView.setVisibility(View.GONE);
                        curCheckBox.setVisibility(View.GONE);
                    }
                }
            }
        });

        if (position > 0) {
            holder.mCheckBox.setEnabled(false);
            holder.mCheckBox.setVisibility(View.GONE);
            holder.mTextView.setVisibility(View.GONE);
        }

        mCheckBoxes.add(holder.mCheckBox);
        mTextViews.add(holder.mTextView);
    }

    //returns size of dataset (invoked by layout manager)
    @Override
    public int getItemCount() {
        return mTaskList.size();
    }
}