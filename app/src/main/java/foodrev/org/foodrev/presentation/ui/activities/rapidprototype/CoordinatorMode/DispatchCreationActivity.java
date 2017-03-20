package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.Dispatch;

import static foodrev.org.foodrev.domain.models.dispatchModels.Dispatch.DispatchStatus.NEED_TO_PLAN;
import static foodrev.org.foodrev.domain.models.dispatchModels.Dispatch.DispatchStatus.PLAN_PREPARED;
import static java.security.AccessController.getContext;


public class DispatchCreationActivity extends AppCompatActivity {

    View selectDonorCard;
    View selectTimeCard;
    View selectDriversCard;
    View selectCommunitiesCard;

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
                writeNewPost();
            }
        });

        setupFirebase();

        selectDonorCard = findViewById(R.id.dispatch_donor_card);
        selectTimeCard = findViewById(R.id.dispatch_time_card);
        selectDriversCard = findViewById(R.id.dispatch_drivers_card);
        selectCommunitiesCard = findViewById(R.id.dispatch_communities_card);

        selectDonorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DispatchCreationActivity.this, DispatchCreationActivity.class);
                intent.putExtra("mode", "donor");
                startActivity(intent);
            }
        });

        selectTimeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DispatchCreationActivity.this,  DispatchDateTimeSelect.class);
                intent.putExtra("mode", "time");
                startActivity(intent);
            }
        });

        selectDriversCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DispatchCreationActivity.this, DispatchCreationActivity.class);
                intent.putExtra("mode", "drivers");
                startActivity(intent);
            }
        });

        selectCommunitiesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DispatchCreationActivity.this, DispatchCreationActivity.class);
                intent.putExtra("mode", "communities");
                startActivity(intent);
            }
        });
    }

    private void setupFirebase() {

        //TODO get dispatch list once and place into Dispatch object list
        firebaseDatabase = FirebaseDatabase.getInstance();

        //dispatch Root
        dispatchRoot = firebaseDatabase.getReference("/DISPATCH");
    }

    private void writeNewPost() {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = dispatchRoot.push().getKey();
        Dispatch dispatch = new Dispatch("DispatchID", "Time and Date", NEED_TO_PLAN);
        Map<String, Object> dispatchValues = dispatch.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, dispatchValues);

        dispatchRoot.updateChildren(childUpdates);
    }
}
