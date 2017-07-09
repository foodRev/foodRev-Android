package foodrev.org.foodrev.presentation.presenters;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import foodrev.org.foodrev.presentation.presenters.base.BasePresenter;
import foodrev.org.foodrev.presentation.ui.BaseView;

/**
 * Created by darver on 1/25/17.
 */

public interface SignInPresenter extends BasePresenter<SignInPresenter.View>,
        GoogleApiClient.OnConnectionFailedListener {
    interface View extends BaseView {
        void startGoogleSignIn(GoogleApiClient googleApiClient);
        void displaySignInError();
        boolean hasSeenIntroSlides();
        void goToUserTypeActivity();
        void goToIntroSlides();
    }
    void signIn();
    void onSignInResult(GoogleSignInResult result);
}
