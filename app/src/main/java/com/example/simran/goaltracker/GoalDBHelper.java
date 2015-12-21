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
    private static final String SQL_CREATE_GOAL_TABLE =
            "CREATE TABLE " + GoalDataSchema.GoalSchema.TABLE_NAME_GOAL + " (" +
                    GoalDataSchema.GoalSchema._ID + " INTEGER PRIMARY KEY," +
                    GoalDataSchema.GoalSchema.COLUMN_NAME_GOAL_NAME + TEXT_TYPE + COMMA_SEP +
                    GoalDataSchema.GoalSchema.COLUMN_NAME_COMPLETED + INTEGER_TYPE + COMMA_SEP +
                    GoalDataSchema.GoalSchema.COLUMN_NAME_ESTIMATED_HOURS + INTEGER_TYPE + COMMA_SEP +
                    GoalDataSchema.GoalSchema.COLUMN_NAME_DATE_YEAR + INTEGER_TYPE + COMMA_SEP +
                    GoalDataSchema.GoalSchema.COLUMN_NAME_DATE_MONTH + INTEGER_TYPE + COMMA_SEP +
                    GoalDataSchema.GoalSchema.COLUMN_NAME_DATE_DAY+ INTEGER_TYPE + COMMA_SEP +
                    GoalDataSchema.GoalSchema.COLUMN_NAME_TIME_HOUR_OF_DAY + INTEGER_TYPE + COMMA_SEP +
                    GoalDataSchema.GoalSchema.COLUMN_NAME_TIME_MINUTE + INTEGER_TYPE +
    // Any other options for the CREATE command
            " )";
    private static final String SQL_CREATE_NOTE_TABLE =
            "CREATE TABLE " + GoalDataSchema.GoalNotesSchema.TABLE_NAME_NOTE + " (" +
                    GoalDataSchema.GoalNotesSchema._ID + " INTEGER PRIMARY KEY," +
                    GoalDataSchema.GoalNotesSchema.COLUMN_NAME_GOAL_ID  + INTEGER_TYPE + COMMA_SEP +
                    GoalDataSchema.GoalNotesSchema.COLUMN_NAME_NOTE_NAME + TEXT_TYPE+
                    // Any other options for the CREATE command
                    " )";

    private static final String SQL_DELETE_GOAL_TABLE =
            "DROP TABLE IF EXISTS " + GoalDataSchema.GoalSchema.TABLE_NAME_GOAL;

    private static final String SQL_DELETE_NOTE_TABLE =
            "DROP TABLE IF EXISTS " + GoalDataSchema.GoalNotesSchema.TABLE_NAME_NOTE;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 5;
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
        db.execSQL(SQL_CREATE_GOAL_TABLE);
        db.execSQL(SQL_CREATE_NOTE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        deleteDataBase(db);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void deleteDataBase(SQLiteDatabase db){
        db.execSQL(SQL_DELETE_GOAL_TABLE);
        db.execSQL(SQL_DELETE_NOTE_TABLE);
    }

    public void deleteSomeData(SQLiteDatabase db) {
        //delete data from database
        Log.d("delete", "starting delete query");


        // Define 'where' part of query.
        String selection = GoalDataSchema.GoalSchema.COLUMN_NAME_GOAL_NAME + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf("testing") };
        // Issue SQL statement.
        db.delete(GoalDataSchema.GoalSchema.TABLE_NAME_GOAL, selection, selectionArgs);
        Log.d("delete", "ending delete query");
    }

    public String[] getAllGoals (SQLiteDatabase db) {
// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                GoalDataSchema.GoalSchema._ID,
                GoalDataSchema.GoalSchema.COLUMN_NAME_GOAL_NAME,
                GoalDataSchema.GoalSchema.COLUMN_NAME_GOAL_NAME

        };


        Cursor c = db.query(
                GoalDataSchema.GoalSchema.TABLE_NAME_GOAL,  // The table to query
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

                String goal = c.getString(c.getColumnIndex(GoalDataSchema.GoalSchema.COLUMN_NAME_GOAL_NAME));
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
    public Goal querySingleGoalData(SQLiteDatabase db, String goalName) {

        String selection = GoalDataSchema.GoalSchema.COLUMN_NAME_GOAL_NAME + " like '" + goalName + "'";


        Cursor c = db.query(
                GoalDataSchema.GoalSchema.TABLE_NAME_GOAL,  // The table to query
                null,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                               // don't filter by row groups
                null
        );

        if(c!= null && c.getCount()>0) {
            Goal goal = new Goal();

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

                String tag = "goal data results";

                long goalId = c.getLong(c.getColumnIndex(GoalDataSchema.GoalSchema._ID));
                goal.setGoalId(goalId);
                Log.i(tag, "goalId: " +goalId);
                String goalNameRedundant = c.getString(c.getColumnIndex(GoalDataSchema.GoalSchema.COLUMN_NAME_GOAL_NAME));
                Log.i(tag, "goalName: " + goalNameRedundant);
                goal.setGoalName(goalNameRedundant);
                int estHours = c.getInt(c.getColumnIndex(GoalDataSchema.GoalSchema.COLUMN_NAME_ESTIMATED_HOURS));
                goal.setEstHours(estHours);
                Log.i(tag, "estHours: " + estHours);
                goal.setIsCompleted(false);
                int year = c.getInt(c.getColumnIndex(GoalDataSchema.GoalSchema.COLUMN_NAME_DATE_YEAR));
                goal.setYear(year);
                Log.i(tag, "year: " + year);
                int month = c.getInt(c.getColumnIndex(GoalDataSchema.GoalSchema.COLUMN_NAME_DATE_MONTH));
                goal.setMonth(month);
                Log.i(tag, "month: " + month);
                goal.setDay(c.getInt(c.getColumnIndex(GoalDataSchema.GoalSchema.COLUMN_NAME_DATE_DAY)));
                //Log.i(tag, "i: " + i);
                goal.setHourOfDay(c.getInt(c.getColumnIndex(GoalDataSchema.GoalSchema.COLUMN_NAME_TIME_HOUR_OF_DAY)));
             //   Log.i(tag, "i: " + i);
                int minute = c.getInt(c.getColumnIndex(GoalDataSchema.GoalSchema.COLUMN_NAME_TIME_MINUTE));
                goal.setMinute(minute);
                Log.i(tag, "min: " + minute);

                goalArray[i] = goalName;
                i++;
                c.moveToNext();


                Log.i("incrementer", "i: " +i);
                Log.i(tag, goalName);
            }
            Log.i("debugging" , "before returning from queryDatabase method");
            return goal;
        }
        else{
            Log.i("empty result", "nothing to show");
            Log.i("empty result", "nothing to show");
            return null;
        }
    }


    public String[] getNotesForAGoal(SQLiteDatabase db, long goalId) {
        Log.i("goalId:", " "+goalId);

        String selection = GoalDataSchema.GoalNotesSchema.COLUMN_NAME_GOAL_ID + " = ?";


        Cursor c = db.query(
                GoalDataSchema.GoalNotesSchema.TABLE_NAME_NOTE,  // The table to query
                null,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                new String[] {String.valueOf(goalId)},   // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                               // don't filter by row groups
                null                                //don't order the rows
        );

        if(c!= null && c.getCount()>0) {


            int count = c.getCount();
            Log.i("row count:", " "+count);
            Log.i("row count:", " " + count);
            Log.i("row count:", " " + count);

            String[] notesArray = new String[count];

            c.moveToFirst();
            int i = 0;
            while(!c.isAfterLast()) {

                if(c.isFirst())Log.i("debugging", "isFirst");
                if(c.isLast())Log.i("debugging", "isLast");

                String tag = "goal data results";

                String  noteName = c.getString(c.getColumnIndex(GoalDataSchema.GoalNotesSchema.COLUMN_NAME_NOTE_NAME));
                Log.i(tag, "noteName: " + noteName);

                notesArray[i] = noteName;
                i++;
                c.moveToNext();


                Log.i("incrementer", "i: " +i);
                Log.i(tag, noteName);
            }
            Log.i("debugging" , "before returning from queryDatabase method");
            return notesArray;
        }
        else{
            Log.i("empty result", "nothing to show");
            Log.i("empty result", "nothing to show");
            return null;
        }
    }

    public long insertGoal(SQLiteDatabase db, String goal, int estHours, int year, int month, int day, int hour, int minute) {

        Log.i("insert", "insert started");

        // Create a new map of values, where column names are the keys
        ContentValues contentValues = new ContentValues();
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_GOAL_NAME, goal);
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_COMPLETED, 0);
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_ESTIMATED_HOURS, estHours);
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_DATE_YEAR, year);
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_DATE_MONTH, month);
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_DATE_DAY, day);
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_TIME_HOUR_OF_DAY, hour);
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_TIME_MINUTE, minute);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                GoalDataSchema.GoalSchema.TABLE_NAME_GOAL,
                GoalDataSchema.GoalSchema.COLUMN_NAME_GOAL_NAME,
                contentValues);
        Log.i("insert", "insert ended");
        return newRowId;
    }

    /*
 * Updates Goal with new data
 * returns 1 if goal with goalName was found and updated
 * returns 0 if no update occured.
 */
    public int updateGoal(String oldGoalName,String newGoalName, int estHours, int year, int month, int day, int hour, int minute) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues contentValues = new ContentValues();
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_GOAL_NAME, newGoalName);
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_COMPLETED, 0);
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_ESTIMATED_HOURS, estHours);
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_DATE_YEAR, year);
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_DATE_MONTH, month);
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_DATE_DAY, day);
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_TIME_HOUR_OF_DAY, hour);
        contentValues.put(GoalDataSchema.GoalSchema.COLUMN_NAME_TIME_MINUTE, minute);

        // Which row to update, based on the ID
        String selection = GoalDataSchema.GoalSchema.COLUMN_NAME_GOAL_NAME + " like '" + oldGoalName + "'";

        //updates rows with data in the contentValues returns the number of Goal rows updated.
        int count = db.update(
                GoalDataSchema.GoalSchema.TABLE_NAME_GOAL,
               contentValues,
                selection,
                null);

        return count;
    }

    public long insertGoalNote(SQLiteDatabase db, long goalId, String noteName) {

        Log.i("insert", "goalNote insert started");

        // Create a new map of values, where column names are the keys
        ContentValues contentValues = new ContentValues();
        contentValues.put(GoalDataSchema.GoalNotesSchema.COLUMN_NAME_GOAL_ID, goalId);
        contentValues.put(GoalDataSchema.GoalNotesSchema.COLUMN_NAME_NOTE_NAME, noteName);
        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                GoalDataSchema.GoalNotesSchema.TABLE_NAME_NOTE,
                null,
                contentValues);


        Log.i("insert", "goalNote"+ noteName +" inserted with newRodId: " + newRowId+ "and goalId: "+goalId );

        return newRowId;
    }
}
