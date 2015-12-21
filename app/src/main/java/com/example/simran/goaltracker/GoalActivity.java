package com.example.simran.goaltracker;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GoalActivity extends AppCompatActivity {
    ListView listview;
    String goalName;
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.GoalActivity.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        Intent intent = getIntent();

        goalName= intent.getStringExtra(ViewGoalActivity.EXTRA_MESSAGE);
        Log.d("Goal acivity", "goalName: " + goalName);
        //get GoalDBHelper instance
        displayGoalData();


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

    private void displayGoalData() {
        GoalDBHelper mDbHelper = GoalDBHelper.getSingletonInstance(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Goal goal = mDbHelper.querySingleGoalData(db, goalName);
        long goalId = goal.getGoalId();
        String [] notesArray = mDbHelper.getNotesForAGoal(db, goalId);

        // set text for textview in the xml file
        TextView textViewGoal = (TextView) findViewById(R.id.new_goal);
        textViewGoal.setText(goalName);

        // Create the text view
        TextView textViewDeadline = (TextView) findViewById(R.id.goal_deadline);

        textViewDeadline.setText("deadline: "+ goal.getDay() + "." + goal.getMonth() + "." + goal.getYear() +":" + goal.getHourOfDay() + "h" +goal.getMinute() + "m");

        TextView textViewHoursToComplete = (TextView) findViewById(R.id.textView_hours_to_complete);
        textViewHoursToComplete.setText("estHours: " + goal.getEstHours());

        /*//Add a list as required
        String[] values = new String[]
                { "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                        "Ut volutpat interdum interdum. Nulla laoreet lacus diam, vitae " +
                        "sodales sapien commodo faucibus. Vestibulum et feugiat enim. Donec " +
                        "semper mi et euismod tempor. Sed sodales eleifend mi id varius. Nam " +
                        "et ornare enim, sit amet gravida sapien. Quisque gravida et enim vel " +
                        "volutpat. Vivamus egestas ut felis a blandit. Vivamus fringilla " +
                        "dignissim mollis. Maecenas imperdiet interdum hendrerit. Aliquam" +
                        " dictum hendrerit ultrices. Ut vitae vestibulum dolor. Donec auctor ante create a goal,",

                        "sodales sapien commodo faucibus. Vestibulum et feugiat enim. Donec " +
                                "semper mi et euismod tempor. Sed sodales eleifend mi id varius. Nam " +
                                "et ornare enim, sit amet gravida sapien. Quisque gravida et enim vel " +
                                "volutpat. Vivamus egestas ut felis a blandit. Vivamus fringilla " +
                                "dignissim mollis. Maecenas imperdiet interdum hendrerit. Aliquam" +
                                " dictum hendrerit ultrices. Ut vitae vestibulum dolor. Donec auctor ante hello world" +
                                " eget libero molestie porta. Nam tempor fringilla ultricies. Nam sem " +
                                "lectus, feugiat eget ullamcorper vitae, ornare et sem. Fusce dapibus ipsum" +
                                " sed laoreet suscipit. View a goal ",
                        "sodales sapien commodo faucibus. Vestibulum et feugiat enim. Donec " +
                                "semper mi et euismod tempor. Sed sodales eleifend mi id varius. Nam " +
                                "et ornare enim, sit amet gravida sapien. Quisque gravida et enim vel " +
                                "volutpat. Vivamus egestas ut felis a blandit. Vivamus fringilla " +
                                "dignissim mollis. Maecenas imperdiet interdum hendrerit. Aliquam" +
                                " dictum hendrerit ultrices. Ut vitae vestibulum dolor. Donec auctor ante hello world" +
                                " eget libero molestie porta. Nam tempor fringilla ultricies. Nam sem " +
                                "lectus, feugiat eget ullamcorper vitae, ornare et sem. Fusce dapibus ipsum" +
                                " sed laoreet suscipit. new goal "};*/

        //Define a new adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.expandable_note_text_view_layout,
                R.id.note_expandable_text_view,
                notesArray);

        // Get Listview object from xml
        listview = (ListView) findViewById(R.id.notes_list);
        listview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_goal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit_goal:/*
                Intent intent = new Intent(getApplicationContext(), UpdateGoal.class);
                intent.putExtra(EXTRA_MESSAGE, goalName);
                startActivity(intent);*/
                Intent i = new Intent(this, UpdateGoal.class);
                i.putExtra(EXTRA_MESSAGE, goalName);
                startActivityForResult(i, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                goalName =data.getStringExtra("result");
                displayGoalData();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
