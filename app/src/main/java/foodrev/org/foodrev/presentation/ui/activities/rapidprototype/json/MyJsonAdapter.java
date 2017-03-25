package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.json;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import foodrev.org.foodrev.R;

/**
 * Created by magulo on 8/25/16.
 */
public class MyJsonAdapter extends RecyclerView.Adapter<MyJsonAdapter.ViewHolder> {
    private String[] mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.json_view);
        }
    }

    //provide suitable constructor, must change with dataset
    public MyJsonAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    //create new views (used by layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //create new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.json_view, parent, false);
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
