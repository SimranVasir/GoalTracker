package com.example.simran.goaltracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ViewGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goal);

        //get the file with the stored goals
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        //extract the goal data from the file
        String defaultString= getString(R.string.saved_goal_default);
        String goal = sharedPref.getString(getString(R.string.goal_key), defaultString);
        String goaltoset = goal;

        //display the goal data in a text view
        TextView view_goal= (TextView) findViewById(R.id.view_goal);
        view_goal.setText(goaltoset);


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

}
