package ru.hh.headhunterclient.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ru.hh.headhunterclient.database.orm.AddressORM;
import ru.hh.headhunterclient.database.orm.AreaORM;
import ru.hh.headhunterclient.database.orm.EmployerORM;
import ru.hh.headhunterclient.database.orm.MetroORM;
import ru.hh.headhunterclient.database.orm.VacancyORM;

/**
 * Created by alena on 08.05.2017.
 */

public class DatabaseWrapper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseWrapper";

    private static final String DATABASE_NAME = "hhClient.db";
    private static final int DATABASE_VERSION = 1;

    public static DatabaseWrapper getInstance(Context context) {
        return new DatabaseWrapper(context);
    }

    private DatabaseWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, String.format("Creating database [%s v.%s]", DATABASE_NAME, DATABASE_VERSION));
        sqLiteDatabase.execSQL(VacancyORM.SQL_CREATE_VACANCY_TABLE);
        sqLiteDatabase.execSQL(AreaORM.SQL_CREATE_AREA_TABLE);
        sqLiteDatabase.execSQL(AddressORM.SQL_CREATE_ADDRESS_TABLE);
        sqLiteDatabase.execSQL(MetroORM.SQL_CREATE_METRO_TABLE);
        sqLiteDatabase.execSQL(EmployerORM.SQL_CREATE_EMPLOYER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.i(TAG, String.format("Upgrading database [%s v.%s] to [%s v.%s]",
                DATABASE_NAME, oldVersion, DATABASE_NAME, newVersion));
        sqLiteDatabase.execSQL(VacancyORM.SQL_DROP_VACANCY_TABLE);
        sqLiteDatabase.execSQL(AreaORM.SQL_DROP_AREA_TABLE);
        sqLiteDatabase.execSQL(AddressORM.SQL_DROP_ADDRESS_TABLE);
        sqLiteDatabase.execSQL(MetroORM.SQL_DROP_METRO_TABLE);
        sqLiteDatabase.execSQL(EmployerORM.SQL_DROP_EMPLOYER_TABLE);
        onCreate(sqLiteDatabase);
    }

    public Cursor getAllData() {
        return getWritableDatabase()
                .query(VacancyORM.TABLE_NAME, null, null, null, null, null, null);
    }
}
