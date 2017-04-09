package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.DateTime;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchCommunity;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.CommunitySelect.DispatchCommunitySelect;

public class DispatchDateTimeSelect extends AppCompatActivity {

    // date times
    Button calendarDatePickButton;

    // Firebase
    private FirebaseDatabase firebaseDatabase;
    // Dispatch Root
    private DatabaseReference dispatchRoot; //driving/unloading/loading
    Intent intent;
    String dispatchKey;

    TextView dispatchCalendarDate;

    EditText startHourField;
    EditText startMinuteField;

    EditText endHourField;
    EditText endMinuteField;

    ToggleButton startAmPm;
    ToggleButton endAmPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_date_time_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();
        dispatchKey = intent.getStringExtra("dispatch_key");

        setupInputs();
        setupFirebase();
        populateDateTime();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupInputs(){
        dispatchCalendarDate = (TextView) findViewById(R.id.date_select_display);

        startHourField = (EditText) findViewById(R.id.start_hour_input) ;
        startMinuteField = (EditText) findViewById(R.id.start_minute_input) ;

        endHourField = (EditText) findViewById(R.id.end_hour_input) ;
        endMinuteField = (EditText) findViewById(R.id.end_minute_input) ;

        startAmPm = (ToggleButton) findViewById(R.id.start_time_am_pm_input);
        endAmPm = (ToggleButton) findViewById(R.id.end_time_am_pm_input);

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
                sendDateTimeUpdate();
            }

        });
    }

    private void sendDateTimeUpdate() {
        Toast.makeText(DispatchDateTimeSelect.this, "Date-Time update sent to cloud", Toast.LENGTH_SHORT).show();

        HashMap<String,Object> timeUpdates = new HashMap<>();

        timeUpdates.put("START_TIME",startHourField.getText() + ":" + startMinuteField.getText() + " " +  startAmPm.getText());
        timeUpdates.put("END_TIME",endHourField.getText() + ":" + endMinuteField.getText() + " " +  endAmPm.getText());
        timeUpdates.put("CALENDAR_DATE",dispatchCalendarDate.getText());

        dispatchRoot.child(dispatchKey)
                .child("DISPATCH_WINDOW")
                .setValue(timeUpdates);
    }

    private void setupFirebase() {

            //TODO get dispatch list once and place into Dispatch object list
            firebaseDatabase = FirebaseDatabase.getInstance();

        //dispatch Root
        dispatchRoot = firebaseDatabase.getReference("/DISPATCHES");
            //dispatch Root
            dispatchRoot = firebaseDatabase.getReference("/DISPATCHES");
        }

    private void populateDateTime() {
        dispatchRoot.child(dispatchKey).child("DISPATCH_WINDOW").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("START_TIME").getValue() != null) {
                    startHourField.setText(dataSnapshot.child("START_TIME").getValue().toString().split(":")[0]);
                    startMinuteField.setText(dataSnapshot.child("START_TIME").getValue().toString().split(":")[1].split(" ")[0]);
                    startAmPm.setChecked(dataSnapshot.child("START_TIME").getValue().toString().split(":")[1].split(" ")[1].equals("PM"));
                }

                if (dataSnapshot.child("END_TIME").getValue() != null) {
                    endHourField.setText(dataSnapshot.child("END_TIME").getValue().toString().split(":")[0]);
                    endMinuteField.setText(dataSnapshot.child("END_TIME").getValue().toString().split(":")[1].split(" ")[0]);
                    endAmPm.setChecked(dataSnapshot.child("END_TIME").getValue().toString().split(":")[1].split(" ")[1].equals("PM"));
                }

                if (dataSnapshot.child("CALENDAR_DATE").getValue() != null) {
                    dispatchCalendarDate.setText(dataSnapshot.child("CALENDAR_DATE").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e("DispatchDateTimeSelect", "onCancelled: " + databaseError.getMessage() );
            }
        });

    }

}
