package foodrev.org.foodrev.storage;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by darver on 2/7/17.
 */

public class FirebaseDB {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private FirebaseDB() {
    }

    private DatabaseReference getDataBaseInstance() {
        if(mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
        return mDatabase;
    }


}
