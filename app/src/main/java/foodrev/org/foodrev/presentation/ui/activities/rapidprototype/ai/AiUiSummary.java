package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.ai;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import foodrev.org.foodrev.R;

public class AiUiSummary extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RelativeLayout contentSummary;
    FloatingActionButton editButton;

    TextView displayDate;
    TextView displayTime;
    TextView displayLocation;
    TextView displayQuantity;

    Button datePicker;
    Button timePicker;
    Spinner locationPicker;
    EditText quantityPicker;

    Button moreButton;

    Button newDestination;
    EditText addDetails;

    boolean editable = false;
    boolean showMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = 1.0f;
        params.dimAmount = 0.5f;
        this.getWindow().setAttributes(params);

        // This sets the window size, while working around the IllegalStateException thrown by ActionBarView
        this.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        this.getWindow().setLayout(500,800);      //for lower resolution phones

        setContentView(R.layout.activity_summary_aiui);
//        setHasOptionsMenu(true);

        initializeItems();
        displayView();

        //core functionality
        editButtonStuff();
        dateTimeStuff();
        locationSpinnerStuff();
        quantityPickerStuff();

        moreButton();
        hideMore();

        editButton.performClick();
    }

    public void setFocusListeners(View v){
        v.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    hideSoftKeyboard();
                    editButton.hasFocus();
                }
            }
        });
    }

    public void initializeItems(){
        contentSummary = (RelativeLayout) findViewById(R.id.content_summary_aiui);
        editButton = (FloatingActionButton) findViewById(R.id.edit_button);

        displayDate = (TextView) findViewById(R.id.display_date);
        displayTime = (TextView) findViewById(R.id.display_time);
        displayLocation = (TextView) findViewById(R.id.display_location);
        displayQuantity = (TextView) findViewById(R.id.display_quantity);

        datePicker = (Button) findViewById(R.id.date_picker_button);
        timePicker = (Button) findViewById(R.id.time_picker_button);
        locationPicker = (Spinner)findViewById(R.id.location_spinner);
        quantityPicker = (EditText) findViewById(R.id.edit_quantity);

        moreButton = (Button) findViewById(R.id.more_button);

        newDestination = (Button) findViewById(R.id.new_destination);
        addDetails = (EditText) findViewById(R.id.add_details);

        editButton.setFocusableInTouchMode(true);
        contentSummary.setFocusableInTouchMode(true);

        setFocusListeners(contentSummary);
        setFocusListeners(editButton);
    }

    public void displayView(){
        datePicker.setVisibility(View.GONE);
        timePicker.setVisibility(View.GONE);
        locationPicker.setVisibility(View.GONE);
        quantityPicker.setVisibility(View.GONE);
        moreButton.setVisibility(View.GONE);

        displayDate.setVisibility(View.VISIBLE);
        displayTime.setVisibility(View.VISIBLE);
        displayLocation.setVisibility(View.VISIBLE);
        displayQuantity.setVisibility(View.VISIBLE);
    }

    public void editView(){
        displayDate.setVisibility(View.GONE);
        displayTime.setVisibility(View.GONE);
        displayLocation.setVisibility(View.GONE);
        displayQuantity.setVisibility(View.GONE);

        datePicker.setVisibility(View.VISIBLE);
        timePicker.setVisibility(View.VISIBLE);
        locationPicker.setVisibility(View.VISIBLE);
        quantityPicker.setVisibility(View.VISIBLE);
        moreButton.setVisibility(View.VISIBLE);

    }

    public void editButtonStuff(){
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editable == false){
                    //set fields to textviews and hide mutable fields
                    displayView();

                    hideMore();
                    showMore = false;
                }
                else if(editable == true){
                    //set fields to editable and hide textviews
                    editView();
                }
                editable = !editable;
            }
        });
    }


    public void hideSoftKeyboard() {
        InputMethodManager imm =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(quantityPicker.getWindowToken(), 0);
    }

    public void dateTimeStuff(){
            //set fields to editable and hide textviews
            datePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDatePickerDialog(view);
                }
            });

            timePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showTimePickerDialog(view);
                }
            });
    }

    public void showDatePickerDialog(View v) {
//                mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
//                DialogFragment newFragment = TimePickerFragment.newInstance(mStackLevel);
        DialogFragment newFragment = DatePickerFragment.newInstance(1);
        newFragment.show(ft, "dialog");
    }
    public void showTimePickerDialog(View v) {
//                mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
//                DialogFragment newFragment = TimePickerFragment.newInstance(mStackLevel);
        DialogFragment newFragment = TimePickerFragment.newInstance(1);
        newFragment.show(ft, "dialog");
    }

    public void locationSpinnerStuff(){
        locationPicker.setOnItemSelectedListener(this);

        List<String> items = new ArrayList<>();

        items.add("No selection");
        items.add(items.size() + " Community Center");
        items.add(items.size() + " Jenny's House");
        items.add(items.size() + " Burger King");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_dropdown_item, items);
        locationPicker.setAdapter(adapter);
    }

    public void quantityPickerStuff(){
        quantityPicker.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String quantityString = s + " items";
                displayQuantity.setText(quantityString);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }
    public void moreButton(){
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                hideMore();
                if(showMore == false){
                    //set fields to textviews and hide mutable fields
                    displayMore();
                }
                else if(showMore == true){
                    //set fields to editable and hide textviews
                    hideMore();
                }

                showMore = !showMore;
            }
        });
    }

    public void displayMore(){
        newDestination.setVisibility(View.VISIBLE);
        addDetails.setVisibility(View.VISIBLE);
        moreButton.setText("less");
    }

    public void hideMore(){
        newDestination.setVisibility(View.GONE);
        addDetails.setVisibility(View.GONE);
        moreButton.setText("more");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        displayLocation.setText(item);
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart(){
        super.onStart();
    }
}
