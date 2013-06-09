package cz.xlinux.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableItems {

    private static final String LOG_TAG = "TableItems";

    // Database table
    public static final String TABLE_NAME = "items";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EXTID = "extid";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_VALUE = "value";

    // Database creation SQL statement
    private static final String DATABASE_CREATE =
    // SQL statement
    "create table " + TABLE_NAME + "(" +
    // Columns
            COLUMN_ID + " integer primary key autoincrement, " +
            //
            COLUMN_EXTID + " text, " +
            //
            COLUMN_NAME + " text not null, " +
            //
            COLUMN_DESC + " text, " +
            //
            COLUMN_VALUE + " text" +
            //
            ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
            int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}