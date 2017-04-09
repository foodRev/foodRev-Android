package foodrev.org.foodrev.domain.infos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import foodrev.org.foodrev.domain.interactors.impl.GetFirebaseInfoInteractorImpl;

public class DonationCenterInfo extends DestinationInfo {

    String infoType = DONOR_TITLE;

    public DonationCenterInfo(FirebaseDatabase firebaseDatabase, GetFirebaseInfoInteractorImpl.Callback callback) {
        super(firebaseDatabase, callback);
        DatabaseReference ref = firebaseDatabase.getReference("DONORS");
        ref.addValueEventListener(super.mListener);
    }

    @Override
    protected void updateData(DataSnapshot snapshot) {
        super.updateData(snapshot);

        if (snapshot.getValue() != null) {
            super.mCallback.onDonationCenterInfoUpdated(this);
        }
    }
}