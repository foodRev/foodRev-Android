package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.model.Channel;

import static foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils.MessageUtil.MESSAGING_CHILD;
import static foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils.UserUtil.USER_CHILD;

public class ChannelUtil {
    private static final String LOG_TAG = ChannelUtil.class.getSimpleName();
    public static final String CHANNELS_CHILD = "channels";
    private static DatabaseReference sFirebaseDatabaseReference =
            FirebaseDatabase.getInstance().getReference();
    private static FirebaseAuth sFirebaseAuth = FirebaseAuth.getInstance();

    public static void createChannel(Channel channel, boolean isPublic) {
        if(isPublic) {
            sFirebaseDatabaseReference
                    .child(MESSAGING_CHILD)
                    .child(CHANNELS_CHILD)
                    .child("Public")
                    .push()
                    .setValue(channel);
        } else {
            sFirebaseDatabaseReference
                    .child(MESSAGING_CHILD)
                    .child(CHANNELS_CHILD)
                    .child("Private")
                    .push().setValue(channel);
        }
    }

    public static class ChannelViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView channelName;
        public static View.OnClickListener sChannelViewListener;

        public ChannelViewHolder(View view) {
            super(view);
            mView = view;
            channelName = (TextView) view.findViewById(R.id.channelNameText);
            mView.setOnClickListener(sChannelViewListener);
        }
    }

    public static FirebaseRecyclerAdapter<Channel, ChannelViewHolder> getFirebaseAdapterForChannelList(final View.OnClickListener clickListener) {
        ChannelViewHolder.sChannelViewListener = clickListener;
        final FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Channel,
                ChannelViewHolder>(
                Channel.class,
                R.layout.item_channel_search,
                ChannelViewHolder.class,
                sFirebaseDatabaseReference
                        .child(MESSAGING_CHILD)
                        .child(CHANNELS_CHILD)
                        .child("Public")) {
            @Override
            protected void populateViewHolder(final ChannelViewHolder viewHolder,
                                              Channel channel, int position) {
                viewHolder.channelName.setText(channel.getChannelName());
            }
        };

        return adapter;
    }

    public static FirebaseRecyclerAdapter<String, ChannelViewHolder> getFirebaseAdapterForUserChannelList(final View.OnClickListener clickListener,
                                                                                                          final String username) {
        ChannelViewHolder.sChannelViewListener = clickListener;

        final FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<String,
                ChannelViewHolder>(
                String.class,
                R.layout.item_channel,
                ChannelViewHolder.class,
                sFirebaseDatabaseReference
                        .child(MESSAGING_CHILD)
                        .child(USER_CHILD)
                        .child(UserUtil.parseUsername(username))
                        .child("username")
                        .child(UserUtil.parseUsername(username))) {
            @Override
            protected void populateViewHolder(final ChannelViewHolder viewHolder,
                                              String channel, int position) {
                viewHolder.channelName.setText(channel);
            }
        };

        return adapter;
    }

    public static void addUserToChannelList(Iterable<DataSnapshot> channelList, String channelName) {
        //This method is for adding a user to random's or general's userlist
        //Iterate through the list of channels
        for (DataSnapshot channel : channelList) {
            //Check if the channel name is the channel you want to update
            //Also, check if there isn't a user in the list already
            if(channel.child("channelName").getValue().equals(channelName)
                    && !channel.child("userList").getValue().toString().contains(
                        UserUtil.parseUsername(sFirebaseAuth.getCurrentUser().getDisplayName()))) {
                //Create the Map to store into firebase, the value is a String, but has to be an Object
                //because updateChildren requires it
                Map<String, Object> user = new HashMap<>();
                user.put(UserUtil.parseUsername(sFirebaseAuth.getCurrentUser().getDisplayName()),
                        UserUtil.parseUsername(sFirebaseAuth.getCurrentUser().getDisplayName()));
                //navigate to chathub/channels/{UNIQUE_KEY}/userlist
                DatabaseReference childReference = channel.child("userList").getRef();
                //updateChildren does not overwrite
                childReference.updateChildren(user);
            }
        }
    }

    public static String getChannelDisplayName(String unEditedChannelName, Activity currActivity){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(currActivity);
        if(unEditedChannelName.contains("=")){
            int start = UserUtil.parseUsername(sharedPreferences.getString("username", "anonymous")).length() + 1;
            unEditedChannelName = "Private Conversation with " + unEditedChannelName.substring(start);
        }

        return unEditedChannelName;
    }
}
