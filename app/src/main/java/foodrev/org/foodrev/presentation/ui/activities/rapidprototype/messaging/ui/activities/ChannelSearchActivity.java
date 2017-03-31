package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.model.Channel;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils.ChannelUtil;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils.UserUtil;

public class ChannelSearchActivity extends AppCompatActivity {

    private static final String TAG = ChannelSearchActivity.class.getSimpleName();
    private static final int REQUEST_NEW_CHANNEL = 20;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private static DatabaseReference sFirebaseDatabaseReference =
            FirebaseDatabase.getInstance().getReference();

    private FirebaseRecyclerAdapter<Channel, ChannelUtil.ChannelViewHolder> mFirebaseAdapter;

    private View.OnClickListener channelJoinClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            final TextView channelName = (TextView) view.findViewById(R.id.channelNameText);
            final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ChannelSearchActivity.this);
            //Joins the selected channel here
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("currentChannel", channelName.getText().toString());
            edit.apply();

            Log.e(TAG, channelName.getText().toString());
            //Adds to user's channels list
            sFirebaseDatabaseReference.child(UserUtil.USER_CHILD).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    sFirebaseDatabaseReference.removeEventListener(this);
                    UserUtil.addChannelToUserChannelList(dataSnapshot, sp, channelName.getText().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

            //Add to channel's user list
            sFirebaseDatabaseReference.child(ChannelUtil.CHANNELS_CHILD).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    sFirebaseDatabaseReference.removeEventListener(this);
                    ChannelUtil.addUserToChannelList(dataSnapshot.child("Public").getChildren(), channelName.getText().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

            //Change view to channel message view
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.channelSearchToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("Channels");

        mMessageRecyclerView = (RecyclerView) findViewById(R.id.channel_list_recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        mFirebaseAdapter = ChannelUtil.getFirebaseAdapterForChannelList(channelJoinClickListener);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.channel_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.create_menu:
                Intent i = new Intent(this, CreateChannelActivity.class);
                startActivityForResult(i, REQUEST_NEW_CHANNEL);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_NEW_CHANNEL) {
            if(resultCode == Activity.RESULT_OK) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        }
    }
}
