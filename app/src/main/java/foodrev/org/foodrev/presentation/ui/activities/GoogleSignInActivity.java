/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package foodrev.org.foodrev.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.Threading.MainThreadImpl;
import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.impl.ThreadExecutor;
import foodrev.org.foodrev.domain.wrappers.GoogleAuthProviderWrapper;
import foodrev.org.foodrev.presentation.presenters.SignInPresenter;
import foodrev.org.foodrev.presentation.presenters.impl.SignInPresenterImpl;


public class GoogleSignInActivity extends AppCompatActivity implements
        SignInPresenter.View,
        View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{


    private static final String TAG = "GoogleSignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private SignInPresenter mPresenter;

    private String mDefaultWebClientId;

    private TextView mStatusTextView;
    private TextView mDetailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
        mDefaultWebClientId = getString(R.string.default_web_client_id);


        attachPresenter();
        setupUi();

    }

    public void attachPresenter() {

        GoogleAuthProviderWrapper authProvider = new GoogleAuthProviderWrapper();
        mPresenter = (SignInPresenterImpl) getLastCustomNonConfigurationInstance();
        if (mPresenter == null) {
            mPresenter = new SignInPresenterImpl.Builder()
                    .setClient(setupGoogleSignIn(this))
                    .setExecutor(ThreadExecutor.getInstance())
                    .setFirebaseAuth(FirebaseAuth.getInstance())
                    .setMainThread(MainThreadImpl.getInstance())
                    .setGoogleAuthProviderWrapper(authProvider)
                    .setConnectionCallbacks(this)
                    .setOnConnectionFailedListener(this)
                    .build();
        }
        mPresenter.attachView(this);
    }

    private GoogleApiClient setupGoogleSignIn(Context context) {
        String defaultWebClientId = ((GoogleSignInActivity)context).getDefaultWebClientId();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(defaultWebClientId)
                .requestEmail()
                .build();


        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage((FragmentActivity) context, mPresenter)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        return googleApiClient;
    }

    private void setupUi() {
        mStatusTextView = (TextView) findViewById(R.id.status);
        mDetailTextView = (TextView) findViewById(R.id.detail);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        } else if (i == R.id.sign_out_button) {
            //signOut();
        } else if (i == R.id.disconnect_button) {
            //revokeAccess();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideProgressDialog();
        mPresenter.stop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mPresenter.onSignInResult(result);

            startActivity(new Intent(this, MainActivity.class));

        }
    }

    @Override
    public void startGoogleSignIn(GoogleApiClient googleApiClient) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "suspend", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
    }


    private void signIn() {
        mPresenter.signIn();
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }


    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displaySignInError() {
        showError(getString(R.string.sign_in_error));
    }

    public String getDefaultWebClientId() {
        return mDefaultWebClientId;
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showProgressDialog() {
        //no-op
    }

    @Override
    public void hideProgressDialog() {

    }
}
