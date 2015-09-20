package net.laggedhero.pomodoroapp.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by laggedhero on 9/15/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pomodoroapp-wear.db";

    private static final int DATABASE_VERSION = 1;

    protected final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createFilesSync = "CREATE TABLE " + PomodoroAppContract.Tasks.TABLE_NAME + " ("
                + PomodoroAppContract.Tasks.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PomodoroAppContract.Tasks.COLUMN_TITLE + " TEXT"
                + ");";
        db.execSQL(createFilesSync);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: upgrade logic (when needed)
    }
}
