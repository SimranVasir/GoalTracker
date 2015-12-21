package com.example.simran.goaltracker;

import android.app.Activity;
import android.app.DialogFragment;
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

public class CreateGoalActivity extends AppCompatActivity implements DatePickerFragment.OnDateSelectedListener, TimePickerFragment.OnTimeSelectedListener{

    DialogFragment newDateFragment;
    DialogFragment newTimeFragment;
    Goal goal;
    int year, month, day, hourOfDay, minute;
    boolean isDateSet = false;
    boolean isTimeSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);

        goal = new Goal();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

                    //finish current activity and go to parent activity
                    NavUtils.navigateUpFromSameTask(CreateGoalActivity.this);
                }
            });

            Button done = (Button) view.findViewById(R.id.action_bar_button_ok);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveGoalInDataBase();
                    Toast.makeText(getApplicationContext(), "Goal Saved", Toast.LENGTH_SHORT).show();

                    //finish current activity and go to parent activity
                    NavUtils.navigateUpFromSameTask(CreateGoalActivity.this);
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

    public void saveGoalInDataBase(){
        //instantiates the database
        GoalDBHelper mDbHelper = GoalDBHelper.getSingletonInstance(this);
        Log.i("start","instantiating db");
        System.out.println("instantiating db");

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //get the goal that the user inputs in the text box
        EditText editTextGoal = (EditText) findViewById(R.id.editText_type_new_goal);
        String goal = editTextGoal.getText().toString();

        EditText editTextEstHours = (EditText) findViewById(R.id.editText_Est_Hours);
        int hours = Integer.parseInt(editTextEstHours.getText().toString());
        Log.d("hours:", "" + hours);

        EditText editTextNote = (EditText) findViewById(R.id.editText_Note);
        String note = editTextNote.getText().toString();
        Log.d("note: ", "" + note);


        if(isDateSet && isTimeSet){
            Log.d("status", "date and time have been successfully set");
            //insert some data into a database
            long goalId = mDbHelper.insertGoal(db, goal,hours,year,month,day,hourOfDay,minute);
            mDbHelper.insertGoalNote(db, goalId, note );
        }
        else
        {
            Log.d("insert error", "date and time are not set");
        }

    }


    /**
     * listner for the Submit button
     * Called when user clicks the Submit button.
     * Saves goal in a database.
     *
     */
    /*public void saveGoalInDataBase(View view){
        //instantiates the database
        GoalDBHelper mDbHelper = GoalDBHelper.getSingletonInstance(this);
        Log.i("start","instantiating db");
        System.out.println("instantiating db");

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //get the goal that the user inputs in the text box
        EditText editTextGoal = (EditText) findViewById(R.id.editText_type_new_goal);
        String goal = editTextGoal.getText().toString();

        EditText editTextEstHours = (EditText) findViewById(R.id.editText_Est_Hours);
        int hours = Integer.parseInt(editTextEstHours.getText().toString());
        Log.d("hours:", ""+hours);

        EditText editTextNote = (EditText) findViewById(R.id.editText_Note);
        String note = editTextNote.getText().toString();
        Log.d("note: ", ""+note);


        if(isDateSet && isTimeSet){
            Log.d("status", "date and time have been successfully set");
        //insert some data into a database
        long goalId = mDbHelper.insertGoal(db, goal,hours,year,month,day,hourOfDay,minute);
        mDbHelper.insertGoalNote(db, goalId, note );
        }
        else
        {
            Log.d("insert error", "date and time are not set");
        }

    }*/

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
