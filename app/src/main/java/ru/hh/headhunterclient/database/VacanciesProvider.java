package ru.hh.headhunterclient.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import ru.hh.headhunterclient.database.orm.VacancyORM;

/**
 * Created by alena on 09.05.2017.
 */

public class VacanciesProvider extends ContentProvider {

    private static final String TAG = VacanciesProvider.class.getSimpleName();

    public static final String AUTHORITY = "ru.hh.headhunterclient.provider";
    public static final String SCHEME = "content://";

    public static final String VACANCIES = SCHEME + AUTHORITY;
    public static final Uri URI_VACANCIES = Uri.parse(VACANCIES);

    public VacanciesProvider() {}

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor result = null;

        if (URI_VACANCIES.equals(uri)) {
            result = DatabaseWrapper
                    .getInstance(getContext())
                    .getReadableDatabase()
                    .query(VacancyORM.TABLE_NAME, VacancyORM.FIELDS, null, null, null,
                            null, null, null);
            result.setNotificationUri(getContext().getContentResolver(), URI_VACANCIES);
        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        return result;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Log.d(TAG, String.format("insert, %s", uri.toString()));
        if (!URI_VACANCIES.equals(uri)) {
            throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        DatabaseWrapper databaseWrapper = DatabaseWrapper.getInstance(getContext());
        SQLiteDatabase database = databaseWrapper.getWritableDatabase();

        long rowID = database.insert(VacancyORM.TABLE_NAME, null, contentValues);
        Uri resultUri = ContentUris.withAppendedId(URI_VACANCIES, rowID);

        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
