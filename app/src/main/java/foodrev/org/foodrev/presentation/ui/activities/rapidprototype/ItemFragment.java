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

import foodrev.org.foodrev.App;
import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.infos.AbstractInfo;
import foodrev.org.foodrev.domain.infos.PopulateInfos;
import foodrev.org.foodrev.domain.infos.models.AbstractModel;

import static foodrev.org.foodrev.domain.infos.AbstractInfo.CARE_TITLE;
import static foodrev.org.foodrev.domain.infos.AbstractInfo.COMMUNITY_CENTER_TITLE;
import static foodrev.org.foodrev.domain.infos.AbstractInfo.DONOR_TITLE;
import static foodrev.org.foodrev.domain.infos.AbstractInfo.DRIVER_TITLE;

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
    private String mContentType;
    private AbstractInfo mContent;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

//    // TODO: Customize parameter initialization
//    @SuppressWarnings("unused")
//    public static ItemFragment newInstance(int columnCount) {
//        ItemFragment fragment = new ItemFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
//        return fragment;
//    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(String content) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        fragment.setArguments(args);
        return fragment;
    }

//    // TODO: Customize parameter initialization
//    @SuppressWarnings("unused")
//    public static ItemFragment newInstance(AbstractInfo content) {
//        ItemFragment fragment = new ItemFragment();
//        Bundle args = new Bundle();
//        args.putSerializable("content", content);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);

            mContentType = (getArguments().getString("content"));

            App app = (App) getActivity().getApplicationContext();

            if (mContentType != null) {
                switch (mContentType) {
                    case DRIVER_TITLE:
                        mContent = app.getDriverInfo();
                        break;
                    case DONOR_TITLE:
                        mContent = app.getDonorInfo();
                        break;
                    case COMMUNITY_CENTER_TITLE:
                        mContent = app.getCcInfo();
                        break;
                    case CARE_TITLE:
                        mContent = app.getCareInfo();
                        break;
                }
            }
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

            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(mContent,
                    mListener));

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
        void onListFragmentInteraction(AbstractModel item);
    }
}
