package com.example.todoapp_material.app;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.todoapp_material.app.ToDoItem.Priority;
import com.example.todoapp_material.app.ToDoItem.Status;

import java.util.Calendar;
import java.util.Date;

public class AddToDoFragment extends Fragment {

    public interface onClickSubmit{
        public void OnSubmit(Bundle data);
    }

    private onClickSubmit mCallback;

    // 7 days in milliseconds - 7 * 24 * 60 * 60 * 1000
    private static final int SEVEN_DAYS = 604800000;

    private static final String TAG = "Lab-UserInterface";

    private static String timeString;
    private static String dateString;
    private static TextView dateView;
    private static TextView timeView;

    private Date mDate;
    private RadioGroup mPriorityRadioGroup;
    private RadioGroup mStatusRadioGroup;
    private EditText mTitleText;
    private RadioButton mDefaultStatusButton;
    private RadioButton mDefaultPriorityButton;


    public AddToDoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_to_do, container, false);

        mTitleText = (EditText) view.findViewById(R.id.title);
        mDefaultStatusButton = (RadioButton) view.findViewById(R.id.statusNotDone);
        mDefaultPriorityButton = (RadioButton) view.findViewById(R.id.medPriority);
        mPriorityRadioGroup = (RadioGroup) view.findViewById(R.id.priorityGroup);
        mStatusRadioGroup = (RadioGroup) view.findViewById(R.id.statusGroup);
        dateView = (TextView) view.findViewById(R.id.date);
        timeView = (TextView) view.findViewById(R.id.time);

        // Set the default date and time

        setDefaultDateTime();

        // OnClickListener for the Date button, calls showDatePickerDialog() to
        // show the Date dialog

        final Button datePickerButton = (Button) view.findViewById(R.id.date_picker_button);
        datePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // OnClickListener for the Time button, calls showTimePickerDialog() to
        // show the Time Dialog

        final Button timePickerButton = (Button) view.findViewById(R.id.time_picker_button);
        timePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        // OnClickListener for the Cancel Button,

        final Button cancelButton = (Button) view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStackImmediate();

                // TODO - Indicate result and finish



            }
        });

        // TODO - Set up OnClickListener for the Reset Button
        final Button resetButton = (Button) view.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // TODO - Reset data to default values





                mTitleText.setText("");
                mPriorityRadioGroup.check(mDefaultPriorityButton.getId());
                mStatusRadioGroup.check(mDefaultStatusButton.getId());

                // reset date and time
                setDefaultDateTime();

            }
        });

        // Set up OnClickListener for the Submit Button

        final Button submitButton = (Button) view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // gather ToDoItem data


                // TODO - Get the current Priority
                Priority priority = getPriority();

                // TODO - Get the current Status
                Status status = getStatus();

                // TODO - Get the current ToDoItem Title
                String titleString = getToDoTitle();


                // Construct the Date string
                String fullDate = dateString + " " + timeString;

                // Package ToDoItem data into an Intent
//                Intent data = new Intent(AddToDoActivity.this,ToDoManagerActivity.class);
//                ToDoItem.packageIntent(data, titleString, priority, status,
//                        fullDate);
//
//
//                setResult(RESULT_OK,data);
//                finish();

                // TODO - return data Intent and finish

                Bundle data = ToDoItem.packageBundle(titleString,priority,status,fullDate);
                mCallback.OnSubmit(data);




            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Make sure that the hosting Activity has implemented
        // the SelectionListener callback interface. We need this
        // because when an item in this ListFragment is selected,
        // the hosting Activity's onItemSelected() method will be called.

        try {

            mCallback = (onClickSubmit) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SelectionListener");
        }
    }

    // Do not modify below this point.

    private void setDefaultDateTime() {

        // Default is current time + 7 days
        mDate = new Date();
        mDate = new Date(mDate.getTime() + SEVEN_DAYS);

        Calendar c = Calendar.getInstance();
        c.setTime(mDate);

        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));

        dateView.setText(dateString);

        setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                c.get(Calendar.MILLISECOND));

        timeView.setText(timeString);
    }

    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {

        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        dateString = year + "-" + mon + "-" + day;
    }

    private static void setTimeString(int hourOfDay, int minute, int mili) {
        String hour = "" + hourOfDay;
        String min = "" + minute;

        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;

        timeString = hour + ":" + min + ":00";
    }

    private Priority getPriority() {

        switch (mPriorityRadioGroup.getCheckedRadioButtonId()) {
            case R.id.lowPriority: {
                return Priority.LOW;
            }
            case R.id.highPriority: {
                return Priority.HIGH;
            }
            default: {
                return Priority.MED;
            }
        }
    }

    private Status getStatus() {

        switch (mStatusRadioGroup.getCheckedRadioButtonId()) {
            case R.id.statusDone: {
                return Status.DONE;
            }
            default: {
                return Status.NOTDONE;
            }
        }
    }

    private String getToDoTitle() {
        return mTitleText.getText().toString();
    }


    // DialogFragment used to pick a ToDoItem deadline date

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            setDateString(year, monthOfYear, dayOfMonth);

            dateView.setText(dateString);
        }

    }

    // DialogFragment used to pick a ToDoItem deadline time

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return
            return new TimePickerDialog(getActivity(), this, hour, minute, true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setTimeString(hourOfDay, minute, 0);

            timeView.setText(timeString);
        }
    }

    private void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");

    }

    private void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }


}
