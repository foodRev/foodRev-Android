/**
 * Copyright Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.presentation.ui.activities.SignInActivity;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.CoordinatorMainLanding.CoordinatorMainActivity;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.FoodMap;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.DriverMode.DriverModeActivity;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.model.ChatMessage;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils.ChannelUtil;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils.MessageUtil;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils.UserUtil;

public class MessageMainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener,
        NavigationView.OnNavigationItemSelectedListener,
        MessageUtil.MessageLoadListener {

    public static final int REQUEST_PREFERENCES = 2;
    public static final int MSG_LENGTH_LIMIT = 64;
    public static final String ANONYMOUS = "anonymous";
    private static final String TAG = "MessageMainActivity";
    private static final int REQUEST_NEW_CHANNEL = 4;
    private static final int REQUEST_SEARCH = 5;
    private static final double MAX_LINEAR_DIMENSION = 500.0;
    private String mUsername;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;

    private FloatingActionButton mSendButton;
    private RecyclerView mMessageRecyclerView;
    private RecyclerView mNavRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;
    private RelativeLayout mChannelAdd;
    private TextView mCurrChanTextView;

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseRecyclerAdapter<ChatMessage, MessageUtil.MessageViewHolder>
            mFirebaseAdapter;

    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private String mCurrentChannel;

    private View.OnClickListener mChannelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Change channel here
            TextView channel = (TextView) view.findViewById(R.id.channelNameText);
            String channelName = "";
            if (channel == null) {
                channel = (TextView) view.findViewById(R.id.username);
                channelName = UserUtil.parseUsername(mSharedPreferences.getString("username", "anonymous")) + "=" + channel.getText().toString();
            } else {
                channelName = channel.getText().toString();
            }
            SharedPreferences.Editor edit = mSharedPreferences.edit();
            edit.putString("currentChannel", channelName);
            edit.apply();
            setChannelPage();

            mDrawerLayout.closeDrawers();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        DesignUtils.applyColorfulTheme(this);
        setContentView(R.layout.message_activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Set default username is anonymous.
        mUsername = ANONYMOUS;
        //Initialize Auth
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mUser.getDisplayName();
            if (mUser.getPhotoUrl() != null) {
                mPhotoUrl = mUser.getPhotoUrl().toString();
            }
        }


        mCurrentChannel = mSharedPreferences.getString("currentChannel", "general");
        mCurrChanTextView = (TextView) findViewById(R.id.currentChannelName);
        mCurrChanTextView.setText(ChannelUtil.getChannelDisplayName(mCurrentChannel, this));

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        // Initialize ProgressBar and RecyclerView.

        mMessageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mChannelAdd = (RelativeLayout) findViewById(R.id.channelAdd);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setTitleTextColor(Color.WHITE);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerLayout.setStatusBarBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));

        mFirebaseAdapter = MessageUtil.getFirebaseAdapter(this,
                this,  /* MessageLoadListener */
                mLinearLayoutManager,
                mMessageRecyclerView);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);

        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MSG_LENGTH_LIMIT)});
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mSendButton = (FloatingActionButton) findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send messages on click.
                mMessageRecyclerView.scrollToPosition(0);
                ChatMessage chatMessage = new
                        ChatMessage(mMessageEditText.getText().toString(),
                        mUsername,
                        mPhotoUrl, mCurrentChannel);
                MessageUtil.send(chatMessage, MessageMainActivity.this);
                mMessageEditText.setText("");
            }
        });



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        mChannelAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MessageMainActivity.this, ChannelSearchActivity.class);
//                startActivityForResult(intent, REQUEST_NEW_CHANNEL);
//            }
//        });

//        SearchView jumpSearchView = (SearchView) findViewById(R.id.jumpSearch);
//        int id = jumpSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//        TextView textView = (TextView) jumpSearchView.findViewById(id);
//        textView.setTextColor(Color.WHITE);
//        textView.setHintTextColor(Color.WHITE);

//        jumpSearchView.setIconified(false);
//        jumpSearchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MessageMainActivity.this, "Not implemented", Toast.LENGTH_SHORT).show();
//            }
//        });

//        mNavRecyclerView = (RecyclerView) findViewById(R.id.navRecyclerView);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        mNavRecyclerView.setLayoutManager(linearLayoutManager);
//        mNavRecyclerView.setAdapter(ChannelUtil.getFirebaseAdapterForUserChannelList(mChannelClickListener,
//                mAuth.getCurrentUser().getDisplayName()));

//        RecyclerView userListRecyclerView = (RecyclerView) findViewById(R.id.userListRecyclerView);
//        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
//        userListRecyclerView.setLayoutManager(linearLayoutManager2);
//        userListRecyclerView.setAdapter(UserUtil.getFirebaseAdapterForUserList(mChannelClickListener));
    }

    private void setChannelPage() {
        mCurrentChannel = UserUtil.parseUsername(mSharedPreferences.getString("currentChannel", "general"));

        mFirebaseAdapter = MessageUtil.getFirebaseAdapter(MessageMainActivity.this,
                MessageMainActivity.this,  /* MessageLoadListener */
                mLinearLayoutManager,
                mMessageRecyclerView);
        mMessageRecyclerView.swapAdapter(mFirebaseAdapter, false);
        mCurrChanTextView.setText(ChannelUtil.getChannelDisplayName(mCurrentChannel, this));

        mNavRecyclerView.swapAdapter(ChannelUtil.getFirebaseAdapterForUserChannelList(mChannelClickListener,
                mSharedPreferences.getString("username", "anonymous")), false);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in.
        // TODO: Add code to check if user is signed in.
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.message_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
//            case R.id.preferences_menu:
////                mSavedTheme = DesignUtils.getPreferredTheme(this);
//                Intent i = new Intent(this, PreferencesActivity.class);
//                startActivityForResult(i, REQUEST_PREFERENCES);
//                return true;
            case R.id.search:
                Intent intent = new Intent(this, MessageSearchActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadComplete() {
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: request=" + requestCode + ", result=" + resultCode);

        if (requestCode == REQUEST_NEW_CHANNEL && resultCode == Activity.RESULT_OK) {
            setChannelPage();
        }
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        switch(id){
            case(R.id.nav_profile):
//                goToDetailItemActivity();
                break;
            case(R.id.nav_coordinator):
                intent = new Intent(this, CoordinatorMainActivity.class);
                startActivity(intent);
                break;
            case(R.id.nav_driver):
                intent = new Intent(this, DriverModeActivity.class);
                startActivity(intent);
                break;
            case(R.id.nav_donor):
                intent = new Intent(this, FoodMap.class);
                startActivity(intent);
                break;
            case(R.id.nav_community):
                intent = new Intent(this, FoodMap.class);
                startActivity(intent);
                break;
            case(R.id.nav_messaging):
//                setUserAddEventListener(FirebaseAuth.getInstance().getCurrentUser().getDisplayName().replace(".", ""));
//                setInitialChannelAddEventListener();
//                intent = new Intent(this, MessageMainActivity.class);
//                startActivity(intent);
                break;
            case(R.id.nav_sign_out):
                FirebaseAuth.getInstance().signOut();
                break;
        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        finish();
        return true;
    }
}
