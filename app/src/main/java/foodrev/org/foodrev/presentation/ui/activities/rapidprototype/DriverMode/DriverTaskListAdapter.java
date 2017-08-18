package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.DriverMode;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import foodrev.org.foodrev.domain.models.dispatchModels.driverInstructionModels.DriverTaskListItem;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.AbstractAndroidEntities.AbstractRecyclerViewAdapter;

/**
 * Created by foodRev on 8/17/17.
 */

public class DriverTaskListAdapter extends AbstractRecyclerViewAdapter<DriverTaskListItem> {

    public DriverTaskListAdapter(Context context, ArrayList<DriverTaskListItem> mList){
        super(context,mList);
    }

    @Override
    public void setCardIcon(DriverTaskListItem mListItem, ImageView cardIcon) {

    }

    @Override
    public void setTextViews(DriverTaskListItem mListItem, TextView[] textViews, TextView[] textViewLabels) {

        String headerMessage = "test";

        // set route name
        if(mListItem.getTaskType().equals("LOAD")) {

            // todo refactor to string resources with placeholders
            headerMessage = mListItem.getTaskType()
                    + " "
                    + String.valueOf(mListItem.getAmountToLoad())
                    + " to "
                    + mListItem.getLocationName();
        } else if(mListItem.getTaskType().equals("UNLOAD")) {
            headerMessage = mListItem.getTaskType()
                    + " "
                    + String.valueOf(mListItem.getAmountToUnload())
                    + " to "
                    + mListItem.getLocationName();
        }
        textViews[0].setText(headerMessage);

        // set total required drivers
        textViews[1].setText("test");

        // additional drivers still needed
        textViews[2].setText("test2");
    }

    @Override
    public void setCardViewListener(DriverTaskListItem mListItem, CardView cardView) {

    }
}
