package ru.hh.headhunterclient.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import ru.hh.headhunterclient.database.contract.AddressContract;
import ru.hh.headhunterclient.database.contract.AreaContract;
import ru.hh.headhunterclient.database.contract.Contract;
import ru.hh.headhunterclient.database.contract.EmployerContract;
import ru.hh.headhunterclient.database.contract.MetroContract;
import ru.hh.headhunterclient.database.contract.VacanciesContract;

/**
 * Created by alena on 09.05.2017.
 */

public class VacanciesProvider extends ContentProvider {

    private static final String TAG = VacanciesProvider.class.getSimpleName();

    private static final int VACANCY = 100;
    private static final int VACANCY_WITH_ID = 200;
    private static final int AREA = 300;
    private static final int AREA_WITH_ID = 400;
    private static final int ADDRESS = 500;
    private static final int ADDRESS_WITH_ID = 600;
    private static final int METRO = 700;
    private static final int METRO_WITH_ID = 800;
    private static final int EMPLOYER = 900;
    private static final int EMPLOYER_WITH_ID = 1000;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private VacanciesDBHelper mOpenHelper;

    public VacanciesProvider() {}

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.CONTENT_AUTHORITY;

        matcher.addURI(authority, VacanciesContract.TABLE_VACANCIES, VACANCY);
        matcher.addURI(authority, VacanciesContract.TABLE_VACANCIES + "/#", VACANCY_WITH_ID);

        matcher.addURI(authority, AreaContract.TABLE_AREA, AREA);
        matcher.addURI(authority, AreaContract.TABLE_AREA + "/#", AREA_WITH_ID);

        matcher.addURI(authority, AddressContract.TABLE_ADDRESS, ADDRESS);
        matcher.addURI(authority, AddressContract.TABLE_ADDRESS + "/#", ADDRESS_WITH_ID);

        matcher.addURI(authority, MetroContract.TABLE_METRO, METRO);
        matcher.addURI(authority, MetroContract.TABLE_METRO + "/#", METRO_WITH_ID);

