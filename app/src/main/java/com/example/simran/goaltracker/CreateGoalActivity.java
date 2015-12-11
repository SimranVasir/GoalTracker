package com.example.simran.goaltracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class CreateGoalActivity extends AppCompatActivity {

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
     * called when user clicks the Submit button
     * @param view
     */
    public void saveGoal(View view){
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

    }

}
