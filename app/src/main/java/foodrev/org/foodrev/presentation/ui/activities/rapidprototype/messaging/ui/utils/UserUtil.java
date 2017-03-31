package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.model.User;

import static foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils.MessageUtil.MESSAGING_CHILD;

public class UserUtil {
    private static final String LOG_TAG = UserUtil.class.getSimpleName();
    public static final String USER_CHILD = "users";
    private static DatabaseReference sFirebaseDatabaseReference =
            FirebaseDatabase.getInstance().getReference();
    private static FirebaseAuth sFirebaseAuth = FirebaseAuth.getInstance();

    public static void createUser(User user) {
        sFirebaseDatabaseReference
                .child(MESSAGING_CHILD)
                .child(USER_CHILD)
                .child(parseUsername(sFirebaseAuth.getCurrentUser().getDisplayName()))
                .setValue(user);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView username;
        public static View.OnClickListener sChannelViewListener;

        public UserViewHolder(View view) {
            super(view);
            mView = view;
            username = (TextView) view.findViewById(R.id.username);
            mView.setOnClickListener(sChannelViewListener);
        }
    }

    public static FirebaseRecyclerAdapter<User, UserViewHolder> getFirebaseAdapterForUserList(
                                                                                   final View.OnClickListener clickListener) {
        UserViewHolder.sChannelViewListener = clickListener;
        final FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<User,
                UserViewHolder>(
                User.class,
                R.layout.item_user,
                UserViewHolder.class,
                sFirebaseDatabaseReference
                        .child(MESSAGING_CHILD)
                        .child(USER_CHILD)) {
            @Override
            protected void populateViewHolder(final UserViewHolder viewHolder,
                                              User user, int position) {
                Log.d("Test2", user.getUsername().keySet().toString());
                viewHolder.username.setText((String)user.getUsername().keySet().toArray()[0]);

            }
        };

        return adapter;
    }
    public static void addChannelToUserChannelList(DataSnapshot dataSnapshot, SharedPreferences sp, String channelName) {
        for(DataSnapshot user : dataSnapshot.getChildren()) {
            //If this is the correct user
            //and this user doesn't have the channel already in their channel list
            String parsedUsername = parseUsername(sp.getString("username", ""));
            if(user.getKey().equals(parsedUsername) &&
                    !user.child("username").child(parsedUsername).toString().contains(channelName)) {
                DatabaseReference channelList = user.child("username").child(parsedUsername).getRef();
                Map<String, Object> channel = new HashMap<>();
                channel.put(channelName, channelName);
                channelList.updateChildren(channel);
            }
        }
    }

    public static String parseUsername(String str){
        return str.replace('.', ' ').replace('#', ' ').replace('$', ' ').replace('[', ' ').replace(']', ' ');
    }
}
