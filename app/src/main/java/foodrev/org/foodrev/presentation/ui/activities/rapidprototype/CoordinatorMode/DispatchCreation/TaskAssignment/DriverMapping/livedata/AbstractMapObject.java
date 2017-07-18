package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import javax.inject.Inject;

import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.Interfaces.MapObject;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.Interfaces.MappableObject;

/**
 * Created by foodRev on 7/17/17.
 */

public abstract class AbstractMapObject {

    // Live Map Objects
    protected HashMap<String, MapObject> hashMapMapObject;

    // deliverable
    protected MutableLiveData<HashMap<String, MapObject>> mapObject;

    // for holding listeners
    protected HashMap<String, ValueEventListener> mapObjectListeners;

    // Firebase References
    protected DatabaseReference rootReference;
    protected DatabaseReference mapObjectReference;

    // path for Firebase subdirectory reference
    protected final String firebasePath;

    // Constructor
    public AbstractMapObject(String dispatchKey, String firebasePath) {
        hashMapMapObject = new HashMap<>();
        mapObjectListeners = new HashMap<>();

        this.firebasePath = firebasePath;

        // set references
        rootReference = FirebaseDatabase.getInstance().getReference("/");
        mapObjectReference = FirebaseDatabase.getInstance()
                .getReference("/DISPATCHES/" + dispatchKey);
    }


    protected void loadMapObjects() {
       mapObjectReference.child(firebasePath).addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               String snapshotKey = dataSnapshot.getKey().toString();
               if (!hashMapMapObject.containsKey(snapshotKey)) {
                   addMapObjectListener(snapshotKey);
               }
           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {
               // loop to hashmap, verify that each key is still in list
               // else remove the key

               String snapshotKey = null;

               snapshotKey = dataSnapshot.getKey().toString();

               if(hashMapMapObject.containsKey(snapshotKey)) {

                   // remove listener
                   removeMapObjectListener(snapshotKey);

                   // remove from hashmap
                   hashMapMapObject.remove(snapshotKey);

                   // update mapobject list
                   mapObject.setValue(hashMapMapObject);


               }
           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

           @Override
           public void onCancelled(DatabaseError databaseError) {}
       });
    }

    protected void addMapObjectListener(String mapObjectHashId) {

        ValueEventListener valueEventListener = rootReference
                .child(firebasePath)
                .child(mapObjectHashId)
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (hashMapMapObject.containsKey(mapObjectHashId)) {
                                    // update map object lat long and other stats
                                    updateMapObjectStats(dataSnapshot, mapObjectHashId);
                                } else {
                                    // add map object to hashmap and init lat long
                                    initializeMapObjectStats(dataSnapshot, mapObjectHashId);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );

        // save the value event listeners in a hashtable with the hashId as key
        mapObjectListeners.put(mapObjectHashId, valueEventListener);
    }


    protected void removeMapObjectListener(String mapObjectHashId) {
        // removes listeners
        rootReference.removeEventListener(mapObjectListeners.get(mapObjectHashId));
        mapObjectListeners.remove(mapObjectHashId);
    }

    // abstract methods
    public abstract LiveData<HashMap<String,MappableObject>> getMappableObjects();
    public abstract void updateMapObjectStats(DataSnapshot dataSnapshot, String mapObjectHashId);
    public abstract void initializeMapObjectStats(DataSnapshot dataSnapshot, String mapObjectHashId);

    // getters and setters
    public HashMap<String, MapObject> getHashMapMapObject() {
        return hashMapMapObject;
    }

    public void setHashMapMapObject(HashMap<String, MapObject> hashMapMapObject) {
        this.hashMapMapObject = hashMapMapObject;
    }

    public MutableLiveData<HashMap<String, MapObject>> getMapObject() {
        return mapObject;
    }

    public void setMapObject(MutableLiveData<HashMap<String, MapObject>> mapObject) {
        this.mapObject = mapObject;
    }

    public HashMap<String, ValueEventListener> getMapObjectListeners() {
        return mapObjectListeners;
    }

    public void setMapObjectListeners(HashMap<String, ValueEventListener> mapObjectListeners) {
        this.mapObjectListeners = mapObjectListeners;
    }

    public DatabaseReference getRootReference() {
        return rootReference;
    }

    public void setRootReference(DatabaseReference rootReference) {
        this.rootReference = rootReference;
    }

    public DatabaseReference getMapObjectReference() {
        return mapObjectReference;
    }

    public void setMapObjectReference(DatabaseReference mapObjectReference) {
        this.mapObjectReference = mapObjectReference;
    }

    public String getFirebasePath() {
        return firebasePath;
    }
}
