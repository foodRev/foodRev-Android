//package foodrev.org.foodrev;
//
//import android.app.ProgressDialog;
//import android.support.test.espresso.IdlingResource;
//
//import foodrev.org.foodrev.presentation.ui.activities.BaseSignInActivity;
//
///**
// * Monitor Activity idle status by watching ProgressDialog.
// */
//public class BaseActivityIdlingResource implements IdlingResource {
//
//    private BaseSignInActivity mActivity;
//    private ResourceCallback mCallback;
//
//    public BaseActivityIdlingResource(BaseSignInActivity activity) {
//        mActivity = activity;
//    }
//
//    @Override
//    public String getName() {
//        return "BaseActivityIdlingResource:" + mActivity.getLocalClassName();
//    }
//
//    @Override
//    public boolean isIdleNow() {
//        ProgressDialog dialog = mActivity.mProgressDialog;
//        boolean idle = (dialog == null || !dialog.isShowing());
//
//        if (mCallback != null && idle) {
//            mCallback.onTransitionToIdle();
//        }
//
//        return idle;
//    }
//
//    @Override
//    public void registerIdleTransitionCallback(ResourceCallback callback) {
//        mCallback = callback;
//    }
//}