        matcher.addURI(authority, EmployerContract.TABLE_EMPLOYER, EMPLOYER);
        matcher.addURI(authority, EmployerContract.TABLE_EMPLOYER + "/#", EMPLOYER_WITH_ID);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = VacanciesDBHelper.getInstance(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri){
        final int match = sUriMatcher.match(uri);
        switch (match){
            case VACANCY:
                return VacanciesContract.DIR_TYPE;
            case VACANCY_WITH_ID:
                return VacanciesContract.ITEM_TYPE;
            case AREA:
                return AreaContract.DIR_TYPE;
            case AREA_WITH_ID:
                return AreaContract.ITEM_TYPE;
            case ADDRESS:
                return AddressContract.DIR_TYPE;
            case ADDRESS_WITH_ID:
                return AddressContract.ITEM_TYPE;
            case METRO:
                return MetroContract.DIR_TYPE;
            case METRO_WITH_ID:
                return MetroContract.ITEM_TYPE;
            case EMPLOYER:
                return EmployerContract.DIR_TYPE;
            case EMPLOYER_WITH_ID:
                return EmployerContract.ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder){
        Cursor retCursor;
        switch(sUriMatcher.match(uri)) {
            case VACANCY: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        VacanciesContract.TABLE_VACANCIES,
                        projection, selection, selectionArgs,
                        null, null, sortOrder);
                return retCursor;
            }
            case VACANCY_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        VacanciesContract.TABLE_VACANCIES,
                        projection,
                        VacanciesContract._ID + " = ?",
                        new String[]{ String.valueOf(ContentUris.parseId(uri)) },
                        null, null, sortOrder);
                return retCursor;
            }
            case AREA: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        AreaContract.TABLE_AREA,
                        projection, selection, selectionArgs,
                        null, null, sortOrder);
                return retCursor;
            }
            case AREA_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        AreaContract.TABLE_AREA,
                        projection,
                        AreaContract._ID + " = ?",
                        new String[]{ String.valueOf(ContentUris.parseId(uri)) },
                        null, null, sortOrder);
                return retCursor;
            }
            case ADDRESS:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        AddressContract.TABLE_ADDRESS,
                        projection, selection, selectionArgs,
                        null, null, sortOrder);
                return retCursor;
            }
            case ADDRESS_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        AddressContract.TABLE_ADDRESS,
                        projection,
                        AddressContract._ID + " = ?",
                        new String[]{ String.valueOf(ContentUris.parseId(uri)) },
                        null, null, sortOrder);
                return retCursor;
            }
            case METRO:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MetroContract.TABLE_METRO,
                        projection, selection, selectionArgs,
                        null, null, sortOrder);
                return retCursor;
            }
            case METRO_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MetroContract.TABLE_METRO,
                        projection,
                        MetroContract._ID + " = ?",
                        new String[]{ String.valueOf(ContentUris.parseId(uri)) },
                        null, null, sortOrder);
                return retCursor;
            }
            case EMPLOYER:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        EmployerContract.TABLE_EMPLOYER,
                        projection, selection, selectionArgs,
                        null, null, sortOrder);
                return retCursor;
            }
            case EMPLOYER_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        EmployerContract.TABLE_EMPLOYER,
                        projection,
                        EmployerContract._ID + " = ?",
                        new String[]{ String.valueOf(ContentUris.parseId(uri)) },
                        null, null, sortOrder);
                return retCursor;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case VACANCY: {
                long _id = db.insert(VacanciesContract.TABLE_VACANCIES, null, values);
                if (_id > 0) {
                    returnUri = VacanciesContract.buildUri(_id);
                } else {
                    throw new android.database.SQLException(
                            "Failed to insert row into: " + uri);
                }
                break;
            }
            case AREA: {
                long _id = db.insert(AreaContract.TABLE_AREA, null, values);
                if (_id > 0) {
                    returnUri = AreaContract.buildUri(_id);
                } else {
                    throw new android.database.SQLException(
                            "Failed to insert row into: " + uri);
                }
                break;
            }
            case ADDRESS: {
                long _id = db.insert(AddressContract.TABLE_ADDRESS, null, values);
                if (_id > 0) {
                    returnUri = AddressContract.buildUri(_id);
                } else {
                    throw new android.database.SQLException(
                            "Failed to insert row into: " + uri);
                }
                break;
            }
            case METRO: {
                long _id = db.insert(MetroContract.TABLE_METRO, null, values);
                if (_id > 0) {
                    returnUri = MetroContract.buildUri(_id);
                } else {
                    throw new android.database.SQLException(
                            "Failed to insert row into: " + uri);
                }
                break;
            }
            case EMPLOYER: {
                long _id = db.insert(EmployerContract.TABLE_EMPLOYER, null, values);
                if (_id > 0) {
                    returnUri = EmployerContract.buildUri(_id);
                } else {
                    throw new android.database.SQLException(
                            "Failed to insert row into: " + uri);
                }
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;
        switch(match) {
            case VACANCY:
                numDeleted = db.delete(VacanciesContract.TABLE_VACANCIES, selection, selectionArgs);
                db.execSQL(VacanciesContract.SQL_DELETE_SEQUENCE);
                break;
            case VACANCY_WITH_ID:
                numDeleted = db.delete(VacanciesContract.TABLE_VACANCIES,
                        VacanciesContract._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                db.execSQL(VacanciesContract.SQL_DELETE_SEQUENCE);
                break;
            case AREA:
                numDeleted = db.delete(AreaContract.TABLE_AREA, selection, selectionArgs);
                db.execSQL(AreaContract.SQL_DELETE_SEQUENCE);
                break;
            case AREA_WITH_ID:
                numDeleted = db.delete(AreaContract.TABLE_AREA,
                        AreaContract._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                db.execSQL(AreaContract.SQL_DELETE_SEQUENCE);
                break;
            case ADDRESS:
                numDeleted = db.delete(AddressContract.TABLE_ADDRESS, selection, selectionArgs);
                db.execSQL(AddressContract.SQL_DELETE_SEQUENCE);
                break;
            case ADDRESS_WITH_ID:
                numDeleted = db.delete(AddressContract.TABLE_ADDRESS,
                        AddressContract._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                db.execSQL(AddressContract.SQL_DELETE_SEQUENCE);
                break;
            case METRO:
                numDeleted = db.delete(MetroContract.TABLE_METRO, selection, selectionArgs);
                db.execSQL(MetroContract.SQL_DELETE_SEQUENCE);
                break;
            case METRO_WITH_ID:
                numDeleted = db.delete(MetroContract.TABLE_METRO,
                        MetroContract._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                db.execSQL(MetroContract.SQL_DELETE_SEQUENCE);
                break;
            case EMPLOYER:
                numDeleted = db.delete(EmployerContract.TABLE_EMPLOYER, selection, selectionArgs);
                db.execSQL(EmployerContract.SQL_DELETE_SEQUENCE);
                break;
            case EMPLOYER_WITH_ID:
                numDeleted = db.delete(EmployerContract.TABLE_EMPLOYER,
                        EmployerContract._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                db.execSQL(EmployerContract.SQL_DELETE_SEQUENCE);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return numDeleted;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values){
        String tableName = getTableName(uri);
        if (tableName == null) {
            return super.bulkInsert(uri, values);
        }

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        int numInserted = 0;
        try {
            for(ContentValues value : values){
                if (value == null){
                    throw new IllegalArgumentException("Cannot have null content values");
                }
                long _id = -1;
                try{
                    _id = db.insertOrThrow(tableName, null, value);
                } catch (SQLiteConstraintException e) {
                    Log.w(TAG, String.format("Attempting to insert %s but value " +
                            "is already in database", value.get(VacanciesContract.NAME)));
                }
                if (_id != -1){
                    numInserted++;
                }
            }
            if(numInserted > 0){
                db.setTransactionSuccessful();
            }
        } finally {
            db.endTransaction();
        }
        if (numInserted > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numInserted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private String getTableName(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case VACANCY:
                return VacanciesContract.TABLE_VACANCIES;
            case AREA:
                return AreaContract.TABLE_AREA;
            case ADDRESS:
                return AddressContract.TABLE_ADDRESS;
            case METRO:
                return MetroContract.TABLE_METRO;
            case EMPLOYER:
                return EmployerContract.TABLE_EMPLOYER;
            default:
                return null;
        }
    }
}
