package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import foodrev.org.foodrev.R;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.model.ChatMessage;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils.SearchResultsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessagesListFragment extends Fragment {

    private static List<ChatMessage> mChatMessages = new ArrayList<>();
    private OnListFragmentInteractionListener mListener;
    private SearchResultsAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MessagesListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MessagesListFragment newInstance(List<ChatMessage> messages) {
        MessagesListFragment fragment = new MessagesListFragment();
        mChatMessages = messages;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new SearchResultsAdapter(mChatMessages, getActivity(),
                    PreferenceManager.getDefaultSharedPreferences(getActivity()));

            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        adapter.setMessageList(chatMessages);
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
        //void onListFragmentInteraction(DummyItem item);
    }
}
