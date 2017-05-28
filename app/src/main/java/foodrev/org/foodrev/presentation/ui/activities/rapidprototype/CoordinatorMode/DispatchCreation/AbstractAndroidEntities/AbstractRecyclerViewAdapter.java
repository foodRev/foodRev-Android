package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.AbstractAndroidEntities;

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
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverDelegation.RouteListAdapter;

/**
 * Created by dastechlabs on 5/28/17.
 */

public abstract class AbstractRecyclerViewAdapter<T> extends RecyclerView.Adapter<AbstractRecyclerViewAdapter.ViewHolder> {

        private ArrayList<T> mList;
        private Context context;

        // define viewholder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public CardView cardView;
            public ImageView cardIcon;

            public TextView textView1;
            public TextView textView2;
            public TextView textView3;

            public TextView textView1Label;
            public TextView textView2Label;
            public TextView textView3Label;

            public ViewHolder(View itemView) {
                super(itemView);

                cardView = (CardView)  itemView.findViewById(R.id.three_entry_card_view);
                cardIcon = (ImageView) itemView.findViewById(R.id.three_entry_card_icon);

                textView1 = (TextView) itemView.findViewById(R.id.three_entry_text_view_1);
                textView2 = (TextView) itemView.findViewById(R.id.three_entry_text_view_2);
                textView3 = (TextView) itemView.findViewById(R.id.three_entry_text_view_3);

                textView1Label = (TextView) itemView.findViewById(R.id.three_entry_text_view_1_label);
                textView2Label = (TextView) itemView.findViewById(R.id.three_entry_text_view_2_label);
                textView3Label = (TextView) itemView.findViewById(R.id.three_entry_text_view_3_label);
            }
        }

        // define how to put these into local array
        public AbstractRecyclerViewAdapter(Context context, ArrayList<T> mList) {
            this.context = context;
            this.mList = mList;
        }


        // inflates layout from xml and returns the view holder
        @Override
        public AbstractRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View listItemView = inflater.inflate(R.layout.three_entry_list_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(listItemView);
            return viewHolder;
        }

        // populate values into item through holder
        @Override
        public void onBindViewHolder(final AbstractRecyclerViewAdapter.ViewHolder viewHolder, int position) {
            final T mListItem = mList.get(position);

            // set icon
            ImageView cardIcon = viewHolder.cardIcon;
            setCardIcon(mListItem, cardIcon);



            // set text views
            TextView textView1 = viewHolder.textView1;
            TextView textView2 = viewHolder.textView2;
            TextView textView3 = viewHolder.textView3;
            TextView[] textViews = new TextView[3];

            TextView textView1Label = viewHolder.textView1Label;
            TextView textView2Label = viewHolder.textView2Label;
            TextView textView3Label = viewHolder.textView3Label;
            TextView[] textViewLabels = new TextView[3];

            setTextViews(mListItem, textViews, textViewLabels);


            // set cardView Color and OnClickListeners
            CardView cardView = viewHolder.cardView;
            setCardViewListener(mListItem, cardView);

        }

        public abstract void setCardIcon(T mListItem, ImageView cardIcon);
        public abstract void setTextViews(T mListItem, TextView[] textViews, TextView[] textViewLabels);
        public abstract void setCardViewListener(T mListItem, CardView cardView);

        // necessary, returns total count of items in list
        @Override
        public int getItemCount() {
            return mList.size();
        }


    }
}
