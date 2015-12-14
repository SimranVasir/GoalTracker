package com.example.simran.goaltracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by simra on 12/12/2015.
 */
public class GoalDataSchema {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public GoalDataSchema() {}
    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "goal";
        public static final String COLUMN_NAME_GOAL_NAME = "goalname";
        public static final String COLUMN_NAME_COMPLETED = "completed";
        public static final String COLUMN_NAME_DATE_YEAR = "year";
        public static final String COLUMN_NAME_DATE_MONTH= "month";
        public static final String COLUMN_NAME_DATE_DAY = "day";
        public static final String COLUMN_NAME_TIME_HOUR_OF_DAY = "hour";
        public static final String COLUMN_NAME_TIME_MINUTE = "minute";
    }
}
