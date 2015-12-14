package com.example.simran.goaltracker;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RelativeLayout rLayout;
        Resources res = getResources();

        //rLayout = (RelativeLayout) findViewById(R.id.rLayout);
        //rLayout.setBackgroundColor(Color.BLACK);

        // Get Listview object from xml
        listview = (ListView) findViewById(R.id.options_list);

        //Add a list as required
        String[] values = new String[]
                { "Create a goal",
                        "View a goal"};

        //Define a new adapter

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                values);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Listview Clicked item index
                int itemPosition = position;

                String itemValue = (String) listview.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(), "Position:" + itemPosition + " ListItem:" +
                        itemValue, Toast.LENGTH_LONG).show();

                if(itemPosition == 0) {
                    Intent intent = new Intent(getApplicationContext(), CreateGoalActivity.class);
                    startActivity(intent);
                }
                if(itemPosition == 1){
                    Intent intent = new Intent(getApplicationContext(), ViewGoalActivity.class);
                    startActivity(intent);
                }


            }
        });


        //auto generated code for the toolbar and action item
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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


}
