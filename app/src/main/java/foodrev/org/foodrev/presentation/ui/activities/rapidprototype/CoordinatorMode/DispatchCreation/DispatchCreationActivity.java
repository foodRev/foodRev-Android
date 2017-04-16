package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.CoordinatorMainLanding.CoordinatorMainActivity;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.CommunitySelect.DispatchCommunitySelect;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.DateTime.DispatchDateTimeSelect;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.DonorSelect.DispatchDonorSelect;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.DriverSelect.DispatchDriverSelect;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DonorCommunityPairCreation.DonorCommunityPairSelect;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverAssignment.DriverAssignmentActivity;


public class DispatchCreationActivity extends AppCompatActivity {

    View selectDonorCard;
    View selectTimeCard;
    View selectDriversCard;
    View selectCommunitiesCard;
    View taskAssignmentCard;

    Intent coordinatorMainIntent;
    String dispatchKey;
    //Firebase
    private FirebaseDatabase firebaseDatabase;
    // Dispatch Root
    private DatabaseReference dispatchRoot; //driving/unloading/loading

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DispatchCreationActivity.this, CoordinatorMainActivity.class);
                startActivity(i);
                //writeNewPost();
            }
        });

        setupFirebase();

        coordinatorMainIntent = getIntent();
        dispatchKey = coordinatorMainIntent.getStringExtra("dispatch_key");

        selectDonorCard = findViewById(R.id.dispatch_donor_card);
        selectTimeCard = findViewById(R.id.dispatch_time_card);
        selectDriversCard = findViewById(R.id.dispatch_drivers_card);
        selectCommunitiesCard = findViewById(R.id.dispatch_communities_card);
        taskAssignmentCard = findViewById(R.id.dispatch_task_assignment_card);

        //TODO refactor into single recycler view with intent modes for driver/donor/community
        selectDonorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DispatchCreationActivity.this, DispatchDonorSelect.class);
                intent.putExtra("dispatch_key", dispatchKey);
                startActivity(intent);
            }
        });

        selectTimeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DispatchCreationActivity.this,  DispatchDateTimeSelect.class);
                intent.putExtra("dispatch_key", dispatchKey);
                startActivity(intent);
            }
        });

        selectDriversCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DispatchCreationActivity.this, DispatchDriverSelect.class);
                intent.putExtra("dispatch_key", dispatchKey);
                startActivity(intent);
            }
        });

        selectCommunitiesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DispatchCreationActivity.this, DispatchCommunitySelect.class);
                intent.putExtra("dispatch_key", dispatchKey);
                startActivity(intent);
            }
        });

        //TODO task Assignment
        taskAssignmentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DispatchCreationActivity.this, DonorCommunityPairSelect.class);
                intent.putExtra("dispatch_key", dispatchKey);
                startActivity(intent);
            }
        });
    }

    private void setupFirebase() {

        //TODO get dispatch list once and place into Dispatch object list
        firebaseDatabase = FirebaseDatabase.getInstance();

        //dispatch Root
        dispatchRoot = firebaseDatabase.getReference("/DISPATCHES");
    }
    // TODO add wiki doc on how to create hashmap -> firebase bulk write
//    private void writeNewPost() {
//        // Create new post at /user-posts/$userid/$postid and at
//        // /posts/$postid simultaneously
//        String key = dispatchRoot.push().getKey();
//        Dispatch dispatch = new Dispatch("DispatchID", "Time and Date", NEED_TO_PLAN);
//        Map<String, Object> dispatchValues = dispatch.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put(key, dispatchValues);
//
//        dispatchRoot.updateChildren(childUpdates);
//    }
}
