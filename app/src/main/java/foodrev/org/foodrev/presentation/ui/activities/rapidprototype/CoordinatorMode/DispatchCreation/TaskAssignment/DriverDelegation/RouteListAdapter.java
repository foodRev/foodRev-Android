package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverDelegation;

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
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;
import foodrev.org.foodrev.domain.models.dispatchModels.DonorToCommunityPair;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DonorCommunityPairCreation.CommunityAllocation.CommunityAllocationList;

/**
 * Created by dastechlabs on 5/24/17.
 */

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.ViewHolder> {

        private ArrayList<DonorToCommunityPair> dispatchRoutes;
        private Context context;

        // define viewholder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView routeIcon;
            public TextView routeTextView;
            public TextView routeTotalCarsOfFood;
            public TextView routeAllocatedCarsOfFood;
            public CardView routeCardView;

            public ViewHolder(View routeItemView) {
                super(routeItemView);

                routeIcon = (ImageView) routeItemView.findViewById(R.id.route_select_icon);
                routeTextView = (TextView) routeItemView.findViewById(R.id.route_select_name);
                routeTotalCarsOfFood = (TextView) routeItemView.findViewById(R.id.route_select_donation_food_capacity);
                routeAllocatedCarsOfFood = (TextView) routeItemView.findViewById(R.id.route_select_donation_allocated_total);
                routeCardView = (CardView) routeItemView.findViewById(R.id.route_card_view);

            }
        }

        // define how to put these into local array
        public RouteListAdapter(Context context, ArrayList<DispatchDonor> dispatchDonors) {
            this.context = context;
            this.dispatchRoutes = dispatchRoutes;
        }


        // inflates layout from xml and returns the view holder
        @Override
        public RouteListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View dispatchRouteView = inflater.inflate(R.layout.route_select_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(dispatchRouteView);
            return viewHolder;
        }

        // populate values into item through holder
        @Override
        public void onBindViewHolder(final RouteListAdapter.ViewHolder viewHolder, int position) {
            final DonorToCommunityPair dispatchRoute = dispatchRoutes.get(position);

            // set item views based on xml and model

            // set donor name
            TextView routeNameView = viewHolder.routeTextView;
            routeNameView.setText(dispatchRoute.getDispatchDonor().getDonorName()
                    + " to "
                    + dispatchRoute.getDispatchCommunity().getCommunityName() );
            //donorNameView.setText(dispatchRoute.getDispatchCommunity());

            // set total amount
            TextView routeAmountView = viewHolder.routeTotalCarsOfFood;
            routeAmountView.setText(String.valueOf(dispatchRoute.getTrunksOfFood()));

            // set total amount
            // TODO change to display allocation
            TextView routeFoodAllocationCountView = viewHolder.routeAllocatedCarsOfFood;
            routeFoodAllocationCountView.setText(String.valueOf(dispatchRoute.getTrunksOfFood()));

            // set icon view
            // TODO create different icons for different donors based on url
            ImageView routeIcon = viewHolder.routeIcon;
            routeIcon.setImageResource(R.drawable.ic_next);

            // set card onclicklistener
            final CardView routeCardView = viewHolder.routeCardView;

            // TODO: add onclick listener

        }


        // necessary, returns total count of items in list
        @Override
        public int getItemCount() {
            return dispatchRoutes.size();
        }


    }
