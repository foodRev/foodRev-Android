package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverDelegation.DriverAssignment;

import android.content.Context;
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
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDriver;

/**
 * Created by dastechlabs on 4/10/17.
 */

public class DriverAssignmentAdapter extends
    RecyclerView.Adapter<DriverAssignmentAdapter.ViewHolder>{

        private ArrayList<DispatchDriver> dispatchDrivers;
        private Context context;

        // define viewholder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView driverIcon;
            public TextView driverNameTextView;
            public TextView driverStandardTrunkCapacityOfFood;
            public CardView driverCardView;

            public ViewHolder(View driverItemView) {
                super(driverItemView);

                driverIcon = (ImageView) driverItemView.findViewById(R.id.driver_select_icon);
                driverNameTextView = (TextView) driverItemView.findViewById(R.id.driver_select_name);
                driverStandardTrunkCapacityOfFood = (TextView) driverItemView.findViewById(R.id.driver_select_cars_of_food);
                driverCardView = (CardView) driverItemView.findViewById(R.id.dispatch_driver_select_card_view);
            }
        }

        // define how to put these into an array

    public DriverAssignmentAdapter(Context context, ArrayList<DispatchDriver> dispatchDrivers) {
            this.context = context;
            this.dispatchDrivers = dispatchDrivers;
        }

        // inflate layout from xml and return viewHolder
        @Override
        public DriverAssignmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View dispatchDriverView = inflater.inflate(R.layout.dispatch_driver_select_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(dispatchDriverView);
            return viewHolder;
        }


    // populate values into item through view holder
        @Override
        public void onBindViewHolder(final DriverAssignmentAdapter.ViewHolder viewHolder, int position) {
            final DispatchDriver dispatchDriver = dispatchDrivers.get(position);

            // set item views based on xml and model

            // set driver name
            TextView driverNameView = viewHolder.driverNameTextView;
            driverNameView.setText(dispatchDriver.getDriverName());

            // set driver car capacity (relative to an empty sedan trunk capacity)
            TextView driverCapacityView = viewHolder.driverStandardTrunkCapacityOfFood;
            driverCapacityView.setText(String.valueOf(dispatchDriver.getVehicleFoodCapacity()));

            // set icon view
            // TODO set driver icons based on some general icon (possibly facebook or gmail)
            ImageView driverIcon = viewHolder.driverIcon;
            driverIcon.setImageResource(R.drawable.ic_driver);

            // set card onClickListener
            final CardView driverCardView = viewHolder.driverCardView;
            if (dispatchDriver.isSelected()) {
                driverCardView.setCardBackgroundColor(Color.CYAN);
            } else {
                driverCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cardview_light_background));
            }

            driverCardView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchDriver.setSelected(!dispatchDriver.isSelected());
                    if (dispatchDriver.isSelected()) {
                        driverCardView.setCardBackgroundColor(Color.CYAN);
                    } else {
                        driverCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cardview_light_background));
                    }
                }
            });
        }

        // necessary to override
        @Override
        public int getItemCount() {
            return dispatchDrivers.size();
        }
}

