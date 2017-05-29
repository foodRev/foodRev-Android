package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverDelegation;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import foodrev.org.foodrev.domain.models.dispatchModels.DispatchRoute;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.AbstractAndroidEntities.AbstractRecyclerViewAdapter;

/**
 * Created by foodRev on 5/24/17.
 */

public class RouteListAdapter extends AbstractRecyclerViewAdapter<DispatchRoute>{

    public RouteListAdapter(Context context, ArrayList<DispatchRoute> mList){
        super(context,mList);
    }

    @Override
    public void setCardIcon(DispatchRoute mListItem, ImageView cardIcon) {

    }

    @Override
    public void setTextViews(DispatchRoute mListItem, TextView[] textViews, TextView[] textViewLabels) {

        // set route name
        textViews[0].setText(mListItem.getDonorName()
                + " to "
                + mListItem.getCommunityName() );

        // set total required drivers
        textViews[1].setText(String.valueOf(mListItem.getCarsRequired()));

        // additional drivers still needed
        textViews[2].setText(String.valueOf(mListItem.getCarsRequired() - mListItem.getCarsAssigned()));
    }

    @Override
    public void setCardViewListener(DispatchRoute mListItem, CardView cardView) {

    }
}
