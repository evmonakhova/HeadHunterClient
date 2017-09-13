package ru.hh.headhunterclient.database.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import ru.hh.headhunterclient.model.Metro;

/**
 * Created by alena on 11.05.2017.
 */

public class MetroContract implements Contract {

    public static final String TABLE_METRO = "metro";

    public static final String STATION_ID = "station_id";
    private static final String STATION_NAME = "station_name";
    private static final String LINE_ID = "line_id";
    private static final String LINE_NAME = "line_name";
    private static final String LAT = "lat";
    private static final String LNG = "lng";

    public static final Uri URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(TABLE_METRO).build();
    public static final String DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + CONTENT_AUTHORITY + "/" + TABLE_METRO;
    public static final String ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + CONTENT_AUTHORITY + "/" + TABLE_METRO;

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_METRO + " (" +
                    _ID + " " + TYPE_INTEGER_PRIMARY_KEY_AUTOINCREMENT + COMMA_SEP +
                    STATION_ID + " " + TYPE_FLOAT + COMMA_SEP +
                    STATION_NAME + " " + TYPE_TEXT + COMMA_SEP +
                    LINE_ID + " " + TYPE_INTEGER + COMMA_SEP +
                    LINE_NAME + " " + TYPE_TEXT + COMMA_SEP +
                    LAT + " " + TYPE_FLOAT + COMMA_SEP +
                    LNG + " " + TYPE_FLOAT + ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_METRO;

    public static final String SQL_DELETE_SEQUENCE =
            "DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                    + TABLE_METRO + "'";

    public static Uri buildUri(long id){
        return ContentUris.withAppendedId(URI, id);
    }

    public static ContentValues metroToContentValues(Metro metro) {
        ContentValues values = new ContentValues();
        values.put(STATION_ID, metro.getStationId());
        values.put(STATION_NAME, metro.getStationName());
        values.put(LINE_ID, metro.getLineId());
        values.put(LINE_NAME, metro.getLineName());
        values.put(LAT, metro.getLat());
        values.put(LNG, metro.getLng());
        return values;
    }

    public static Metro cursorToMetro(Cursor cursor) {
        Metro metro = new Metro();
        metro.setStationId(cursor.getDouble(cursor.getColumnIndex(STATION_ID)));
        metro.setStationName(cursor.getString(cursor.getColumnIndex(STATION_NAME)));
        metro.setLineId(cursor.getInt(cursor.getColumnIndex(LINE_ID)));
        metro.setLineName(cursor.getString(cursor.getColumnIndex(LINE_NAME)));
        metro.setLat(cursor.getDouble(cursor.getColumnIndex(LAT)));
        metro.setLng(cursor.getDouble(cursor.getColumnIndex(LNG)));
        return metro;
    }
}
