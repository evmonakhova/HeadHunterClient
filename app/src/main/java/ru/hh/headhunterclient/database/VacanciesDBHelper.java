package ru.hh.headhunterclient.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ru.hh.headhunterclient.database.contract.AddressContract;
import ru.hh.headhunterclient.database.contract.AreaContract;
import ru.hh.headhunterclient.database.contract.EmployerContract;
import ru.hh.headhunterclient.database.contract.MetroContract;
import ru.hh.headhunterclient.database.contract.VacanciesContract;

/**
 * Created by alena on 11.05.2017.
 */

public class VacanciesDBHelper extends SQLiteOpenHelper {

    private static final String TAG = VacanciesDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "hhClient.db";
    private static final int DATABASE_VERSION = 1;

    public static VacanciesDBHelper getInstance(Context context) {
        return new VacanciesDBHelper(context);
    }

    private VacanciesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, String.format("Creating database [%s v.%s]", DATABASE_NAME, DATABASE_VERSION));

        sqLiteDatabase.execSQL(VacanciesContract.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(AreaContract.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(AddressContract.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(MetroContract.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(EmployerContract.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.i(TAG, String.format("Upgrading database [%s v.%s] to [%s v.%s]",
                DATABASE_NAME, oldVersion, DATABASE_NAME, newVersion));

        sqLiteDatabase.execSQL(VacanciesContract.SQL_DROP_TABLE);
        sqLiteDatabase.execSQL(AreaContract.SQL_DROP_TABLE);
        sqLiteDatabase.execSQL(AddressContract.SQL_DROP_TABLE);
        sqLiteDatabase.execSQL(MetroContract.SQL_DROP_TABLE);
        sqLiteDatabase.execSQL(EmployerContract.SQL_DROP_TABLE);

        onCreate(sqLiteDatabase);
    }

}
