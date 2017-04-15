package foodrev.org.foodrev.presentation.presenters.impl;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;
import foodrev.org.foodrev.domain.infos.models.DriverTasks;
import foodrev.org.foodrev.presentation.presenters.DriverModePresenter;

/**
 * Created by darver on 3/24/17.
 */

public class DriverModePresenterImpl implements DriverModePresenter {

    private DriverModePresenter.View mView;
    private Executor mExecutor;
    private MainThread mMainThread;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mTaskReference;
    private ValueEventListener mTaskListener;
    private DriverTasks mDriverTasks;

    public DriverModePresenterImpl(Executor executor, MainThread mainThread) {
        mExecutor = executor;
        mMainThread = mainThread;
    }

    @Override
    public void attachView(DriverModePresenter.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void resume() {

        // Get Driver ID associated with signed in user
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Log.d("DriverMode DriverKey:", uid);
        // Using that ID, fetch the associated task list
        mDriverTasks = getDriverTasks(uid);

        // Pass Driver Tasks to view

    }

    private DriverTasks getDriverTasks(String uid) {
        mTaskReference = FirebaseDatabase.getInstance().getReference("/TASKS").child(uid);
        ValueEventListener taskListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDriverTasks = dataSnapshot.getValue(DriverTasks.class);

                Log.d("onDataChange", mDriverTasks.sourceKey + mDriverTasks.destinationKey + mDriverTasks.progress);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mTaskReference.addValueEventListener(taskListener);
        mTaskListener = taskListener;
        return mDriverTasks;
    }




    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void signOut() {
        if(mView != null) {
            FirebaseAuth.getInstance().signOut();
            mView.goToSignInActivity();
        }

    }
}
