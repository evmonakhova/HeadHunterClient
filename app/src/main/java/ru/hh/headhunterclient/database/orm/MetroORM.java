package ru.hh.headhunterclient.database.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ru.hh.headhunterclient.database.DatabaseWrapper;
import ru.hh.headhunterclient.model.Metro;

/**
 * Created by alena on 08.05.2017.
 */

public class MetroORM {

    private static final String TAG = MetroORM.class.getSimpleName();

    private static final String COMMA_SEP = ", ";
    private static final String TYPE_TEXT = "TEXT";
    private static final String TYPE_INTEGER = "INTEGER";
    private static final String TYPE_FLOAT = "FLOAT";

    private static final String TABLE_NAME = "metro";
    private static final String COLUMN_STATION_ID = "station_id";
    private static final String COLUMN_STATION_NAME = "station_name";
    private static final String COLUMN_LINE_ID = "line_id";
    private static final String COLUMN_LINE_NAME = "line_name";
    private static final String COLUMN_LAT = "lat";
    private static final String COLUMN_LNG = "lng";

    public static final String SQL_CREATE_METRO_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_STATION_ID + " " + TYPE_FLOAT + COMMA_SEP +
                    COLUMN_STATION_NAME + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_LINE_ID + " " + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_LINE_NAME + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_LAT + " " + TYPE_FLOAT + COMMA_SEP +
                    COLUMN_LNG + " " + TYPE_FLOAT + ")";

    public static final String SQL_DROP_METRO_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static Metro findMetroById(Context context, Double metroId) {
        DatabaseWrapper databaseWrapper = DatabaseWrapper.getInstance(context);
        SQLiteDatabase database = databaseWrapper.getReadableDatabase();

        Metro metro = null;
        if (database != null) {
//            Log.i(TAG, String.format("Loading metro [%s]", metroId));
            Cursor cursor = database.rawQuery(String.format("SELECT * FROM %s WHERE %s = %s",
                    TABLE_NAME, COLUMN_STATION_ID, metroId), null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                metro = cursorToMetro(cursor);
//                Log.i(TAG, "Metro loaded successfully!");
            }
            database.close();
        }

        return metro;
    }

    public static void insertMetro(Context context, Metro metro) {
        if (metro != null) {
            if (findMetroById(context, metro.getStationId()) != null) {
//                Log.i(TAG, "Metro already exists in database, not inserting!");
                return;
            }

            ContentValues metroValues = metroToContentValues(metro);
            DatabaseWrapper databaseWrapper = DatabaseWrapper.getInstance(context);
            SQLiteDatabase database = databaseWrapper.getWritableDatabase();

            try {
                if (database != null) {
                    long metroId = database.insert(MetroORM.TABLE_NAME, "null", metroValues);
//                    Log.i(TAG, String.format("Inserted new Metro with ID: %s", metroId));
                }
            } catch (NullPointerException ex) {
                Log.i(TAG, String.format("Failed to insert Metro with ID: %s", metro.getStationId()));
            } finally {
                if (database != null) {
                    database.close();
                }
            }
        }
    }

    private static ContentValues metroToContentValues(Metro metro) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATION_ID, metro.getStationId());
        values.put(COLUMN_STATION_NAME, metro.getStationName());
        values.put(COLUMN_LINE_ID, metro.getLineId());
        values.put(COLUMN_LINE_NAME, metro.getLineName());
        values.put(COLUMN_LAT, metro.getLat());
        values.put(COLUMN_LNG, metro.getLng());
        return values;
    }

    private static Metro cursorToMetro(Cursor cursor) {
        Metro metro = new Metro();
        metro.setStationId(cursor.getDouble(cursor.getColumnIndex(COLUMN_STATION_ID)));
        metro.setStationName(cursor.getString(cursor.getColumnIndex(COLUMN_STATION_NAME)));
        metro.setLineId(cursor.getInt(cursor.getColumnIndex(COLUMN_LINE_ID)));
        metro.setLineName(cursor.getString(cursor.getColumnIndex(COLUMN_LINE_NAME)));
        metro.setLat(cursor.getDouble(cursor.getColumnIndex(COLUMN_LAT)));
        metro.setLng(cursor.getDouble(cursor.getColumnIndex(COLUMN_LNG)));
        return metro;
    }
}
