package ru.hh.headhunterclient.cache.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ru.hh.headhunterclient.cache.DatabaseWrapper;
import ru.hh.headhunterclient.model.Area;

/**
 * Created by alena on 08.05.2017.
 */

public class AreaORM {

    private static final String TAG = AreaORM.class.getSimpleName();

    private static final String COMMA_SEP = ", ";
    private static final String TYPE_INTEGER_PRIMARY_KEY = "INTEGER PRIMARY KEY";
    private static final String TYPE_TEXT = "TEXT";

    private static final String TABLE_NAME = "area";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_URL = "url";

    public static final String SQL_CREATE_AREA_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " " + TYPE_INTEGER_PRIMARY_KEY + COMMA_SEP +
                    COLUMN_NAME + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_URL + " " + TYPE_TEXT + ")";

    public static final String SQL_DROP_AREA_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static Area findAreaById(Context context, long areaId) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getReadableDatabase();

        Area area = null;
        if (database != null) {
            Log.i(TAG, String.format("Loading area [%s]", areaId));
            Cursor cursor = database.rawQuery(String.format("SELECT * FROM %s WHERE %s = %s",
                    TABLE_NAME, COLUMN_ID, areaId), null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                area = cursorToArea(cursor);
                Log.i(TAG, "Area loaded successfully!");
            }
            database.close();
        }

        return area;
    }

    public static void insertArea(Context context, Area area) {
        if (area != null) {
            if (findAreaById(context, area.getId()) != null) {
                Log.i(TAG, "Area already exists in database, not inserting!");
                return;
            }

            DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
            SQLiteDatabase database = databaseWrapper.getWritableDatabase();
            ContentValues areaValues = areaToContentValues(area);

            try {
                if (database != null) {
                    long areaId = database.insert(TABLE_NAME, "null", areaValues);
                    Log.i(TAG, String.format("Inserted new Area with ID: %s", areaId));
                }
            } catch (NullPointerException ex) {
                Log.i(TAG, String.format("Failed to insert Area with ID: %s", area.getId()));
            } finally {
                if(database != null) {
                    database.close();
                }
            }
        }
    }

    private static ContentValues areaToContentValues(Area area) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, area.getId());
        values.put(COLUMN_NAME, area.getName());
        values.put(COLUMN_URL, area.getUrl());
        return values;
    }

    private static Area cursorToArea(Cursor cursor) {
        Area area = new Area();
        area.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        area.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        area.setUrl(cursor.getString(cursor.getColumnIndex(COLUMN_URL)));
        return area;
    }
}
