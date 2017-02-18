package foodrev.org.foodrev.domain.infos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CommunityCenterInfo extends DestinationInfo {
    public CommunityCenterInfo(FirebaseDatabase firebaseDatabase) {
        super(firebaseDatabase);
        DatabaseReference ref = firebaseDatabase.getReference("community_centers/");
        ref.addValueEventListener(super.mListener);
    }

//    @Override
//    protected void updateData(DataSnapshot snapshot) {
//        super.updateData(snapshot);
//        if(mAllDataReceived != null) {
//            super.mAllDataReceived.receivedCommunityCenters();
//        }
//    }
}
