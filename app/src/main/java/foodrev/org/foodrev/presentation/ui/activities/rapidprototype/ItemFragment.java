package foodrev.org.foodrev.presentation.ui.activities.rapidprototype;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.dummy.DummyContent;
import foodrev.org.foodrev.domain.dummy.DummyContentCare;
import foodrev.org.foodrev.domain.dummy.DummyContentCommunityCenter;
import foodrev.org.foodrev.domain.dummy.DummyContentDispatch;
import foodrev.org.foodrev.domain.dummy.DummyContentDonor;
import foodrev.org.foodrev.domain.dummy.DummyContentDriver;

import static foodrev.org.foodrev.domain.dummy.DummyContent.CARE_TITLE;
import static foodrev.org.foodrev.domain.dummy.DummyContent.COMMUNITY_CENTER_TITLE;
import static foodrev.org.foodrev.domain.dummy.DummyContent.DONOR_TITLE;
import static foodrev.org.foodrev.domain.dummy.DummyContent.DRIVER_TITLE;

//import foodrev.org.rapidprototype.dummy.DummyContent.DummyItem;
//import foodrev.org.rapidprototype.dummy.DummyContentCare;
//import foodrev.org.rapidprototype.dummy.DummyContentCommunityCenter;
//import foodrev.org.rapidprototype.dummy.DummyContentDonor;
//import foodrev.org.rapidprototype.dummy.DummyContentDriver;
//
//import static foodrev.org.rapidprototype.dummy.DummyContent.CARE_TITLE;
//import static foodrev.org.rapidprototype.dummy.DummyContent.COMMUNITY_CENTER_TITLE;
//import static foodrev.org.rapidprototype.dummy.DummyContent.DONOR_TITLE;
//import static foodrev.org.rapidprototype.dummy.DummyContent.DRIVER_TITLE;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String mContent;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(String content) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);

            mContent = (String) getArguments().getSerializable("content");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            switch (mContent) {
                case DRIVER_TITLE:
                    recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContentDriver.ITEMS, mListener));
                    break;
                case DONOR_TITLE:
                    recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContentDonor.ITEMS, mListener));
                    break;
                case COMMUNITY_CENTER_TITLE:
                    recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContentCommunityCenter.ITEMS, mListener));
                    break;
                case CARE_TITLE:
                    recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContentCare.ITEMS, mListener));
                    break;
                case "Dispatches":
                    recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContentDispatch.ITEMS, mListener));
                    break;
            }
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyContent.DummyItem item);
    }
}
