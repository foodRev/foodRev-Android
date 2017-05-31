package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.EntityAddTest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.Builders.DispatchDonorBuilder;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchCommunity;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchCoordinator;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDriver;

public class AddFirebaseEntity extends AppCompatActivity {

    private Button addDriver;
    private Button addDonor;
    private Button addCommunity;
    private Button addCoordinator;

    //Firebase
    private FirebaseDatabase firebaseDatabase;

    // Firebase Roots
    private DatabaseReference driverRoot;
    private DatabaseReference donorRoot;
    private DatabaseReference communityRoot;
    private DatabaseReference coordinatorRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_firebase_entity);

        setupFirebase();
        setupButtons();
    }

    private void setupFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();

        driverRoot = firebaseDatabase.getReference("/DRIVERS");
        donorRoot = firebaseDatabase.getReference("/DONORS");
        communityRoot = firebaseDatabase.getReference("/COMMUNITIES");
        coordinatorRoot = firebaseDatabase.getReference("/COORDINATORS");
    }

    private void setupButtons() {
        addDriver = (Button) findViewById(R.id.add_driver);
        addDonor = (Button) findViewById(R.id.add_donor);
        addCommunity = (Button) findViewById(R.id.add_community);
        addCoordinator = (Button) findViewById(R.id.add_coordinator);

        addDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewDriver();
            }
        });
        addDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewDonor();
            }
        });
        addCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCommunity();
            }
        });
        addCoordinator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCoordinator();
            }
        });
    }

    private void addNewDriver() {
        String driverKey = driverRoot.push().getKey();
        DispatchDriver newDriver = new DispatchDriver("Kauna Lei", 1);
        Map<String,Object> driverValues = newDriver.toMap();

        Map<String,Object> postDriver = new HashMap<>();
        postDriver.put(driverKey,driverValues);

        driverRoot.updateChildren(postDriver);
    }
    private void addNewDonor() {
        String donorKey = donorRoot.push().getKey();
        DispatchDonor newDonor = new DispatchDonorBuilder().setDonorName("Donor M").setCarsOfFood(8).setLatitude(37.7955703).setLongitude(-122.3955095).createDispatchDonor();
        Map<String,Object> donorValues = newDonor.toMap();

        Map<String,Object> postDonor = new HashMap<>();
        postDonor.put(donorKey,donorValues);

        donorRoot.updateChildren(postDonor);
    }
    private void addNewCommunity() {
        String communityKey = communityRoot.push().getKey();
        DispatchCommunity newCommunity = new DispatchCommunity("ECS Alder", 8, 37.7800849,-122.4097458);
        Map<String,Object> communityValues = newCommunity.toMap();

        Map<String,Object> postCommunity = new HashMap<>();
        postCommunity.put(communityKey,communityValues);

        communityRoot.updateChildren(postCommunity);
    }
    private void addNewCoordinator() {
        String coordinatorKey = coordinatorRoot.push().getKey();
        DispatchCoordinator newCoordinator = new DispatchCoordinator("David Breitzmann", "asdfj43fnoq4jflaef");
        Map<String,Object> coordinatorValues = newCoordinator.toMap();

        Map<String,Object> postCoordinator = new HashMap<>();
        postCoordinator.put(coordinatorKey,coordinatorValues);

        coordinatorRoot.updateChildren(postCoordinator);
    }
}
