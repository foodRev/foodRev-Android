package foodrev.org.foodrev.domain.infos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by darver on 2/23/17.
 */

public class InfoUpdateListener implements ValueEventListener {
    AbstractInfo mParent;

    public InfoUpdateListener(AbstractInfo parent) {
        mParent = parent;
    }

    @Override
    public void onDataChange(DataSnapshot snapshot) {
        mParent.updateData(snapshot);
    }

    @Override
    public void onCancelled(DatabaseError error) {
        mParent.updateError(error.getMessage());
    }
}
