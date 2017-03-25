package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.model.ChatMessage;


/**
 * Created by thomastse on 12/5/16.
 */

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchViewHolder> {
    private List<ChatMessage> mMessageList;
    private Activity mActivity;
    private SharedPreferences mSharedPrefs;

    public SearchResultsAdapter(List<ChatMessage> messages, Activity activity,
                                SharedPreferences sharedPreferences){
        this.mMessageList = messages;
        this.mActivity = activity;
        this.mSharedPrefs = sharedPreferences;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_found_message, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        ChatMessage message = mMessageList.get(position);
        holder.messageChannel.setText(message.getChannelName());
        if(message.getChannelName().contains("Private")){
            holder.poundSymbol.setText("@");
        }
        setPhotoAndMessage(holder, message, mSharedPrefs);
        setTimestamp(message, holder);

    }

    private void setPhotoAndMessage(final SearchResultsAdapter.SearchViewHolder viewHolder,
                                           ChatMessage chatMessage,
                                           final SharedPreferences preferences) {
        viewHolder.messageTextView.setText(chatMessage.getText());
        viewHolder.messengerTextView.setText(chatMessage.getName());
        if (chatMessage.getPhotoUrl() == null) {
            viewHolder.messengerImageView
                    .setImageDrawable(ContextCompat
                            .getDrawable(mActivity,
                                    R.drawable.ic_account_circle_black_36dp));
        } else {
            SimpleTarget target = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                    viewHolder.messengerImageView.setImageBitmap(bitmap);
//                    final String palettePreference = mActivity.getString(R.string
//                            .auto_palette_preference);

//                    if (preferences.getBoolean(palettePreference, false)) {
//                        DesignUtils.setBackgroundFromPalette(bitmap, viewHolder
//                                .messageLayout);
//                    } else {
                        viewHolder.messageLayout.setBackground(
                                mActivity.getResources().getDrawable(
                                        R.drawable.message_background));
//                    }

                }
            };
            Glide.with(mActivity)
                    .load(chatMessage.getPhotoUrl())
                    .asBitmap()
                    .into(target);
        }
    }


    private void setTimestamp(ChatMessage chatMessage,
                                     final SearchResultsAdapter.SearchViewHolder viewHolder) {
        long timestamp = chatMessage.getTimestamp();
        if (timestamp == 0 || timestamp == chatMessage.NO_TIMESTAMP ) {
            viewHolder.timestampTextView.setVisibility(View.GONE);
        } else {
            viewHolder.timestampTextView.setText(DesignUtils.formatTime(mActivity,
                    timestamp));
            viewHolder.timestampTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public void setMessageList(List<ChatMessage> chatMessages) {
        mMessageList = chatMessages;
        notifyDataSetChanged();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder{

        public TextView messageTextView;
        public TextView messengerTextView;
        public CircleImageView messengerImageView;
        public TextView timestampTextView;
        public View messageLayout;
        public TextView messageChannel;
        public TextView poundSymbol;

        public SearchViewHolder(View v){
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
            timestampTextView = (TextView) itemView.findViewById(R.id.timestampTextView);
            messageLayout = (View) itemView.findViewById(R.id.messageLayout);
            messageChannel = (TextView) itemView.findViewById(R.id.foundChannel);
            poundSymbol = (TextView) itemView.findViewById(R.id.pound_sign);
        }

    }
}
