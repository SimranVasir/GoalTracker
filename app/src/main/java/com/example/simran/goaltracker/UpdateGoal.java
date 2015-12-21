package com.example.simran.goaltracker;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateGoal extends AppCompatActivity implements DatePickerFragment.OnDateSelectedListener, TimePickerFragment.OnTimeSelectedListener {

    DialogFragment newDateFragment;
    DialogFragment newTimeFragment;
    Goal goal;
    String newGoal;
    int year, month, day, hourOfDay, minute;
    boolean isDateSet = false;
    boolean isTimeSet = false;
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.UpdateGoalActivity.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_goal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        goal = new Goal();
        Intent intent = getIntent();
        String goalName = intent.getStringExtra(GoalActivity.EXTRA_MESSAGE);
        goal.setGoalName(goalName);


        //get GoalDBHelper instance
        GoalDBHelper mDbHelper = GoalDBHelper.getSingletonInstance(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //get old Goal data
        Goal goalOld = mDbHelper.querySingleGoalData(db, goalName);
        goal.setGoalId(goalOld.getGoalId());
        goal.setGoalName(goalOld.getGoalName());
        goal.setYear(goalOld.getYear());
        goal.setMonth(goalOld.getMonth());
        goal.setDay(goalOld.getDay());
        goal.setHourOfDay(goalOld.getHourOfDay());
        goal.setMinute(goalOld.getMinute());
        goal.setEstHours(goalOld.getEstHours());
        goal.setIsCompleted(goalOld.isCompleted());
        isDateSet = true;
        isTimeSet =true;

        //get the goal that the user inputs in the text box
        EditText editTextGoal = (EditText) findViewById(R.id.editText_type_new_goal);
        editTextGoal.setText(goalOld.getGoalName());

        EditText editTextEstHours = (EditText) findViewById(R.id.editText_Est_Hours);
        editTextEstHours.setText("" + goalOld.getEstHours());

        final android.support.v7.app.ActionBar ab = getSupportActionBar();

        if(ab!= null) {
            ab.setDisplayShowTitleEnabled(false);

            final LayoutInflater inflater = (LayoutInflater) ab.getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.custom_action_bar, null);
            ab.setCustomView(view, new android.support.v7.app.ActionBar.LayoutParams(
                    android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT,
                    android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT));
            ab.setDisplayShowCustomEnabled(true);

            Button cancel = (Button) view.findViewById(R.id.action_bar_button_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Goal cancelled", Toast.LENGTH_LONG).show();
                    Log.d("goal status", "cancelled");

                    ///finish current activity without any result
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                }
            });

            Button done = (Button) view.findViewById(R.id.action_bar_button_ok);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateGoalInDataBase();
                    Toast.makeText(getApplicationContext(), "Goal Saved", Toast.LENGTH_SHORT).show();

                    //setResult and finish current activity to return the result
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", newGoal);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();

                }
            });
        }
        else{
            Log.d("action bar", "is null");}

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void updateGoalInDataBase() {
        //instantiates the database
        GoalDBHelper mDbHelper = GoalDBHelper.getSingletonInstance(this);
        Log.i("start","instantiating db");
        System.out.println("instantiating db");

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //get the goal that the user inputs in the text box
        EditText editTextGoal = (EditText) findViewById(R.id.editText_type_new_goal);
        String newGoalName = editTextGoal.getText().toString();
        Log.d("new goal name", "" + newGoalName);
        newGoal = newGoalName;
        Log.d("old goal name", "" + goal.getGoalName());

        EditText editTextEstHours = (EditText) findViewById(R.id.editText_Est_Hours);
        int hours = Integer.parseInt(editTextEstHours.getText().toString());
        goal.setEstHours(hours);
        Log.d("hours:", "" + hours);

        EditText editTextNote = (EditText) findViewById(R.id.editText_Note);
        String note = editTextNote.getText().toString();
        Log.d("note: ", "" + note);



        if(isDateSet && isTimeSet){
            Log.d("status", "date and time have been successfully set");
            //insert some data into a database
           int count =  mDbHelper.updateGoal( goal.getGoalName(),newGoal, goal.getEstHours(),goal.getYear(),goal.getMonth(),goal.getDay(),goal.getHourOfDay(),goal.getMinute());

            if(note.length()!= 0) {
                mDbHelper.insertGoalNote(db, goal.getGoalId(), note);
            }
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
        goal.setYear(year); goal.setMonth(month); goal.setDay(day);
        this.year = year;
        this.month = month;
        this.day = day;
        isDateSet = true;
    }

    @Override
    public void onTimeSelected(int hourOfDay, int minute) {
        goal.setHourOfDay(hourOfDay); goal.setMinute(minute);
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        isTimeSet = true;
    }
}
