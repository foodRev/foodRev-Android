package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.CommunitySelect;

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
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchCommunity;

/**
 * Created by dastechlabs on 3/23/17.
 */

public class CommunitySelectAdapter extends
    RecyclerView.Adapter<CommunitySelectAdapter.ViewHolder>{

    private ArrayList<DispatchCommunity> dispatchCommunities;
    private Context context;

    // define viewHolder which connects xml with its attributes
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView communityIcon;
        public TextView communityNameTextView;
        public TextView communityDonationFoodCapacity;
        public CardView communityCardView;

        public ViewHolder(View communityItemView) {
            super(communityItemView);

            communityIcon = (ImageView) communityItemView.findViewById(R.id.community_select_icon);
            communityNameTextView = (TextView) communityItemView.findViewById(R.id.community_select_name);
            communityDonationFoodCapacity = (TextView) communityItemView.findViewById(R.id.community_select_donation_food_capacity);
            communityCardView = (CardView) communityItemView.findViewById(R.id.dispatch_community_select_card_view);
        }
    }


    // set local array from input objects
    public CommunitySelectAdapter(Context context, ArrayList<DispatchCommunity> dispatchCommunities){
        this.context = context;
        this.dispatchCommunities = dispatchCommunities;
    }


    // inflates layout from xml and returns the view holder
    @Override
    public CommunitySelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View dispatchCommunityView = inflater.inflate(R.layout.dispatch_community_select_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(dispatchCommunityView);
        return viewHolder;
    }


    // populate values into item through holder
    @Override
    public void onBindViewHolder(final CommunitySelectAdapter.ViewHolder viewHolder, int position) {
        // get this particular dispatch community at position so we can do set its properties
        final DispatchCommunity dispatchCommunity = dispatchCommunities.get(position);

        // set item views based on xml and model

        // set community name
        TextView communityNameView = viewHolder.communityNameTextView;
        communityNameView.setText(dispatchCommunity.getName());

        // set community food donation capacity
        TextView communityDonationFoodCapacity = viewHolder.communityDonationFoodCapacity;
        communityDonationFoodCapacity.setText(String.valueOf(dispatchCommunity.getFoodDonationCapacity()));

        // set icon view
        // TODO feature for custom icon per community center for those who provide
        ImageView communityIcon = viewHolder.communityIcon;
        communityIcon.setImageResource(R.drawable.ic_community_destination);

        // set card onClickListener
        final CardView communityCardView = viewHolder.communityCardView;
        if (dispatchCommunity.isSelected()) {
            communityCardView.setCardBackgroundColor(Color.CYAN);
        } else {
            communityCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cardview_light_background));
        }
        communityCardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchCommunity.setSelected(!dispatchCommunity.isSelected());
                if (dispatchCommunity.isSelected()) {
                    communityCardView.setCardBackgroundColor(Color.CYAN);
                } else {
                    communityCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cardview_light_background));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dispatchCommunities.size();
    }
}
