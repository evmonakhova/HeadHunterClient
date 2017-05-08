package ru.hh.headhunterclient.cache.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.LinkAddress;
import android.util.Log;

import ru.hh.headhunterclient.cache.DatabaseWrapper;
import ru.hh.headhunterclient.model.Address;
import ru.hh.headhunterclient.model.Area;
import ru.hh.headhunterclient.model.Metro;

/**
 * Created by alena on 08.05.2017.
 */

public class AddressORM {

    private static final String TAG = AddressORM.class.getSimpleName();

    private static final String COMMA_SEP = ", ";
    private static final String TYPE_INTEGER_PRIMARY_KEY = "INTEGER PRIMARY KEY";
    private static final String TYPE_TEXT = "TEXT";
    private static final String TYPE_INTEGER = "INTEGER";
    private static final String TYPE_FLOAT = "FLOAT";

    private static final String TABLE_NAME = "address";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_STREET = "street";
    private static final String COLUMN_BUILDING = "building";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_LAT = "lat";
    private static final String COLUMN_LNG = "lng";
    private static final String COLUMN_METRO_ID = "metro_id";

    public static final String SQL_CREATE_ADDRESS_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " " + TYPE_INTEGER_PRIMARY_KEY + COMMA_SEP +
                    COLUMN_CITY + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_STREET + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_BUILDING + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_DESCRIPTION + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_LAT + " " + TYPE_FLOAT + COMMA_SEP +
                    COLUMN_LNG + " " + TYPE_FLOAT + COMMA_SEP +
                    COLUMN_METRO_ID + " " + TYPE_INTEGER + ")";

    public static final String SQL_DROP_ADDRESS_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static Address findAddressById(Context context, Integer addressId) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getReadableDatabase();

        Address address = null;
        if (database != null) {
            Log.i(TAG, String.format("Loading address [%s]", addressId));
            Cursor cursor = database.rawQuery(String.format("SELECT * FROM %s WHERE %s = %s",
                    TABLE_NAME, COLUMN_ID, addressId), null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                address = cursorToAddress(cursor);
                Log.i(TAG, "Address loaded successfully!");
            }
            database.close();
        }

        return address;
    }

    public static void insertAddress(Context context, Address address) {
        if (address != null) {
            if (findAddressById(context, address.getId()) != null) {
                Log.i(TAG, "Address already exists in database, not inserting!");
                return;
            }

            ContentValues addressValues = addressToContentValues(address);
            DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
            SQLiteDatabase database = databaseWrapper.getWritableDatabase();

            try {
                if (database != null) {
                    long addressId = database.insert(TABLE_NAME, "null", addressValues);
                    Log.i(TAG, String.format("Inserted new Address with ID: %s", addressId));
                }
            } catch (NullPointerException ex) {
                Log.i(TAG, String.format("Failed to insert Address with ID: %s", address.getId()));
            } finally {
                if(database != null) {
                    database.close();
                }
            }
        }
    }

    private static ContentValues addressToContentValues(Address address) {
        Metro metro = address.getMetro();
        Double stationId = null;
        if (metro != null) {
            stationId = metro.getStationId();
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, address.getId());
        values.put(COLUMN_CITY, address.getCity());
        values.put(COLUMN_STREET, address.getStreet());
        values.put(COLUMN_BUILDING, address.getBuilding());
        values.put(COLUMN_DESCRIPTION, address.getDescription());
        values.put(COLUMN_LAT, address.getLat());
        values.put(COLUMN_LNG, address.getLng());
        values.put(COLUMN_METRO_ID, stationId);

        return values;
    }

    private static Address cursorToAddress(Cursor cursor) {
        Address address = new Address();
        address.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        address.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
        int streetColumnId = cursor.getColumnIndex(COLUMN_STREET);
        if (streetColumnId > 0) {
            address.setStreet(cursor.getString(streetColumnId));
        }
        int buildingColumnId = cursor.getColumnIndex(COLUMN_BUILDING);
        if (buildingColumnId > 0) {
            address.setBuilding(cursor.getString(buildingColumnId));
        }
        int descriptionColumnId = cursor.getColumnIndex(COLUMN_DESCRIPTION);
        if (descriptionColumnId > 0) {
            address.setDescription(cursor.getString(descriptionColumnId));
        }
        address.setLat(cursor.getDouble(cursor.getColumnIndex(COLUMN_LAT)));
        address.setLng(cursor.getDouble(cursor.getColumnIndex(COLUMN_LNG)));
        Metro metro = new Metro();
        int metroColumnId = cursor.getColumnIndex(COLUMN_METRO_ID);
        if (metroColumnId > 0) {
            metro.setStationId(cursor.getDouble(metroColumnId));
        }
        address.setMetro(metro);
        return address;
    }

}
