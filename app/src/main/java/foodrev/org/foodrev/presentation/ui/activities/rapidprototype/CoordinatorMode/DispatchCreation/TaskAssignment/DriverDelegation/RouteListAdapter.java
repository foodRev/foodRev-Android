package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverDelegation;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchRoute;
import foodrev.org.foodrev.domain.models.dispatchModels.DonorToCommunityPair;

/**
 * Created by dastechlabs on 5/24/17.
 */

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.ViewHolder> {

        private ArrayList<DispatchRoute> dispatchRoutes;
        private Context context;

        // define viewholder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView routeIcon;
            public TextView routeTextView;
            public TextView routeCarsRequired;
            public TextView routeAdditionalCarsNeeded;
            public CardView routeCardView;

            public ViewHolder(View routeItemView) {
                super(routeItemView);

                routeIcon = (ImageView) routeItemView.findViewById(R.id.route_select_icon);
                routeTextView = (TextView) routeItemView.findViewById(R.id.route_select_name);
                routeCarsRequired = (TextView) routeItemView.findViewById(R.id.route_cars_required);
                routeAdditionalCarsNeeded = (TextView) routeItemView.findViewById(R.id.route_cars_additional_needed);
                routeCardView = (CardView) routeItemView.findViewById(R.id.route_card_view);

            }
        }

        // define how to put these into local array
        public RouteListAdapter(Context context, ArrayList<DispatchRoute> dispatchRoutes) {
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
            final DispatchRoute dispatchRoute = dispatchRoutes.get(position);

            // set item views based on xml and model

            // set donor name
            TextView routeNameView = viewHolder.routeTextView;
            routeNameView.setText(dispatchRoute.getDonorName()
                    + " to "
                    + dispatchRoute.getCommunityName() );
            //donorNameView.setText(dispatchRoute.getDispatchCommunity());

            // set total amount
            TextView routeCarsRequired = viewHolder.routeCarsRequired;
            routeCarsRequired.setText(String.valueOf(dispatchRoute.getCarsRequired()));

            // set total amount
            TextView routeAdditonalCarsNeeded = viewHolder.routeAdditionalCarsNeeded;
            routeAdditonalCarsNeeded.setText(String.valueOf(dispatchRoute.getCarsRequired() - dispatchRoute.getCarsAssigned()));

            // TODO: add onclick listener
            // set card onclicklistener
            final CardView routeCardView = viewHolder.routeCardView;


        }


        // necessary, returns total count of items in list
        @Override
        public int getItemCount() {
            return dispatchRoutes.size();
        }


    }
