package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.dummy.DummyContent.DummyItem;
import foodrev.org.foodrev.domain.dummy.DummyContentDispatch;

public class DispatchViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<DummyContentDispatch.DummyItemDispatch> mValues;
    private final DispatchItemFragment.OnListFragmentInteractionListener mListener;

    public DispatchViewAdapter(List<DummyContentDispatch.DummyItemDispatch> items, DispatchItemFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);

                    Intent intent = new Intent(v.getContext(), DispatchDetailItemActivity.class);

                    intent.putExtra("item", holder.mItem);

                    v.getContext().startActivity(intent);
                }
            }
        });
    }

//    @Override
//    public int getItemViewType(int position) {
//        // Just as an example, return 0 or 2 depending on position
//        // Note that unlike in ListView adapters, types don't have to be contiguous
//        return position % 2 * 2;
//    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
//        public final TextView mIdView;
        public final ImageView mItemImage;
        public final TextView mContentView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.id);
            mItemImage = (ImageView) view.findViewById(R.id.item_image);
            mItemImage.setImageResource(R.drawable.ic_dispatch);
            mContentView = (TextView) view.findViewById(R.id.dummy_content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
