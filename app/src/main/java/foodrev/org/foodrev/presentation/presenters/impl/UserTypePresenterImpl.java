package foodrev.org.foodrev.presentation.presenters.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import foodrev.org.foodrev.domain.models.Coordinator;
import foodrev.org.foodrev.presentation.presenters.UserTypePresenter;

/**
 * Created by darver on 5/15/17.
 */

public class UserTypePresenterImpl implements UserTypePresenter {
    private UserTypePresenter.View mView;

    @Override
    public void attachView(View view) {
        mView = view;
    }

    @Override
    public void coordinatorSelected() {
        // TODO: do checking with an associated user hash instead
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("COORDINATORS");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot coordinator : dataSnapshot.getChildren()) {
                    TempCoordinator tc = coordinator.getValue(TempCoordinator.class);
                    if (uid.equals(tc.getCoordinatorOauth())) {
                        mView.goToCoordinatorMode();
                    }
                }
                mView.showError("Not a coordinator");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.showError(databaseError.getMessage());
            }
        });
    }

    public static class TempCoordinator {
        private String coordinatorName;
        private String coordinatorOauth;

        public TempCoordinator() {

        }

        public String getCoordinatorName() {
            return this.coordinatorName;
        }

        public String getCoordinatorOauth() {
            return this.coordinatorOauth;
        }

    }

    @Override
    public void driverSelected() {
        // Look for driver associated with auth hash

        mView.goToDriverMode();
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void resume() {

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

}
