package foodrev.org.foodrev.domain.interactors.impl;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;
import foodrev.org.foodrev.domain.interactors.SignInInteractor;
import foodrev.org.foodrev.domain.interactors.base.AbstractInteractor;


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
