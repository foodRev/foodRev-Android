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

import com.google.android.gms.vision.text.Text;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        dispatchCalendarDate = (TextView) findViewById(R.id.date_select_display);

        startHourField = (EditText) findViewById(R.id.start_hour_input) ;
        startMinuteField = (EditText) findViewById(R.id.start_minute_input) ;

        endHourField = (EditText) findViewById(R.id.end_hour_input) ;
        endMinuteField = (EditText) findViewById(R.id.end_minute_input) ;

        startAmPm = (ToggleButton) findViewById(R.id.start_time_am_pm_input);
        endAmPm = (ToggleButton) findViewById(R.id.end_time_am_pm_input);

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
                Toast.makeText(DispatchDateTimeSelect.this, "Date-Time update sent to cloud", Toast.LENGTH_SHORT).show();

                dispatchRoot.child(dispatchKey)
                        .child("DISPATCH_WINDOW")
                        .child("START_TIME")
                        .setValue(startHourField.getText() + ":" + startMinuteField.getText() + " " +  startAmPm.getText());

                dispatchRoot.child(dispatchKey)
                        .child("DISPATCH_WINDOW")
                        .child("END_TIME")
                        .setValue(endHourField.getText() + ":" + endMinuteField.getText() + " " +  endAmPm.getText());

                dispatchRoot.child(dispatchKey)
                        .child("DISPATCH_WINDOW")
                        .child("CALENDAR_DATE")
                        .setValue(dispatchCalendarDate.getText());
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
