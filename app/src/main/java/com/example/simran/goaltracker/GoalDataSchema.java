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
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        //public static final String COLUMN_NAME_SUBTITLE = "subtitle";

    }
}
