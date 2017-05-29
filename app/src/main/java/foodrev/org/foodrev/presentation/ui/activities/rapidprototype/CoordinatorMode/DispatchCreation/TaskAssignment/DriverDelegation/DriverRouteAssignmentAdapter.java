package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverDelegation;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDriver;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.AbstractAndroidEntities.AbstractRecyclerViewAdapter;

/**
 * Created by foodRev on 5/27/17.
 */

public class DriverRouteAssignmentAdapter extends AbstractRecyclerViewAdapter<DispatchDriver>{
    public DriverRouteAssignmentAdapter(Context context, ArrayList<DispatchDriver> mList) {
       super(context,mList);
    }

    @Override
    public void setCardIcon(DispatchDriver mListItem, ImageView cardIcon) {

    }

    @Override
    public void setTextViews(DispatchDriver mListItem, TextView[] textViews, TextView[] textViewLabels) {
        // set title to be driver name
        textViews[0].setText(mListItem.getDriverName());

        // set second field to number of trips assigned
        textViews[1].setText(String.valueOf(mListItem.getTotalTripsAssignedInDispatch()));

        // set third field to be how much as been allocated to this route;
        textViews[2].setText(String.valueOf(mListItem.getTripsAssignedForThisRoute()));
    }

    @Override
    public void setCardViewListener(DispatchDriver mListItem, CardView cardView) {

    }
}

