package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverDelegation;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;
import foodrev.org.foodrev.domain.models.dispatchModels.DonorToCommunityPair;

/**
 * Created by dastechlabs on 5/24/17.
 */

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.ViewHolder> {

        private ArrayList<DonorToCommunityPair> dispatchDCPair;
        private Context context;

        // define viewholder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView donorCommunityIcon;
            public TextView donorCommunityTextView;
            public TextView donorCommunityTotalCarsOfFood;
            public TextView donorCommunityAllocatedCarsOfFood;
            public CardView donorCommunityCardView;

            public ViewHolder(View donorCommunityItemView) {
                super(donorCommunityItemView);

                donorCommunityIcon = (ImageView) donorCommunityItemView.findViewById(R.id.route_select_icon);
                donorCommunityTextView = (TextView) donorCommunityItemView.findViewById(R.id.route_select_name);
                donorCommunityTotalCarsOfFood = (TextView) donorCommunityItemView.findViewById(R.id.route_total_cars_of_food);
                donorCommunityAllocatedCarsOfFood = (TextView) donorCommunityItemView.findViewById(R.id.route_allocated_cars_of_food);
                donorCommunityCardView = (CardView) donorCommunityItemView.findViewById(R.id.dispatch_route_select_card_view);

            }
        }

        // define how to put these into local array
        public DonorCommunityPairSelectAdapter(Context context, ArrayList<DispatchDonor> dispatchDonors) {
            this.context = context;
            this.dispatchDonors = dispatchDonors;
        }


        // inflates layout from xml and returns the view holder
        @Override
        public DonorCommunityPairSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View dispatchDonorView = inflater.inflate(R.layout.donor_community_pair_select_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(dispatchDonorView);
            return viewHolder;
        }

        // populate values into item through holder
        @Override
        public void onBindViewHolder(final DonorCommunityPairSelectAdapter.ViewHolder viewHolder, int position) {
            final DispatchDonor dispatchDonor = dispatchDonors.get(position);

            // set item views based on xml and model

            // set donor name
            TextView donorNameView = viewHolder.donorNameTextView;
            donorNameView.setText(dispatchDonor.getDonorName());

            // set total amount
            TextView donorDonationAmountView = viewHolder.donorTotalCarsOfFood;
            donorDonationAmountView.setText(String.valueOf(dispatchDonor.getCarsOfFood()));

            // set total amount
            TextView donorFoodAllocationCountView = viewHolder.donorAllocatedCarsOfFood;
            donorFoodAllocationCountView.setText(String.valueOf(dispatchDonor.getAllocatedCarsOfFood()));

            // set icon view
            // TODO create different icons for different donors based on url
            ImageView donorIcon = viewHolder.donorIcon;
            donorIcon.setImageResource(R.drawable.ic_donor);

            // set card onclicklistener
            final CardView donorCardView = viewHolder.donorCardView;

            highlightIfExistsUnallocatedFood(donorCardView, dispatchDonor);

            donorCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CommunityAllocationList.class);
                    intent.putExtra("dispatch_key", dispatchDonor.getDispatchKey());
                    intent.putExtra("donor_key", dispatchDonor.getDonorUid());
                    intent.putExtra("donor_name", dispatchDonor.getDonorName());
                    intent.putExtra("donor_total_food", String.valueOf(dispatchDonor.getCarsOfFood()));
                    context.startActivity(intent);
                }
            });

        }

        private void highlightIfExistsUnallocatedFood(CardView donorCardView, DispatchDonor dispatchDonor) {
            if (dispatchDonor.getAllocatedCarsOfFood() < dispatchDonor.getCarsOfFood()) {
                donorCardView.setCardBackgroundColor(Color.CYAN);
            } else {
                donorCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cardview_light_background));
            }
        }

        // necessary, returns total count of items in list
        @Override
        public int getItemCount() {
            return dispatchDonors.size();
        }


    }
}
