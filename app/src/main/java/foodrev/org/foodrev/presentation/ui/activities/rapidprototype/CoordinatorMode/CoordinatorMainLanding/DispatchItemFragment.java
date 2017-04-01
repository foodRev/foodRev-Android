package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.CoordinatorMainLanding;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.Dispatch;

import static foodrev.org.foodrev.domain.models.dispatchModels.Dispatch.DispatchStatus.NEED_TO_PLAN;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class DispatchItemFragment extends Fragment {
    //TODO Clean Class

    DispatchSelectAdapter dispatchViewAdapter;

    //Firebase
    private FirebaseDatabase firebaseDatabase;

    // Dispatch Root
    private DatabaseReference dispatchRoot; //driving/unloading/loading

    //ChildEventListener
    private ChildEventListener childEventListener;

    //create custom ordering query to sort results
    private Query dispatchQuery;

    // get firebase user
    FirebaseUser firebaseUser;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String mContent;
    private OnListFragmentInteractionListener mListener;

    ArrayList<Dispatch> dispatches = new ArrayList<Dispatch>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DispatchItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DispatchItemFragment newInstance(int columnCount) {
        DispatchItemFragment fragment = new DispatchItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DispatchItemFragment newInstance(String content) {
        DispatchItemFragment fragment = new DispatchItemFragment();
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
            RecyclerView rvDispatches = (RecyclerView) view;
            if (mColumnCount <= 1) {
                rvDispatches.setLayoutManager(new LinearLayoutManager(context));
            } else {
                rvDispatches.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            switch (mContent) {
                case "Dispatches":

                    // initialize list of dispatches
                    // asnd eetup firebase listeners
                    setupFirebase();
//                    addFirebaseListener();

                    // create adapter passing the sample user data
                    dispatchViewAdapter = new DispatchSelectAdapter(getContext(), dispatches);

                    // attach adapter to recyclerview in order to populate the items
                    rvDispatches.setAdapter(dispatchViewAdapter);

                    // set layout manager to position items
                    rvDispatches.setLayoutManager(new LinearLayoutManager((getContext())));
                    break;
            }
        }
        return view;
    }

    private void setupFirebase() {

        firebaseDatabase = FirebaseDatabase.getInstance();

        //dispatch Root
        dispatchRoot = firebaseDatabase.getReference("/DISPATCHES");

    }

    private void removeFirebaseListener() {

        dispatchRoot.removeEventListener(childEventListener);
    }

    private void addFirebaseListener() {
        // note: this will also do the initial population of the list as well
        childEventListener = dispatchRoot.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String dispatchId; //create single string entity to hold start time

                String dispatchDate; //create single string entity to hold start time
                String dispatchStartTime; //create single string entity to hold start time

                if(dataSnapshot.child("DISPATCH_WINDOW").child("START_TIME").getValue() != null
                        && dataSnapshot.child("DISPATCH_WINDOW").child("END_TIME").getValue() != null
                        && dataSnapshot.child("DISPATCH_WINDOW").child("CALENDAR_DATE").getValue() != null) {

                    dispatchId = dataSnapshot
                            .getKey()
                            .toString();

                    dispatchStartTime = dataSnapshot.child("DISPATCH_WINDOW")
                            .child("START_TIME")
                            .getValue().toString();

                     dispatchDate = dataSnapshot.child("DISPATCH_WINDOW")
                            .child("CALENDAR_DATE")
                            .getValue().toString();
                    // update the client-side model
                    dispatches.add(0, new Dispatch(
                            dispatchId,
                            dispatchDate,
                            dispatchStartTime,
                            NEED_TO_PLAN));
                    // update the UI
                    dispatchViewAdapter.notifyItemInserted(0);
                    Toast.makeText(getContext(), "child added", Toast.LENGTH_SHORT).show();
                }

            }

            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Toast.makeText(getContext(), "child changed", Toast.LENGTH_SHORT).show();
            }

            public void onChildRemoved(DataSnapshot dataSnapshot) {
               // Toast.makeText(getContext(), "child removed", Toast.LENGTH_SHORT).show();
            }

            public void onCancelled(DatabaseError e) {
            }
        });
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


    private void clearList() {
        int size = this.dispatches.size();
        this.dispatches.clear();
        dispatchViewAdapter.notifyItemRangeRemoved(0,size);
    }

    @Override
    public void onPause() {
        super.onPause();
        removeFirebaseListener();
        clearList();
    }

    @Override
    public void onResume() {
        super.onResume();
        addFirebaseListener();
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
        void onListFragmentInteraction(Dispatch dispatch );
    }
}
