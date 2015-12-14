package com.example.simran.goaltracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by simra on 12/12/2015.
 */
public class GoalDBHelper extends SQLiteOpenHelper {
    private static GoalDBHelper instance = null;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GoalDataSchema.FeedEntry.TABLE_NAME + " (" +
                    GoalDataSchema.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    GoalDataSchema.FeedEntry.COLUMN_NAME_GOAL_NAME + TEXT_TYPE + COMMA_SEP +
                    GoalDataSchema.FeedEntry.COLUMN_NAME_COMPLETED + INTEGER_TYPE + COMMA_SEP +
                    GoalDataSchema.FeedEntry.COLUMN_NAME_DATE_YEAR + INTEGER_TYPE + COMMA_SEP +
                    GoalDataSchema.FeedEntry.COLUMN_NAME_DATE_MONTH + INTEGER_TYPE + COMMA_SEP +
                    GoalDataSchema.FeedEntry.COLUMN_NAME_DATE_DAY+ INTEGER_TYPE + COMMA_SEP +
                    GoalDataSchema.FeedEntry.COLUMN_NAME_TIME_HOUR_OF_DAY + INTEGER_TYPE + COMMA_SEP +
                    GoalDataSchema.FeedEntry.COLUMN_NAME_TIME_MINUTE + INTEGER_TYPE +
    // Any other options for the CREATE command
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GoalDataSchema.FeedEntry.TABLE_NAME;


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "FeedReader.db";

    private GoalDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static GoalDBHelper getSingletonInstance(Context context){
        if(instance == null) {
            instance = new GoalDBHelper(context);
        }
        return instance;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void deleteDataBase(SQLiteDatabase db){

    }

    public void deleteSomeData(SQLiteDatabase db) {
        //delete data from database
        Log.d("delete", "starting delete query");


        // Define 'where' part of query.
        String selection = GoalDataSchema.FeedEntry.COLUMN_NAME_GOAL_NAME + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf("testing") };
        // Issue SQL statement.
        db.delete(GoalDataSchema.FeedEntry.TABLE_NAME, selection, selectionArgs);
        Log.d("delete", "ending delete query");
    }

    public String[] queryDatabase(SQLiteDatabase db) {


// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                GoalDataSchema.FeedEntry._ID,
                GoalDataSchema.FeedEntry.COLUMN_NAME_GOAL_NAME,
                GoalDataSchema.FeedEntry.COLUMN_NAME_GOAL_NAME

        };


        Cursor c = db.query(
                GoalDataSchema.FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                               // don't filter by row groups
                null
        );

        if(c!= null && c.getCount()>0) {
            int count = c.getCount();
            Log.i("row count:", " "+count);
            Log.i("row count:", " " + count);
            Log.i("row count:", " " + count);

            String[] goalArray = new String[count];

            c.moveToFirst();
            int i = 0;
            while(!c.isAfterLast()) {

                if(c.isFirst())Log.i("debugging", "isFirst");
                if(c.isLast())Log.i("debugging", "isLast");

                String goal = c.getString(c.getColumnIndex(GoalDataSchema.FeedEntry.COLUMN_NAME_GOAL_NAME));
                goalArray[i] = goal;
                i++;
                c.moveToNext();

                String tag = "i message";
                Log.i(tag, "i: " +i);
                Log.i(tag, goal);
            }
            Log.i("debugging" , "before returning from queryDatabase method");
            return goalArray;
        }
        else{
            Log.i("empty result", "nothing to show");
            Log.i("empty result", "nothing to show");
            return null;
        }


    }

    public void insertGoal(SQLiteDatabase db, String goal, int year, int month, int day, int hour, int minute) {

        Log.i("insert", "insert started");

        // Create a new map of values, where column names are the keys
        ContentValues contentValues = new ContentValues();
        contentValues.put(GoalDataSchema.FeedEntry.COLUMN_NAME_GOAL_NAME, goal);
        contentValues.put(GoalDataSchema.FeedEntry.COLUMN_NAME_COMPLETED, 0);
        contentValues.put(GoalDataSchema.FeedEntry.COLUMN_NAME_DATE_YEAR, year);
        contentValues.put(GoalDataSchema.FeedEntry.COLUMN_NAME_DATE_MONTH, month);
        contentValues.put(GoalDataSchema.FeedEntry.COLUMN_NAME_DATE_DAY, day);
        contentValues.put(GoalDataSchema.FeedEntry.COLUMN_NAME_TIME_HOUR_OF_DAY, hour);
        contentValues.put(GoalDataSchema.FeedEntry.COLUMN_NAME_TIME_MINUTE, minute);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                GoalDataSchema.FeedEntry.TABLE_NAME,
                GoalDataSchema.FeedEntry.COLUMN_NAME_GOAL_NAME,
                contentValues);
        Log.i("insert", "insert ended");
    }
}
