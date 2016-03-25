package com.arbuleac.loan.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arbuleac.loan.provider.StatsContract.StatsEntry;

public class StatsDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "stats.db";

    public StatsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_STATS_TABLE = "CREATE TABLE " + StatsEntry.TABLE_NAME + " (" +
                StatsEntry._ID + " INTEGER PRIMARY KEY," +
                StatsEntry.COLUMN_VALUE + " TEXT UNIQUE NOT NULL" +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_STATS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StatsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
