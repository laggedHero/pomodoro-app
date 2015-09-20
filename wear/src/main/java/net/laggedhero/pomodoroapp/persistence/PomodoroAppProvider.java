package net.laggedhero.pomodoroapp.persistence;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by laggedhero on 9/15/15.
 */
public class PomodoroAppProvider extends ContentProvider {
    private static final int URI_MATCHES_TASKS = 1;
    private static final int URI_MATCHES_ID_TASKS = 2;

    private static final UriMatcher uriMatcher;
    private static final HashMap<String, String> projectionMapTasks;

    private DatabaseHelper databaseHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(PomodoroAppContract.AUTHORITY, PomodoroAppContract.Tasks.TABLE_NAME, URI_MATCHES_TASKS);
        uriMatcher.addURI(PomodoroAppContract.AUTHORITY, PomodoroAppContract.Tasks.TABLE_NAME + "/#", URI_MATCHES_ID_TASKS);

        projectionMapTasks = new HashMap<>();
        projectionMapTasks.put(
                PomodoroAppContract.Tasks.COLUMN_ID,
                PomodoroAppContract.Tasks.COLUMN_ID
        );
        projectionMapTasks.put(
                PomodoroAppContract.Tasks.COLUMN_TITLE,
                PomodoroAppContract.Tasks.COLUMN_TITLE
        );
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        String tableName;
        HashMap<String, String> projectionMap;
        String orderBy;

        switch (uriMatcher.match(uri)) {
            case URI_MATCHES_TASKS:
                tableName = PomodoroAppContract.Tasks.TABLE_NAME;
                projectionMap = projectionMapTasks;
                if (TextUtils.isEmpty(sortOrder)) {
                    orderBy = PomodoroAppContract.Tasks.DEFAULT_SORT_ORDER;
                } else {
                    orderBy = sortOrder;
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(tableName);
        qb.setProjectionMap(projectionMap);

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        cursor = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_MATCHES_TASKS:
                return PomodoroAppContract.Tasks.CONTENT_TYPE;
            case URI_MATCHES_ID_TASKS:
                return PomodoroAppContract.Tasks.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        Uri itemUri = null;
        String tableName;
        Uri contentURI;

        switch (uriMatcher.match(uri)) {
            case URI_MATCHES_TASKS:
                tableName = PomodoroAppContract.Tasks.TABLE_NAME;
                contentURI = PomodoroAppContract.Tasks.CONTENT_ID_URI_BASE;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values;

        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        long rowId = db.insert(tableName, null, values);

        if (rowId > 0) {
            itemUri = ContentUris.withAppendedId(contentURI, rowId);
            getContext().getContentResolver().notifyChange(itemUri, null);
        }

        return itemUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int count;

        switch (uriMatcher.match(uri)) {
            case URI_MATCHES_TASKS:
                count = db.delete(PomodoroAppContract.Tasks.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int count;

        switch (uriMatcher.match(uri)) {
            case URI_MATCHES_TASKS:
                count = db.update(PomodoroAppContract.Tasks.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }
}
