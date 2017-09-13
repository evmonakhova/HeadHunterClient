package ru.hh.headhunterclient.database.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import ru.hh.headhunterclient.model.Employer;
import ru.hh.headhunterclient.model.LogoUrls;

/**
 * Created by alena on 11.05.2017.
 */

public class EmployerContract implements Contract {

    public static final String TABLE_EMPLOYER = "employer";

    private static final String NAME = "name";
    private static final String TRUSTED = "trusted";
    private static final String URL = "url";
    private static final String ALTERNATE_URL = "alternate_url";
    private static final String VACANCIES_URL = "vacancies_url";
    private static final String LOGO_90_URL = "logo_90";
    private static final String LOGO_240_URL = "logo_240";
    private static final String LOGO_ORIGINAL_URL = "logo_original";

    public static final Uri URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(TABLE_EMPLOYER).build();
    public static final String DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + CONTENT_AUTHORITY + "/" + TABLE_EMPLOYER;
    public static final String ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + CONTENT_AUTHORITY + "/" + TABLE_EMPLOYER;

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_EMPLOYER + " (" +
                    _ID + " " + TYPE_INTEGER_PRIMARY_KEY + COMMA_SEP +
                    NAME + " " + TYPE_TEXT + COMMA_SEP +
                    TRUSTED + " " + TYPE_INTEGER + COMMA_SEP +
                    URL + " " + TYPE_TEXT + COMMA_SEP +
                    ALTERNATE_URL + " " + TYPE_TEXT + COMMA_SEP +
                    VACANCIES_URL + " " + TYPE_TEXT + COMMA_SEP +
                    LOGO_90_URL + " " + TYPE_TEXT + COMMA_SEP +
                    LOGO_240_URL + " " + TYPE_TEXT + COMMA_SEP +
                    LOGO_ORIGINAL_URL + " " + TYPE_TEXT + ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_EMPLOYER;

    public static final String SQL_DELETE_SEQUENCE =
            "DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                    + TABLE_EMPLOYER + "'";

    public static Uri buildUri(long id){
        return ContentUris.withAppendedId(URI, id);
    }

    public static ContentValues employerToContentValues(Employer employer) {
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
        values.put(_ID, employer.getId());
        values.put(NAME, employer.getName());
        values.put(TRUSTED, employer.isTrusted() ? 1 : 0);
        values.put(URL, employer.getUrl());
        values.put(ALTERNATE_URL, employer.getAlternateUrl());
        values.put(VACANCIES_URL, employer.getVacanciesUrl());
        values.put(LOGO_90_URL, logo90);
        values.put(LOGO_240_URL, logo240);
        values.put(LOGO_ORIGINAL_URL, logoOriginal);

        return values;
    }

    public static Employer cursorToEmployer(Cursor cursor) {
        Employer employer = new Employer();
        employer.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
        employer.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        employer.setTrusted(cursor.getInt(cursor.getColumnIndex(TRUSTED)) > 0);
        employer.setUrl(cursor.getString(cursor.getColumnIndex(URL)));
        employer.setAlternateUrl(cursor.getString(cursor.getColumnIndex(ALTERNATE_URL)));
        employer.setVacanciesUrl(cursor.getString(cursor.getColumnIndex(VACANCIES_URL)));
        LogoUrls logoUrls = new LogoUrls();
        logoUrls.setLogo90(cursor.getString(cursor.getColumnIndex(LOGO_90_URL)));
        logoUrls.setLogo240(cursor.getString(cursor.getColumnIndex(LOGO_240_URL)));
        logoUrls.setOriginal(cursor.getString(cursor.getColumnIndex(LOGO_ORIGINAL_URL)));
        employer.setLogoUrls(logoUrls);
        return employer;
    }
}
