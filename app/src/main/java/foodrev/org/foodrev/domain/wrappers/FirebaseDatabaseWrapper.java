package foodrev.org.foodrev.domain.wrappers;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by darver on 2/8/17.
 */

// TODO: find out which class should be testable
public class FirebaseDatabaseWrapper {
    public FirebaseDatabase getInstance() {
        return FirebaseDatabase.getInstance();
    }
}
