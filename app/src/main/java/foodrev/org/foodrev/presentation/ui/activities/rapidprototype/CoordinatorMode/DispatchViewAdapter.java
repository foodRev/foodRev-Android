package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.Dispatch;

public class DispatchViewAdapter extends
        RecyclerView.Adapter<DispatchViewAdapter.ViewHolder> {

    // member variable for dispatches
    private ArrayList<Dispatch> dispatches;

    // context to facilitate access
    private Context context;

    //pass in the contact array into constructor

    public DispatchViewAdapter(Context context, ArrayList<Dispatch> dispatches) {
        this.dispatches = dispatches;
        this.context = context;
    }

    private Context getContext() {
        return this.context;
    }

    @Override
    public DispatchViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       Context context = parent.getContext();
       LayoutInflater inflater = LayoutInflater.from(context);

        // inflate custom layout
        // TODO create custom dispatch item
        View dispatchView = inflater.inflate(R.layout.fragment_item, parent, false);

        // return new holder instance
        ViewHolder viewHolder = new ViewHolder(dispatchView);
        return viewHolder;
    }

    // populate data in the item through the holder
    @Override
    public void onBindViewHolder(DispatchViewAdapter.ViewHolder viewHolder, int position) {
        // get the data model based on position
        Dispatch dispatch = dispatches.get(position);

        // set item views based on your views and data model

        // set description
        TextView dispatchDescription = viewHolder.dispatchDescription;
        dispatchDescription.setText(dispatch.getDispatchId() + dispatch.getDispatchDate());

        // set dispatch icon
        ImageView dispatchIcon = viewHolder.dispatchIcon;
        dispatchIcon.setImageResource(R.drawable.ic_dispatch);
   }

    // return total count of items in list
    @Override
    public int getItemCount() {
        if (dispatches == null) {
            return 0;
        } else {
            return dispatches.size();
        }
    }

    // Provide a direct reference to each of the view within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView dispatchIcon;
        public TextView dispatchDescription;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            dispatchIcon = (ImageView) itemView.findViewById(R.id.item_image);
            dispatchDescription = (TextView) itemView.findViewById(R.id.dummy_content);
        }
    }
}
