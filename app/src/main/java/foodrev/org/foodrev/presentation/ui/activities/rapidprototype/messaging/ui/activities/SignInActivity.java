///**
// * Copyright Google Inc. All Rights Reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.activities;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.GoogleAuthProvider;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.HashMap;
//
//
//import foodrev.org.foodrev.R;
//import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.model.Channel;
//import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.model.User;
//import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils.ChannelUtil;
//import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.ui.utils.UserUtil;
//
//public class SignInActivity extends AppCompatActivity implements
//        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
//
//    private static final String TAG = "SignInActivity";
//    private static final int RC_SIGN_IN = 9001;
//
//    private SignInButton mSignInButton;
//
//    private GoogleApiClient mGoogleApiClient;
//
//    // Firebase instance variables
//    private FirebaseAuth mAuth;
//    private static DatabaseReference sFirebaseDatabaseReference =
//            FirebaseDatabase.getInstance().getReference();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_in);
//
//        // Assign fields
//        mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
//        // Set click listeners
//        mSignInButton.setOnClickListener(this);
//
//        // Configure Google Sign In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        // Log.e(TAG, gso.toString());
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//
//        // Initialize FirebaseAuth
//        //Initialize Auth
//        mAuth = FirebaseAuth.getInstance();
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.sign_in_button:
//                signIn();
//                break;
//        }
//    }
//
//    private void signIn() {
//        Intent signInIntent =
//                Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
//        // be available.
//        Log.d(TAG, "onConnectionFailed:" + connectionResult);
//        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // Handle the result of the sign-in activity
//        // Handle the result of the sign-in activity
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            Log.e(TAG, "data:" + data.getExtras().toString());
//            if (result.isSuccess()) {
////                mSlackImageView.setVisibility(View.VISIBLE);
////                Animation rotation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
////                mSlackImageView.setAnimation(rotation);
//                mSignInButton.setVisibility(View.INVISIBLE);
//                // successful, now authenticate with Firebase
//                GoogleSignInAccount account = result.getSignInAccount();
//                firebaseAuthWithGoogle(account);
//            } else {
//                Log.e(TAG, "Google Sign In failed.");
//            }
//        }
//
//    }
//
//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        Log.e(TAG, "WTF: "+ credential.toString());
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        // Process the auth task result
//                        // Process the auth task result
//                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
//                        // If sign in fails, display a message to the user.
//                        // If sign in succeeds, start MessageMainActivity and finish this activity
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "signInWithCredential", task.getException());
//                            Toast.makeText(SignInActivity.this, "Authentication failed.", Toast
//                                    .LENGTH_SHORT).show();
//                            mSignInButton.setVisibility(View.VISIBLE);
//                        } else {
//                            setUserAddEventListener(mAuth.getCurrentUser().getDisplayName().replace(".", ""));
//                            setInitialChannelAddEventListener();
//                            startActivity(new Intent(SignInActivity.this, MessageMainActivity.class));
//                            finish();
//                        }
//                    }
//                });
//    }
//
//    private void setUserAddEventListener(final String username) {
//        sFirebaseDatabaseReference.child(UserUtil.USER_CHILD).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.e(TAG, "setUserAddEventListener running.");
//                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SignInActivity.this);
//                SharedPreferences.Editor edit = sp.edit();
//                addUserIfExists(edit, dataSnapshot, UserUtil.parseUsername(username));
//                sFirebaseDatabaseReference.removeEventListener(this);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        });
//    }
//
//    private void addUserIfExists(SharedPreferences.Editor edit, DataSnapshot dataSnapshot, String username) {
//        boolean isUserInFirebase = false;
//        //TODO: Change to email to prevent same display name
//        Log.e(TAG, "USERNAME: "+ username);
//        edit.putString("username", mAuth.getCurrentUser().getDisplayName());
//        edit.commit();
//        //This checks the edgecase where firebase doesn't have any users at all
//        if(!dataSnapshot.getChildren().iterator().hasNext()) {
//            putUserIntoFirebase();
//        } else {
//            //Goes through each of the users in firebase
//            for (DataSnapshot children : dataSnapshot.getChildren()) {
//                //Goes deep into the user json
//                //Basically chathub/users/{some-user-name}/username/{some-user-name}
//                //then compares {some-user-name} with the username
//                if(children.getChildren().iterator().next().getChildren().iterator().next().getKey().equals(username)) {
//                    isUserInFirebase = true;
//                }
//            }
//            if(!isUserInFirebase) {
//                putUserIntoFirebase();
//            }
//        }
//    }
//
//    private void setInitialChannelAddEventListener() {
//        sFirebaseDatabaseReference.child(ChannelUtil.CHANNELS_CHILD).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                checkIfChildChannelExists(dataSnapshot, "general");
//                checkIfChildChannelExists(dataSnapshot, "random");
//                sFirebaseDatabaseReference.removeEventListener(this);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        });
//    }
//
//    private void checkIfChildChannelExists(DataSnapshot dataSnapshot, String channel) {
//        boolean isChannelInFirebase = false;
//        if(!dataSnapshot.getChildren().iterator().hasNext()) {
//            createChannelIntoFirebase(channel);
//        } else {
//            //Goes through each of the channels in firebase
//            for (DataSnapshot children : dataSnapshot.child("Public").getChildren()) {
//                //Goes deep into the channels json
//                //Basically chathub/channels/UNIQUE_KEY/channelName
//                if (children.getChildren().iterator().next().getValue().equals(channel)) {
//                    isChannelInFirebase = true;
//                }
//            }
//            if(!isChannelInFirebase) {
//                createChannelIntoFirebase(channel);
//            } else {
//                ChannelUtil.addUserToChannelList(dataSnapshot.child("Public").getChildren(), channel);
//            }
//        }
//    }
//
//    private void createChannelIntoFirebase(String channelName) {
//        HashMap<String, String> userList = new HashMap<>();
//        //user list
//        userList.put(UserUtil.parseUsername(mAuth.getCurrentUser().getDisplayName()), mAuth.getCurrentUser().getDisplayName());
//        Channel channel = new Channel(userList, channelName, "General Purpose");
//        ChannelUtil.createChannel(channel, true);
//    }
//
//    private void putUserIntoFirebase() {
//        HashMap<String, String> channels = new HashMap<String, String>();
//        channels.put("general", "general");
//        channels.put("random", "random");
//        HashMap<String, HashMap<String, String>> newUser = new HashMap<>();
//        newUser.put(UserUtil.parseUsername(mAuth.getCurrentUser().getDisplayName()), channels);
//        UserUtil.createUser(new User(newUser));
//    }
//}
