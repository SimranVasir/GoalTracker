package com.example.simran.goaltracker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewGoalActivity extends AppCompatActivity {

    ListView listView;
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.ViewGoalActivity.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goal);

        /*//get the file with the stored goals
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        //extract the goal data from the file
        String defaultString= getString(R.string.saved_goal_default);
        String goal = sharedPref.getString(getString(R.string.goal_key), defaultString);
        String goaltoset = goal;*/

        //query Database And Show Data
        String[] goaltoShow = queryAndDisplay();

       /* //Add a list as required
        String[] goaltoShow = new String[]
                { "Goal 1",
                        "Goal 2"};
*/

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, goaltoShow);

        listView = (ListView) findViewById(R.id.goals_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                int itemPosition = position;

                Intent intent = new Intent(getApplicationContext(), GoalActivity.class);

                String itemValue = (String) listView.getItemAtPosition(position);
                Log.d("item value", ":" +itemValue);
                intent.putExtra(EXTRA_MESSAGE, itemValue);
                startActivity(intent);

            }
        });


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

    private String[] queryAndDisplay() {
        //instantiates the database
        GoalDBHelper mDbHelper = GoalDBHelper.getSingletonInstance(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String[] goaltoset = mDbHelper.getAllGoals(db);
        Log.d("debugging", "end of queryAndDisplay method");
        return goaltoset;
/*
        //display the goal data in a text view
        TextView view_goal= (TextView) findViewById(R.id.view_goal);
        view_goal.setText(goaltoset);*/
    }

}
