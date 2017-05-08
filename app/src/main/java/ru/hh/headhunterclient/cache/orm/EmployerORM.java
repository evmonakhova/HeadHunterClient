package ru.hh.headhunterclient.cache.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ru.hh.headhunterclient.cache.DatabaseWrapper;
import ru.hh.headhunterclient.model.Employer;
import ru.hh.headhunterclient.model.LogoUrls;

/**
 * Created by alena on 08.05.2017.
 */

public class EmployerORM {

    private static final String TAG = EmployerORM.class.getSimpleName();

    private static final String COMMA_SEP = ", ";
    private static final String TYPE_INTEGER_PRIMARY_KEY = "INTEGER PRIMARY KEY";
    private static final String TYPE_TEXT = "TEXT";
    private static final String TYPE_BOOLEAN = "BOOLEAN";

    private static final String TABLE_NAME = "employer";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TRUSTED = "trusted";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_ALTERNATE_URL = "alternate_url";
    private static final String COLUMN_VACANCIES_URL = "vacancies_url";
    private static final String COLUMN_LOGO_90_URL = "logo_90";
    private static final String COLUMN_LOGO_240_URL = "logo_240";
    private static final String COLUMN_LOGO_ORIGINAL_URL = "logo_original";

    public static final String SQL_CREATE_EMPLOYER_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " " + TYPE_INTEGER_PRIMARY_KEY + COMMA_SEP +
                    COLUMN_NAME + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_TRUSTED + " " + TYPE_BOOLEAN + COMMA_SEP +
                    COLUMN_URL + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_ALTERNATE_URL + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_VACANCIES_URL + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_LOGO_90_URL + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_LOGO_240_URL + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_LOGO_ORIGINAL_URL + " " + TYPE_TEXT + ")";

    public static final String SQL_DROP_EMPLOYER_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public static Employer findEmployerById(Context context, long employerId) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getReadableDatabase();

        Employer employer = null;
        if (database != null) {
            Log.i(TAG, String.format("Loading employer [%s]", employerId));
            Cursor cursor = database.rawQuery(String.format("SELECT * FROM %s WHERE %s = %s",
                    TABLE_NAME, COLUMN_ID, employerId), null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                employer = cursorToEmployer(cursor);
                Log.i(TAG, "Employer loaded successfully!");
            }
            database.close();
        }

        return employer;
    }


    public static void insertEmployer(Context context, Employer employer) {
        if (employer != null) {
            if (findEmployerById(context, employer.getId()) != null) {
                Log.i(TAG, "Employer already exists in database, not inserting!");
                return;
            }

            ContentValues values = employerToContentValues(employer);
            DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
            SQLiteDatabase database = databaseWrapper.getWritableDatabase();

            try {
                if (database != null) {
                    long id = database.insert(EmployerORM.TABLE_NAME, "null", values);
                    Log.i(TAG, String.format("Inserted new Employer with ID: %s", id));
                }
            } catch (NullPointerException ex) {
                Log.i(TAG, String.format("Failed to insert Employer with ID: %s", employer.getId()));
            } finally {
                if (database != null) {
                    database.close();
                }
            }
        }
    }

    private static ContentValues employerToContentValues(Employer employer) {
        String logo90 = "";
        String logo240 = "";
        String logoOriginal = "";
        LogoUrls logoUrls = employer.getLogoUrls();
        if (logoUrls != null) {
            logo90 = logoUrls.getLogo90();
            logo240 = logoUrls.getLogo240();
            logoOriginal = logoUrls.getOriginal();
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, employer.getId());
        values.put(COLUMN_NAME, employer.getName());
        values.put(COLUMN_TRUSTED, employer.isTrusted());
        values.put(COLUMN_URL, employer.getUrl());
        values.put(COLUMN_ALTERNATE_URL, employer.getAlternateUrl());
        values.put(COLUMN_VACANCIES_URL, employer.getVacanciesUrl());
        values.put(COLUMN_LOGO_90_URL, logo90);
        values.put(COLUMN_LOGO_240_URL, logo240);
        values.put(COLUMN_LOGO_ORIGINAL_URL, logoOriginal);

        return values;
    }

    private static Employer cursorToEmployer(Cursor cursor) {
        Employer employer = new Employer();
        employer.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        employer.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        employer.setTrusted(cursor.getInt(cursor.getColumnIndex(COLUMN_TRUSTED)) > 0);
        employer.setUrl(cursor.getString(cursor.getColumnIndex(COLUMN_URL)));
        employer.setAlternateUrl(cursor.getString(cursor.getColumnIndex(COLUMN_ALTERNATE_URL)));
        employer.setVacanciesUrl(cursor.getString(cursor.getColumnIndex(COLUMN_VACANCIES_URL)));
        LogoUrls logoUrls = new LogoUrls();
        logoUrls.setLogo90(cursor.getString(cursor.getColumnIndex(COLUMN_LOGO_90_URL)));
        logoUrls.setLogo240(cursor.getString(cursor.getColumnIndex(COLUMN_LOGO_240_URL)));
        logoUrls.setOriginal(cursor.getString(cursor.getColumnIndex(COLUMN_LOGO_ORIGINAL_URL)));
        employer.setLogoUrls(logoUrls);
        return employer;
    }
}
