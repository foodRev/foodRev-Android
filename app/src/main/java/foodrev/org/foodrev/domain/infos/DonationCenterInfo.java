package foodrev.org.foodrev.domain.infos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import foodrev.org.foodrev.domain.interactors.impl.GetFirebaseInfoInteractorImpl;

public class DonationCenterInfo extends DestinationInfo {
    public DonationCenterInfo(FirebaseDatabase firebaseDatabase, GetFirebaseInfoInteractorImpl.Callback callback) {
        super(firebaseDatabase, callback);
        DatabaseReference ref = firebaseDatabase.getReference("donation_centers/");
        ref.addValueEventListener(super.mListener);
    }

    @Override
    protected void updateData(DataSnapshot snapshot) {
        super.updateData(snapshot);
        if(super.mCallback != null) {
            super.mCallback.onDonationCenterInfoUpdated();
        }
    }
}