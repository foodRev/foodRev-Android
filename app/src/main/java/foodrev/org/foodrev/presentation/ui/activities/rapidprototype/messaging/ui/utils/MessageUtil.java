package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.model.ChatMessage;

public class MessageUtil {
    private static final String LOG_TAG = MessageUtil.class.getSimpleName();
    public static final String MESSAGES_CHILD = "messages";
    public static final String MESSAGING_CHILD = "MESSAGING";
    private static DatabaseReference sFirebaseDatabaseReference =
            FirebaseDatabase.getInstance().getReference();
    private static MessageLoadListener sAdapterListener;
    public interface MessageLoadListener { void onLoadComplete(); }

    public static void send(ChatMessage chatMessage, Activity activity) {
        final SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(activity);
        String channelName = preferences.getString("currentChannel", "general");
        String[] parsedChannel = channelName.split("=");
        if(parsedChannel.length == 1) {
            sFirebaseDatabaseReference
                    .child(MESSAGING_CHILD)
                    .child(MESSAGES_CHILD)
                    .child(channelName)
                    .push().setValue(chatMessage);
        } else if (parsedChannel[0].equals(parsedChannel[1])) {
            sFirebaseDatabaseReference
                    .child(MESSAGING_CHILD)
                    .child(MESSAGES_CHILD)
                    .child(channelName)
                    .push().setValue(chatMessage);
        } else {
            sFirebaseDatabaseReference
                    .child(MESSAGING_CHILD)
                    .child(MESSAGES_CHILD)
                    .child(parsedChannel[0]+"="+parsedChannel[1])
                    .push().setValue(chatMessage);
            sFirebaseDatabaseReference
                    .child(MESSAGING_CHILD)
                    .child(MESSAGES_CHILD)
                    .child(parsedChannel[1]+"="+parsedChannel[0])
                    .push().setValue(chatMessage);
        }
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public static View.OnClickListener sMessageViewListener;
        public TextView messageTextView;
        public ImageView messageImageView;
        public TextView messengerTextView;
        public CircleImageView messengerImageView;
        public TextView timestampTextView;
        public View messageLayout;

        public MessageViewHolder(View v) {
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
            messageImageView = (ImageView) itemView.findViewById(R.id.messageImageView);
            timestampTextView = (TextView) itemView.findViewById(R.id.timestampTextView);
            messageLayout = itemView.findViewById(R.id.messageLayout);
            v.setOnClickListener(sMessageViewListener);
        }
    }

    public static FirebaseRecyclerAdapter getFirebaseAdapter(final Activity activity,
                                                             MessageLoadListener listener,
                                                             final LinearLayoutManager linearManager,
                                                             final RecyclerView recyclerView) {
        final SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(activity);
        sAdapterListener = listener;

        final FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<ChatMessage,
                MessageViewHolder>(
                ChatMessage.class,
                R.layout.item_message,
                MessageViewHolder.class,
                sFirebaseDatabaseReference
                        .child(MESSAGING_CHILD)
                        .child(MESSAGES_CHILD)
                        .child(UserUtil.parseUsername(preferences.getString("currentChannel", "general")))) {
            @Override
            protected void populateViewHolder(final MessageViewHolder viewHolder,
                                              ChatMessage chatMessage, int position) {
                sAdapterListener.onLoadComplete();
                setPhotoAndMessage(viewHolder, chatMessage, activity);
                setTimestamp(chatMessage, viewHolder, activity);

                long minTimestamp = chatMessage.getTimestamp() - 2000;
                long maxTimestamp = chatMessage.getTimestamp() + 2000;
                long currentTimestamp = new Date().getTime();

                if(chatMessage.getText().contains("@"+preferences.getString("username", "anonymous"))
                        && currentTimestamp >= minTimestamp
                        && currentTimestamp <= maxTimestamp) {
                    NotificationCreator.createNotification(activity,
                            preferences.getString("currentChannel", "general"),
                            chatMessage.getText());
                }
            }
        };

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int messageCount = adapter.getItemCount();
                int lastVisiblePosition = linearManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (messageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.scrollToPosition(positionStart);
                }
            }
        });
        return adapter;
    }

    private static void setPhotoAndMessage(final MessageViewHolder viewHolder,
                                           ChatMessage chatMessage,
                                           final Activity activity) {
        viewHolder.messageTextView.setText(chatMessage.getText());
        viewHolder.messengerTextView.setText(chatMessage.getName());
        if (chatMessage.getPhotoUrl() == null) {
            viewHolder.messengerImageView
                    .setImageDrawable(ContextCompat
                            .getDrawable(activity,
                                    R.drawable.ic_account_circle_black_36dp));
        }
    }

    private static void setTimestamp(ChatMessage chatMessage,
                                     final MessageViewHolder viewHolder,
                                     Activity activity) {
        long timestamp = chatMessage.getTimestamp();
        if (timestamp == 0 || timestamp == ChatMessage.NO_TIMESTAMP ) {
            viewHolder.timestampTextView.setVisibility(View.GONE);
        } else {
            viewHolder.timestampTextView.setText(DesignUtils.formatTime(activity,
                    timestamp));
            viewHolder.timestampTextView.setVisibility(View.VISIBLE);
        }
    }

}
