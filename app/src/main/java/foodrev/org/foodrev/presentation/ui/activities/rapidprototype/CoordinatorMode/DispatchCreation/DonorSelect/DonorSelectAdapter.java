package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.DonorSelect;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
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


/**
 * Created by dastechlabs on 3/21/17.
 */

public class DonorSelectAdapter extends
    RecyclerView.Adapter<DonorSelectAdapter.ViewHolder>{

    private ArrayList<DispatchDonor> dispatchDonors;
    private Context context;


    // define viewholder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView donorIcon;
        public TextView donorNameTextView;
        public TextView donorCarsOfFood;
        public CardView donorCardView;

        public ViewHolder(View donorItemView) {
            super(donorItemView);

            donorIcon = (ImageView) donorItemView.findViewById(R.id.donor_select_icon);
            donorNameTextView = (TextView) donorItemView.findViewById(R.id.donor_select_name);
            donorCarsOfFood = (TextView) donorItemView.findViewById(R.id.donor_select_cars_of_food);
            donorCardView = (CardView) donorItemView.findViewById(R.id.dispatch_donor_select_card_view);

        }
    }

    // define how to put these into local array
    public DonorSelectAdapter(Context context, ArrayList<DispatchDonor> dispatchDonors) {
        this.context = context;
        this.dispatchDonors = dispatchDonors;
    }


    // inflates layout from xml and returns the view holder
    @Override
    public DonorSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View dispatchDonorView = inflater.inflate(R.layout.dispatch_donor_select_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(dispatchDonorView);
        return viewHolder;
    }

    // populate values into item through holder
    @Override
    public void onBindViewHolder(final DonorSelectAdapter.ViewHolder viewHolder, int position) {
        final DispatchDonor dispatchDonor = dispatchDonors.get(position);

        // set item views based on xml and model

        // set donor name
        TextView donorNameView = viewHolder.donorNameTextView;
        donorNameView.setText(dispatchDonor.getDonorName());

        // set donor donation amount
        TextView donorDonationAmountView = viewHolder.donorCarsOfFood;
        donorDonationAmountView.setText(String.valueOf(dispatchDonor.getCarsOfFood()));

        // set icon view
        // TODO create different icons for different donors based on url
        ImageView donorIcon = viewHolder.donorIcon;
        donorIcon.setImageResource(R.drawable.ic_donor);

        // set card onclicklistener
        final CardView donorCardView = viewHolder.donorCardView;
        if (dispatchDonor.isSelected()) {
            donorCardView.setCardBackgroundColor(Color.CYAN);
        } else {
            donorCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cardview_light_background));
        }
        donorCardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchDonor.setSelected(!dispatchDonor.isSelected());
                if (dispatchDonor.isSelected()) {
                    donorCardView.setCardBackgroundColor(Color.CYAN);
                } else {
                    donorCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cardview_light_background));
                }

            }

        });

    }

    // necessary, returns total count of items in list
    @Override
    public int getItemCount() {
        return dispatchDonors.size();
    }


}
