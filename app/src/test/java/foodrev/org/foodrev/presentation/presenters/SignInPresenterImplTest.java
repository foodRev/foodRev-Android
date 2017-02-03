package foodrev.org.foodrev.presentation.presenters;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;
import foodrev.org.foodrev.domain.wrappers.GoogleAuthProviderWrapper;
import foodrev.org.foodrev.presentation.presenters.impl.SignInPresenterImpl;

/**
 * Created by darver on 2/1/17.
 */

public class SignInPresenterImplTest {

    private SignInPresenterImpl signInPresenter;
    private GoogleApiClient googleApiClientMock;
    private GoogleAuthProviderWrapper googleAuthProviderWrapper;
    private FirebaseAuth firebaseAuthMock;

    @Before
    public void setup() {
        googleApiClientMock = Mockito.mock(GoogleApiClient.class);
        Executor executorMock = Mockito.mock(Executor.class);
        firebaseAuthMock = Mockito.mock(FirebaseAuth.class);
        MainThread mainThreadMock = Mockito.mock(MainThread.class);
        googleAuthProviderWrapper = Mockito.mock(GoogleAuthProviderWrapper.class);
        signInPresenter = new SignInPresenterImpl.Builder()
                .setClient(googleApiClientMock)
                .setExecutor(executorMock)
                .setFirebaseAuth(firebaseAuthMock)
                .setMainThread(mainThreadMock)
                .setGoogleAuthProviderWrapper(googleAuthProviderWrapper)
                .build();
    }

    @Test
    public void signInTest() {
        SignInPresenter.View viewMock = Mockito.mock(SignInPresenter.View.class);
        signInPresenter.attachView(viewMock);
        signInPresenter.signIn();
        Mockito.verify(viewMock).startGoogleSignIn(googleApiClientMock);
    }

    @Test
    public void testSignInResultSuccess() {
        SignInPresenter.View viewMock = Mockito.mock(SignInPresenter.View.class);
        signInPresenter.attachView(viewMock);
        GoogleSignInResult signInResultMock = Mockito.mock(GoogleSignInResult.class);
        GoogleSignInAccount signInAccountMock = Mockito.mock(GoogleSignInAccount.class);
        Mockito.when(signInResultMock.isSuccess()).thenReturn(true);
        Mockito.when(signInResultMock.getSignInAccount()).thenReturn(signInAccountMock);
        AuthCredential authCredentialMock = Mockito.mock(AuthCredential.class);
        Mockito.when(googleAuthProviderWrapper.getCredential(Matchers.anyString(), Matchers.anyString()))
                .thenReturn(authCredentialMock);
        Task<AuthResult> taskMock = Mockito.mock(Task.class);
        Mockito.when(firebaseAuthMock.signInWithCredential(authCredentialMock))
                .thenReturn(taskMock);
        ArgumentCaptor<OnCompleteListener> argumentCaptor = ArgumentCaptor.forClass(OnCompleteListener.class);
        Mockito.when(taskMock.addOnCompleteListener(Matchers.any(OnCompleteListener.class)))
                .thenReturn(taskMock);
        signInPresenter.onSignInResult(signInResultMock);
        Mockito.verify(viewMock).showProgressDialog();
        Mockito.verify(taskMock).addOnCompleteListener(argumentCaptor.capture());
        Mockito.when(taskMock.isSuccessful()).thenReturn(true);
        argumentCaptor.getValue().onComplete(taskMock);
        Mockito.verify(viewMock, Mockito.never()).displaySignInError();
        Mockito.verify(viewMock).hideProgressDialog();
    }
}
