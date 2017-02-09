package foodrev.org.foodrev.storage;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import foodrev.org.foodrev.domain.wrappers.FirebaseDatabaseWrapper;

/**
 * Created by darver on 2/7/17.
 */

public class FirebaseDB {
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    private FirebaseDB() {
    }

    public FirebaseDatabase getInstance() {
        if(mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
        }
        return mDatabase;
    }








}
