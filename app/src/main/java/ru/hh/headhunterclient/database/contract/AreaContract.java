package ru.hh.headhunterclient.database.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import ru.hh.headhunterclient.model.Area;

/**
 * Created by alena on 11.05.2017.
 */

public class AreaContract implements Contract {

    private static final String TAG = AreaContract.class.getSimpleName();

    public static final String TABLE_AREA = "area";

    private static final String NAME = "name";
    private static final String URL = "url";

    public static final Uri URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(TABLE_AREA).build();
    public static final String DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + CONTENT_AUTHORITY + "/" + TABLE_AREA;
    public static final String ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + CONTENT_AUTHORITY + "/" + TABLE_AREA;

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_AREA + " (" +
                    _ID + " " + TYPE_INTEGER_PRIMARY_KEY + COMMA_SEP +
                    NAME + " " + TYPE_TEXT + COMMA_SEP +
                    URL + " " + TYPE_TEXT + ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_AREA;

    public static final String SQL_DELETE_SEQUENCE =
            "DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                    + TABLE_AREA + "'";

    public static Uri buildUri(long id){
        return ContentUris.withAppendedId(URI, id);
    }

    public static ContentValues areaToContentValues(Area area) {
        ContentValues values = new ContentValues();
        values.put(_ID, area.getId());
        values.put(NAME, area.getName());
        values.put(URL, area.getUrl());
        return values;
    }

    public static Area cursorToArea(Cursor cursor) {
        Area area = new Area();
        area.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
        area.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        area.setUrl(cursor.getString(cursor.getColumnIndex(URL)));
        return area;
    }
}
