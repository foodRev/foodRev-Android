package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.ai;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import foodrev.org.foodrev.R;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    static TimePickerFragment newInstance(int num) {
        TimePickerFragment f = new TimePickerFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        TextView textView = (TextView) getActivity().findViewById(R.id.display_time);
        textView.setText(timeConversion(hourOfDay,minute));
        Button button = (Button) getActivity().findViewById(R.id.time_picker_button);
        button.setText(timeConversion(hourOfDay,minute));
    }

    public String timeConversion(int hourOfDay, int minute){
        String timeString;

        if(hourOfDay > 12){
            hourOfDay = hourOfDay % 12;

            timeString = hourOfDay + ":" + minute + " pm";

            return timeString;
        } else if (hourOfDay == 0){
            hourOfDay = 12;
        }

        timeString = hourOfDay + ":" + minute + " am";
        return timeString;
    }
}