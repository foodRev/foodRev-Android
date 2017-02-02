package foodrev.org.foodrev.domain.interactors.impl;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
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
import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;
import foodrev.org.foodrev.domain.interactors.SignInInteractor;
import foodrev.org.foodrev.domain.interactors.base.AbstractInteractor;
import foodrev.org.foodrev.presentation.ui.activities.GoogleSignInActivity;

/**
 * Created by darver on 1/26/17.
 */

public class SignInInteractorImpl extends AbstractInteractor implements SignInInteractor {

    private static final String TAG = "SignInInteractorImpl";
    private SignInInteractor.Callback mCallback;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;

    public SignInInteractorImpl(Executor executor, MainThread mainThread, Callback callback) {
        super(executor, mainThread);
        mCallback = callback;
    }



    public void run() {

    }
}
