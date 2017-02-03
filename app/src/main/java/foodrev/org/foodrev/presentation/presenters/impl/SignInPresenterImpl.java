package foodrev.org.foodrev.presentation.presenters.impl;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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
import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;
import foodrev.org.foodrev.domain.interactors.SignInInteractor;
import foodrev.org.foodrev.domain.interactors.impl.SignInInteractorImpl;
import foodrev.org.foodrev.domain.wrappers.GoogleAuthProviderWrapper;
import foodrev.org.foodrev.presentation.presenters.SignInPresenter;
import foodrev.org.foodrev.presentation.presenters.base.AbstractPresenter;
import foodrev.org.foodrev.presentation.ui.BaseView;

/**
 * Created by darver on 1/25/17.
 */


// TODO: add GoogleApiClient.ConnectionCallbacks and GoogleApiClient.OnConnectionFailedListener interfaces
public class SignInPresenterImpl extends AbstractPresenter implements
    SignInPresenter,
    SignInInteractor.Callback{


    private static final String TAG = "SignInPresenterImpl";
    private SignInPresenter.View mView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private GoogleAuthProviderWrapper mGoogleAuthProviderWrapper;

    public static class Builder {
        private GoogleApiClient client;
        private Executor executor;
        private MainThread mainThread;
        private FirebaseAuth firebaseAuth;
        private GoogleAuthProviderWrapper googleAuthProviderWrapper;


        public Builder setClient(GoogleApiClient client) {
            this.client = client;
            return this;
        }

        public Builder setExecutor(Executor executor) {
            this.executor = executor;
            return this;
        }

        public Builder setMainThread(MainThread mainThread) {
            this.mainThread = mainThread;
            return this;
        }

        public Builder setFirebaseAuth(FirebaseAuth firebaseAuth) {
            this.firebaseAuth = firebaseAuth;
            return this;
        }

        public Builder setGoogleAuthProviderWrapper(GoogleAuthProviderWrapper wrapper) {
            this.googleAuthProviderWrapper = wrapper;
            return this;
        }


        public SignInPresenterImpl build() {
            if(client == null
                    || executor == null
                    || mainThread == null
                    || firebaseAuth == null
                    || googleAuthProviderWrapper == null) {
                throw new IllegalArgumentException("Missing Builder Dependency!");
            }
            SignInPresenterImpl impl = new SignInPresenterImpl(executor, mainThread);
            impl.mGoogleApiClient = client;
            impl.mAuth = firebaseAuth;
            impl.mGoogleAuthProviderWrapper = googleAuthProviderWrapper;
            return impl;
        }
    }

    private SignInPresenterImpl(Executor executor, MainThread mainThread) {
        super(executor, mainThread);
        mAuthListener = setupAuthStateListener();
    }

    @Override
    public void attachView(BaseView view) {
        mView = (SignInPresenter.View) view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void signIn() {
        if(getView() != null) {
            getView().startGoogleSignIn(mGoogleApiClient);
        }
    }

    private FirebaseAuth.AuthStateListener setupAuthStateListener(){
        FirebaseAuth.AuthStateListener listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                mView.showProgressDialog();
            }
        };
        return listener;
    }

    @Override
    public void onSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
        } else {
            // Google Sign In failed, update UI appropriately
            // updateUI(null);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        mView.displaySignInError();
    }

    public void start() {
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        mView.showProgressDialog();

        AuthCredential credential = mGoogleAuthProviderWrapper.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "signInWithCredential", task.getException());
                            mView.displaySignInError();
                        }
                        mView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void resume() {
        SignInInteractorImpl interactor = new SignInInteractorImpl(mExecutor, mMainThread, this);
        interactor.execute();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                       mView.showProgressDialog();
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        mView.showProgressDialog();
                    }
                });
    }

    public View getView() {
        return mView;
    }
}
