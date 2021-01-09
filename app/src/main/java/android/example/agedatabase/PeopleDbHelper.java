package android.example.agedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.example.agedatabase.PeopleContract.PersonEntry;

public class PeopleDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "roster.db";
    private static final int DATABASE_VERSION = 1;

    public PeopleDbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_PEOPLE_TABLE = "CREATE TABLE " + PersonEntry.TABLE_NAME + " ("
                + PersonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PersonEntry.COLUMN_PERSON_NAME + " TEXT NOT NULL, "
                + PersonEntry.COLUMN_PERSON_AGE + " INTEGER);";

        db.execSQL(SQL_CREATE_PEOPLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
