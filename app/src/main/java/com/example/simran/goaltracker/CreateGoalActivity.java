package com.example.simran.goaltracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class CreateGoalActivity extends AppCompatActivity implements DatePickerFragment.OnDateSelectedListener, TimePickerFragment.OnTimeSelectedListener{

    DialogFragment newDateFragment;
    DialogFragment newTimeFragment;
    int year, month, day, hourOfDay, minute;
    boolean isDateSet = false;
    boolean isTimeSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * listner for the Submit button
     * Called when user clicks the Submit button.
     * Saves goal in a database.
     * @param view
     */
    public void saveGoalInDataBase(View view){
        //instantiates the database
        GoalDBHelper mDbHelper = GoalDBHelper.getSingletonInstance(this);
        Log.i("start","instantiating db");
        System.out.println("instantiating db");

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //get the goal that the user inputs in the text box
        EditText editText = (EditText) findViewById(R.id.type_new_goal);
        String goal = editText.getText().toString();


        if(isDateSet && isTimeSet){
            Log.d("status", "date and time have been successfully set");
        //insert some data into a database
        mDbHelper.insertGoal(db, goal,year,month,day,hourOfDay,minute);
        }
        else
        {
            Log.d("insert error", "date and time are not set");
        }

    }

    public void showTimePickerDialog(View v) {
        newTimeFragment = new TimePickerFragment();
        newTimeFragment.show(getFragmentManager(), "timePicker");
/*
        FragmentManager fm = getFragmentManager();
        TimePickerFragment dialogFragment = new TimePickerFragment ();
        dialogFragment.show(fm, "Sample Fragment");*/
    }

    public void showDatePickerDialog(View v) {
        newDateFragment = new DatePickerFragment();
        newDateFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSelected(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        isDateSet = true;
    }

    @Override
    public void onTimeSelected(int hourOfDay, int minute) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        isTimeSet = true;
    }


    /**
     * listner for the Submit button
     * called when user clicks the Submit button
     * saves goal in a preference file
     * @param view
     */
  /*  public void saveGoal(View view){
        //SharedPreferences sharedPref = this.getSharedPreferences(
        //getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //get the file to store the goal data
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        //editor to edit the file
        SharedPreferences.Editor editor = sharedPref.edit();

        //get the goal that the user inputs in the text box
        EditText editText = (EditText) findViewById(R.id.type_new_goal);
        String goal = editText.getText().toString();

        //store the goal in the file
        editor.putString(getString(R.string.goal_key), goal);
        editor.commit();

    }*/

}
