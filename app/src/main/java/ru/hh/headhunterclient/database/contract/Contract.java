package ru.hh.headhunterclient.database.contract;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by alena on 11.05.2017.
 */

public interface Contract extends BaseColumns {

    String CONTENT_AUTHORITY = "com.hh.headhunterclient.VacanciesProvider";
    Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    String COMMA_SEP = ", ";
    String TYPE_INTEGER_PRIMARY_KEY = "INTEGER PRIMARY KEY";
    String TYPE_INTEGER_PRIMARY_KEY_AUTOINCREMENT = "INTEGER PRIMARY KEY AUTOINCREMENT";
    String TYPE_TEXT = "NOT NULL DEFAULT ''";
    String TYPE_INTEGER = "INTEGER";
    String TYPE_FLOAT = "FLOAT";

}
