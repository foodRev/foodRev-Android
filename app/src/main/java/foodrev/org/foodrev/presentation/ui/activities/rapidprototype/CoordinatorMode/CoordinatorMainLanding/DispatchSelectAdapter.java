package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.CoordinatorMainLanding;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.Dispatch;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.DispatchCreationActivity;

public class DispatchSelectAdapter extends
        RecyclerView.Adapter<DispatchSelectAdapter.ViewHolder> {

    // member variable for dispatches
    private ArrayList<Dispatch> dispatches;

    // context to facilitate access
    private Context context;

    //pass in the contact array into constructor

    public DispatchSelectAdapter(Context context, ArrayList<Dispatch> dispatches) {
        this.dispatches = dispatches;
        this.context = context;
    }

    @Override
    public DispatchSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // inflate custom layout
        View dispatchView = inflater.inflate(R.layout.dispatch_select_item, parent, false);

        // return new holder instance
        ViewHolder viewHolder = new ViewHolder(dispatchView);
        return viewHolder;
    }

    // Provide a direct reference to each of the view within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView dispatchIcon;
        public TextView dispatchDescription;
        public CardView dispatchCardView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View dispatchItemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(dispatchItemView);

            dispatchIcon = (ImageView) dispatchItemView.findViewById(R.id.dispatch_select_icon);
            dispatchDescription = (TextView) dispatchItemView.findViewById(R.id.dispatch_description_left);
            dispatchCardView = (CardView) dispatchItemView.findViewById(R.id.dispatch_select_card_view);
        }

    }


    // populate data in the item through the holder
    @Override
    public void onBindViewHolder(DispatchSelectAdapter.ViewHolder viewHolder, int position) {
        // get the data model based on position
        Dispatch dispatch = dispatches.get(position);

        final String dispatchId = dispatch.getDispatchId();

        // set item views based on your views and data model

        // set description
        TextView dispatchDescription = viewHolder.dispatchDescription;
        dispatchDescription.setText(dispatch.getDispatchDate() + " " + dispatch.getDispatchStartTime());

        // set dispatch icon
        ImageView dispatchIcon = viewHolder.dispatchIcon;
        dispatchIcon.setImageResource(R.drawable.ic_dispatch);

        // set card color based on information input
        final CardView dispatchCardView = viewHolder.dispatchCardView;

        if (dispatch.getDispatchStatus() == Dispatch.DispatchStatus.NEED_TO_PLAN) {
            dispatchCardView.setCardBackgroundColor(Color.YELLOW);
        } else {
            dispatchCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cardview_light_background));
        }

        dispatchCardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DispatchCreationActivity.class);
                intent.putExtra("dispatch_key", dispatchId);
                getContext().startActivity(intent);
            }
        });

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

    //smaller methods
    private Context getContext() {
        return this.context;
    }

}
