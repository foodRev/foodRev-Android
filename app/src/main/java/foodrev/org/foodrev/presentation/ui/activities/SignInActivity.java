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
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.IntroSlidesGeneric;
import foodrev.org.foodrev.threading.MainThreadImpl;
import foodrev.org.foodrev.domain.executor.impl.ThreadExecutor;
import foodrev.org.foodrev.domain.wrappers.GoogleAuthProviderWrapper;
import foodrev.org.foodrev.presentation.presenters.SignInPresenter;
import foodrev.org.foodrev.presentation.presenters.impl.SignInPresenterImpl;


public class SignInActivity extends AppCompatActivity implements SignInPresenter.View, View.OnClickListener {


    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private SignInPresenter mPresenter;
    private String mDefaultWebClientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
        mDefaultWebClientId = getString(R.string.default_web_client_id);
        attachPresenter();
        setupUi();

    }
    public void attachPresenter() {
        mPresenter = (SignInPresenterImpl) getLastCustomNonConfigurationInstance();
        if (mPresenter == null) {
            mPresenter = new SignInPresenterImpl.Builder()
                    .setClient(setupGoogleSignIn(this))
                    .setExecutor(ThreadExecutor.getInstance())
                    .setMainThread(MainThreadImpl.getInstance())
                    .setFirebaseAuth(FirebaseAuth.getInstance())
                    .setGoogleAuthProviderWrapper(new GoogleAuthProviderWrapper())
                    .build();
        }
        mPresenter.attachView(this);
    }

    private void setupUi() {
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.stop();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    private GoogleApiClient setupGoogleSignIn(Context context) {
        String defaultWebClientId = ((SignInActivity)context).getDefaultWebClientId();
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

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            mPresenter.signIn();
            showProgressDialog();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mPresenter.onSignInResult(result);
        }
    }

    @Override
    public void startGoogleSignIn(GoogleApiClient googleApiClient) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displaySignInError() {
        showError(getString(R.string.sign_in_error));
        hideProgressDialog();
    }

    public String getDefaultWebClientId() {
        return mDefaultWebClientId;
    }

    @Override
    public void showRetrievedData(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressDialog() {
        findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    @Override
    public void goToIntroSlides() {
        startActivity(new Intent(this, IntroSlidesGeneric.class));
        //TODO create shared preferences for skipping intros if seen
        finish();
    }

    // Save presenter
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
    }
}
