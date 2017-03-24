package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.DateTime;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import foodrev.org.foodrev.R;

public class DispatchDateTimeSelect extends AppCompatActivity {

    // date times
    Button calendarDatePickButton;
    TextView dateValue;

    // start and end times
    EditText startHour;
    EditText startMin;
    EditText endHour;
    EditText endMin;

    ToggleButton startAmPm;
    ToggleButton endAmPm;

    // Firebase
    private FirebaseDatabase firebaseDatabase;
    // Dispatch Root
    private DatabaseReference dispatchRoot; //driving/unloading/loading
    Intent intent;
    String dispatchKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_date_time_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();
        dispatchKey = intent.getStringExtra("dispatch_key");

        setupFirebase();

        calendarDatePickButton = (Button) findViewById(R.id.date_select_button);

        calendarDatePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateSelectFragment calendarFragment = new DateSelectFragment();
                calendarFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(DispatchDateTimeSelect.this, "Date and Time Set", Toast.LENGTH_SHORT).show();

                dispatchRoot.child(dispatchKey).child("date").setValue("1");
                dispatchRoot.child(dispatchKey).child("time").setValue("0");

            }

        });

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

        private void setupFirebase() {

            //TODO get dispatch list once and place into Dispatch object list
            firebaseDatabase = FirebaseDatabase.getInstance();

            //dispatch Root
            dispatchRoot = firebaseDatabase.getReference("/DISPATCHES");
        }

}
