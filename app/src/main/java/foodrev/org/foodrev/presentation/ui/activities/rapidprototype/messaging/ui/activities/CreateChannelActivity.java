package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.model.Channel;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils.ChannelUtil;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils.UserUtil;

public class CreateChannelActivity extends AppCompatActivity {

    private EditText mChannelEditText;
    private EditText mPurposeEditText;
    private TextView mChannelType;
    private TextView mChannelDescription;
    private Switch mTypeSwitch;
    private Menu mMenu;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private static DatabaseReference sFirebaseDatabaseReference =
            FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_channel);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.channelCreateToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create channel");
        toolbar.setTitleTextColor(Color.WHITE);

        mChannelDescription = (TextView)findViewById(R.id.channel_description);
        mChannelType = (TextView)findViewById(R.id.channel_type);
        mChannelEditText = (EditText) findViewById(R.id.channelEditText);
        mPurposeEditText = (EditText) findViewById(R.id.purposeEditText);
        mTypeSwitch = (Switch) findViewById(R.id.public_private_switch);
        mTypeSwitch.setChecked(true);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mTypeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    mChannelType.setText(getString(R.string.public_channel));
                    mChannelDescription.setText(getString(R.string.public_description));
                } else {
                    mChannelType.setText(getString(R.string.private_channel));
                    mChannelDescription.setText(getString(R.string.private_description));
                }
            }
        });

        mChannelEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() < 21 && s.length() > 0 && !s.toString().contains(" ")
                        && !s.toString().contains(".") && !s.toString().matches(".*[A-Z].*")) {
                    mMenu.findItem(R.id.create_menu).setEnabled(true);
                } else {
                    mMenu.findItem(R.id.create_menu).setEnabled(false);
                }
            }
        });
    }

    private void createChannel() {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(CreateChannelActivity.this);
        SharedPreferences.Editor edit = sp.edit();
        final String channelName = mChannelEditText.getText().toString();
        String channelType = mChannelType.getText().toString();
        String purpose = null;
        HashMap<String, String> userList = new HashMap<>();
        if(!mPurposeEditText.toString().equals("")) {
            purpose = mPurposeEditText.getText().toString();
        }

        edit.putString("currentChannel", channelName);
        edit.apply();

        //Adding channel to user's list of channel
        sFirebaseDatabaseReference.child(UserUtil.USER_CHILD).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserUtil.addChannelToUserChannelList(dataSnapshot, sp, channelName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        boolean isPublic = mTypeSwitch.isChecked();

        //user list for channel
        userList.put(UserUtil.parseUsername(mUser.getDisplayName()), UserUtil.parseUsername(mUser.getDisplayName()));
        Channel channel = new Channel(userList, channelName, purpose);
        ChannelUtil.createChannel(channel, isPublic);
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.channel_activity_menu, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            finish();
        } else if (itemId == R.id.create_menu) {
            createChannel();
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        super.onPrepareOptionsMenu(menu);

        MenuItem myItem = menu.findItem(R.id.create_menu);
        myItem.setEnabled(false);

        return true;
    }
}
