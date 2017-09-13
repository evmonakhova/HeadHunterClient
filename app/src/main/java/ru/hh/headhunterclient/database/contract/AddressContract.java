package ru.hh.headhunterclient.database.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import ru.hh.headhunterclient.model.Address;
import ru.hh.headhunterclient.model.Metro;

/**
 * Created by alena on 11.05.2017.
 */

public class AddressContract implements Contract {

    public static final String TABLE_ADDRESS = "address";

    private static final String CITY = "city";
    private static final String STREET = "street";
    private static final String BUILDING = "building";
    private static final String DESCRIPTION = "description";
    private static final String LAT = "lat";
    private static final String LNG = "lng";
    private static final String METRO_ID = "metro_id";

    public static final Uri URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(TABLE_ADDRESS).build();
    public static final String DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + CONTENT_AUTHORITY + "/" + TABLE_ADDRESS;
    public static final String ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + CONTENT_AUTHORITY + "/" + TABLE_ADDRESS;

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_ADDRESS + " (" +
                    _ID + " " + TYPE_INTEGER_PRIMARY_KEY + COMMA_SEP +
                    CITY + " " + TYPE_TEXT + COMMA_SEP +
                    STREET + " " + TYPE_TEXT + COMMA_SEP +
                    BUILDING + " " + TYPE_TEXT + COMMA_SEP +
                    DESCRIPTION + " " + TYPE_TEXT + COMMA_SEP +
                    LAT + " " + TYPE_FLOAT + COMMA_SEP +
                    LNG + " " + TYPE_FLOAT + COMMA_SEP +
                    METRO_ID + " " + TYPE_INTEGER + ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_ADDRESS;

    public static final String SQL_DELETE_SEQUENCE =
            "DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                    + TABLE_ADDRESS + "'";


    public static Uri buildUri(long id){
        return ContentUris.withAppendedId(URI, id);
    }

    public static ContentValues addressToContentValues(Address address) {
        Metro metro = address.getMetro();
        Double stationId = null;
        if (metro != null) {
            stationId = metro.getStationId();
        }

        ContentValues values = new ContentValues();
        values.put(_ID, address.getId());
        values.put(CITY, address.getCity());
        values.put(STREET, address.getStreet());
        values.put(BUILDING, address.getBuilding());
        values.put(DESCRIPTION, address.getDescription());
        values.put(LAT, address.getLat());
        values.put(LNG, address.getLng());
        values.put(METRO_ID, stationId);

        return values;
    }

    public static Address cursorToAddress(Cursor cursor) {
        Address address = new Address();
        address.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
        address.setCity(cursor.getString(cursor.getColumnIndex(CITY)));
        int streetColumnId = cursor.getColumnIndex(STREET);
        if (streetColumnId > 0) {
            address.setStreet(cursor.getString(streetColumnId));
        }
        int buildingColumnId = cursor.getColumnIndex(BUILDING);
        if (buildingColumnId > 0) {
            address.setBuilding(cursor.getString(buildingColumnId));
        }
        int descriptionColumnId = cursor.getColumnIndex(DESCRIPTION);
        if (descriptionColumnId > 0) {
            address.setDescription(cursor.getString(descriptionColumnId));
        }
        address.setLat(cursor.getDouble(cursor.getColumnIndex(LAT)));
        address.setLng(cursor.getDouble(cursor.getColumnIndex(LNG)));

        Metro metro = new Metro();
        int metroColumnId = cursor.getColumnIndex(METRO_ID);
        if (metroColumnId > 0) {
            metro.setStationId(cursor.getDouble(metroColumnId));
        }
        address.setMetro(metro);
        return address;
    }
}
