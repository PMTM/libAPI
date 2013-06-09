package cz.xlinux.db;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

    private static String LOG_TAG = "MyContentProvider";

    // database
    private MySQLiteHelper database;

    // Used for the UriMacher
    private static final int ITEMS = 10;
    private static final int ITEM_ID = 20;

    private static final String AUTHORITY = "cz.xlinux.db";

    private static final String BASE_PATH = "items";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/items";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/item";

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, ITEMS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ITEM_ID);
    }

    @Override
    public boolean onCreate() {
        Log.v(LOG_TAG, "OnCreate()");
        database = new MySQLiteHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        Log.v(LOG_TAG, "query()");

        // Using SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // Check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(TableItems.TABLE_NAME);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
        case ITEMS:
            break;
        case ITEM_ID:
            // Adding the ID to the original query
            queryBuilder.appendWhere(TableItems.COLUMN_ID + "="
                    + uri.getLastPathSegment());
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG, "insert(\n * uri: " + uri + "\n * selection:"
                + selection + "\n * selectionArgs: " + selectionArgs + "\n)");

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
        case ITEMS:
            rowsDeleted = sqlDB.delete(TableItems.TABLE_NAME, selection,
                    selectionArgs);
            break;
        case ITEM_ID:
            String id = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = sqlDB.delete(TableItems.TABLE_NAME,
                        TableItems.COLUMN_ID + "=" + id, null);
            } else {
                rowsDeleted = sqlDB.delete(TableItems.TABLE_NAME,
                        TableItems.COLUMN_ID + "=" + id + " and " + selection,
                        selectionArgs);
            }
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        Log.d(LOG_TAG, "getType(\n * uri: " + uri + "\n)");
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(LOG_TAG, "insert(\n * uri: " + uri + "\n * values:" + values
                + "\n)");

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
        case ITEMS:
            id = sqlDB.insert(TableItems.TABLE_NAME, null, values);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {

        Log.d(LOG_TAG, "insert(\n * uri: " + uri + "\n * values: " + values
                + "\n * selection:" + selection + "\n * selectionArgs: "
                + selectionArgs + "\n)");

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
        case ITEMS:
            rowsUpdated = sqlDB.update(TableItems.TABLE_NAME, values,
                    selection, selectionArgs);
            break;
        case ITEM_ID:
            String id = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = sqlDB.update(TableItems.TABLE_NAME, values,
                        TableItems.COLUMN_ID + "=" + id, null);
            } else {
                rowsUpdated = sqlDB.update(TableItems.TABLE_NAME, values,
                        TableItems.COLUMN_ID + "=" + id + " and " + selection,
                        selectionArgs);
            }
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = { TableItems.COLUMN_DESC, TableItems.COLUMN_EXTID,
                TableItems.COLUMN_ID, TableItems.COLUMN_NAME,
                TableItems.COLUMN_VALUE };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(available));
            // Check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }
        }
    }
}
