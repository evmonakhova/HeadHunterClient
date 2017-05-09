package ru.hh.headhunterclient.database.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.hh.headhunterclient.database.DatabaseWrapper;
import ru.hh.headhunterclient.model.Address;
import ru.hh.headhunterclient.model.Area;
import ru.hh.headhunterclient.model.Department;
import ru.hh.headhunterclient.model.Employer;
import ru.hh.headhunterclient.model.Salary;
import ru.hh.headhunterclient.model.Snippet;
import ru.hh.headhunterclient.model.Type;
import ru.hh.headhunterclient.model.Vacancy;

/**
 * Created by alena on 08.05.2017.
 */

public class VacancyORM {

    private static final String TAG = VacancyORM.class.getSimpleName();

    private static final String COMMA_SEP = ", ";
    private static final String TYPE_INTEGER_PRIMARY_KEY = "INTEGER PRIMARY KEY";
    private static final String TYPE_TEXT = "NOT NULL DEFAULT ''";
    private static final String TYPE_INTEGER = "INTEGER";

    public static final String TABLE_NAME = "vacancy";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SALARY_FROM = "salary_from";
    private static final String COLUMN_SALARY_TO = "salary_to";
    private static final String COLUMN_CURRENCY = "currency";
    private static final String COLUMN_AREA_ID = "area_id";
    private static final String COLUMN_ADDRESS_ID = "address_id";
    private static final String COLUMN_EMPLOYER_ID = "employer_id";
    private static final String COLUMN_DEPARTMENT_ID = "department_id";
    private static final String COLUMN_DEPARTMENT_NAME = "department_name";
    private static final String COLUMN_TYPE_ID = "type_id";
    private static final String COLUMN_TYPE_NAME = "type_name";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_ALTERNATE_URL = "alternate_url";
    private static final String COLUMN_APPLY_ALTERNATE_URL = "apply_alternate_url";
    private static final String COLUMN_PUBLISHED_AT = "published_at";
    private static final String COLUMN_RESPONSE_LETTER_REQUIRED = "response_letter_required";
    private static final String COLUMN_ARCHIVED = "archived";
    private static final String COLUMN_SNIPPET_REQUIREMENT = "snippet_requirement";
    private static final String COLUMN_SNIPPET_RESPONSIBILITY = "snippet_responsibility";

    public static final String[] FIELDS = { COLUMN_ID, COLUMN_NAME, COLUMN_SALARY_FROM,
            COLUMN_SALARY_TO, COLUMN_CURRENCY, COLUMN_AREA_ID, COLUMN_ADDRESS_ID,
            COLUMN_EMPLOYER_ID, COLUMN_DEPARTMENT_ID, COLUMN_DEPARTMENT_NAME,
            COLUMN_TYPE_ID, COLUMN_TYPE_NAME, COLUMN_URL, COLUMN_ALTERNATE_URL,
            COLUMN_APPLY_ALTERNATE_URL, COLUMN_PUBLISHED_AT, COLUMN_RESPONSE_LETTER_REQUIRED,
            COLUMN_ARCHIVED, COLUMN_SNIPPET_REQUIREMENT, COLUMN_SNIPPET_RESPONSIBILITY};

    public static final String SQL_CREATE_VACANCY_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " " + TYPE_INTEGER_PRIMARY_KEY + COMMA_SEP +
                    COLUMN_NAME + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_SALARY_FROM + " " + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_SALARY_TO + " " + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_CURRENCY + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_AREA_ID + " " + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_ADDRESS_ID + " " + TYPE_INTEGER+ COMMA_SEP +
                    COLUMN_EMPLOYER_ID + " " + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_DEPARTMENT_ID + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_DEPARTMENT_NAME + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_TYPE_ID + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_TYPE_NAME + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_URL + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_ALTERNATE_URL + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_APPLY_ALTERNATE_URL + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_PUBLISHED_AT + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_RESPONSE_LETTER_REQUIRED + " " + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_ARCHIVED + " " + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_SNIPPET_REQUIREMENT + " " + TYPE_TEXT + COMMA_SEP +
                    COLUMN_SNIPPET_RESPONSIBILITY + " " + TYPE_TEXT +
                    ")";

    public static final String SQL_DROP_VACANCY_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static Vacancy findVacancyById(Context context, Integer vacancyId) {
        DatabaseWrapper databaseWrapper = DatabaseWrapper.getInstance(context);
        SQLiteDatabase database = databaseWrapper.getReadableDatabase();

        Vacancy vacancy = null;
        if (database != null) {
//            Log.i(TAG, String.format("Loading vacancy [%s]", vacancyId));
            Cursor cursor = database.rawQuery(String.format("SELECT * FROM %s WHERE %s = %s",
                    TABLE_NAME, COLUMN_ID, vacancyId), null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                vacancy = cursorToVacancy(cursor);
//                Log.i(TAG, "Vacancy loaded successfully!");
            }
            database.close();
        }

        return vacancy;
    }

    public static void insertVacancy(Context context, Vacancy vacancy) {
        if (findVacancyById(context, vacancy.getId()) != null) {
//            Log.i(TAG, "Vacancy already exists in database, not inserting!");
            return;
        }

        ContentValues vacancyValues = vacancyToContentValues(vacancy);
        DatabaseWrapper databaseWrapper = DatabaseWrapper.getInstance(context);
        SQLiteDatabase database = databaseWrapper.getWritableDatabase();

        try {
            if (database != null) {
                long vacancyId = database.insert(TABLE_NAME, "null", vacancyValues);
//                Log.i(TAG, "Inserted new Vacancy with ID: " + vacancyId);
            }
        } catch (NullPointerException ex) {
            Log.i(TAG, String.format("Failed to insert Vacancy with ID: %s", vacancy.getId()));
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    public static List<Vacancy> getVacancies(Context context) {
        DatabaseWrapper databaseWrapper = DatabaseWrapper.getInstance(context);
        SQLiteDatabase database = databaseWrapper.getReadableDatabase();

        List<Vacancy> postList = null;

        if(database != null) {
            Cursor cursor = database.rawQuery(String.format("SELECT * FROM %s", TABLE_NAME), null);
//            Log.i(TAG, String.format("Loaded %s Vacancies...", cursor.getCount()));
            int count = 20;
            if (cursor.getCount() > 0) {
                postList = new ArrayList<>();
                cursor.moveToFirst();
                while (!cursor.isAfterLast() && count > 0) {
                    Vacancy vacancy = cursorToVacancy(cursor);
                    postList.add(vacancy);
                    cursor.moveToNext();
                    count--;
                }
//                Log.i(TAG, "Vacancies loaded successfully.");
            }

            database.close();
        }

        return postList;
    }


    private static ContentValues vacancyToContentValues(Vacancy vacancy) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, vacancy.getId());
        values.put(COLUMN_NAME, vacancy.getName());

        Salary salary = vacancy.getSalary();
        if (salary != null) {
            if (salary.getFrom() != null) {
                values.put(COLUMN_SALARY_FROM, salary.getFrom());
            }
            if (salary.getTo() != null) {
                values.put(COLUMN_SALARY_TO, salary.getTo());
            }
            values.put(COLUMN_CURRENCY, salary.getCurrency());
        }
        Area area = vacancy.getArea();
        if (area != null) {
            values.put(COLUMN_AREA_ID, vacancy.getArea().getId());
        }
        Address address = vacancy.getAddress();
        if (address != null) {
            values.put(COLUMN_ADDRESS_ID, address.getId());
        }
        Employer employer = vacancy.getEmployer();
        if (employer != null) {
            values.put(COLUMN_EMPLOYER_ID, employer.getId());
        }
        Department department = vacancy.getDepartment();
        if (department != null) {
            values.put(COLUMN_DEPARTMENT_ID, department.getId());
            values.put(COLUMN_DEPARTMENT_NAME, department.getName());
        }
        Type type = vacancy.getType();
        if (type != null) {
            values.put(COLUMN_TYPE_ID, type.getId());
            values.put(COLUMN_TYPE_NAME, type.getName());
        }

        if (vacancy.getUrl() != null) {
            values.put(COLUMN_URL, vacancy.getUrl());
        }
        if (vacancy.getAlternateUrl() != null) {
            values.put(COLUMN_ALTERNATE_URL, vacancy.getAlternateUrl());
        }
        if (vacancy.getApplyAlternateUrl() != null) {
            values.put(COLUMN_APPLY_ALTERNATE_URL, vacancy.getApplyAlternateUrl());
        }
        if (vacancy.getPublishedAt() != null) {
            values.put(COLUMN_PUBLISHED_AT, vacancy.getPublishedAt());
        }
        values.put(COLUMN_RESPONSE_LETTER_REQUIRED, vacancy.isResponseLetterRequired() ? 1 : 0);
        values.put(COLUMN_ARCHIVED, vacancy.isArchived() ? 1 : 0);

        Snippet snippet = vacancy.getSnippet();
        if (snippet != null) {
            if (snippet.getRequirement() != null) {
                values.put(COLUMN_SNIPPET_REQUIREMENT, snippet.getRequirement());
            }
            if (snippet.getResponsibility() != null) {
                values.put(COLUMN_SNIPPET_RESPONSIBILITY, snippet.getResponsibility());
            }
        }

        return values;
    }

    private static Vacancy cursorToVacancy(Cursor cursor) {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        vacancy.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));

        Salary salary = new Salary();
        int columnSalaryFrom = cursor.getColumnIndex(COLUMN_SALARY_FROM);
        if (columnSalaryFrom > 0) {
            salary.setFrom(cursor.getInt(columnSalaryFrom));
        }
        int columnSalaryTo = cursor.getColumnIndex(COLUMN_SALARY_TO);
        if (columnSalaryTo > 0) {
            salary.setTo(cursor.getInt(columnSalaryTo));
        }
        int columnCurrency = cursor.getColumnIndex(COLUMN_CURRENCY);
        if (columnCurrency > 0) {
            salary.setCurrency(cursor.getString(columnCurrency));
        }
        vacancy.setSalary(salary);

        Area area = new Area();
        int columnAreaId = cursor.getColumnIndex(COLUMN_AREA_ID);
        if (columnAreaId > 0) {
            area.setId(cursor.getInt(columnAreaId));
        }
        vacancy.setArea(area);

        Address address = new Address();
        int addressColumnId = cursor.getColumnIndex(COLUMN_ADDRESS_ID);
        if (addressColumnId > 0){
            address.setId(cursor.getInt(addressColumnId));
        }
        vacancy.setAddress(address);

        Employer employer = new Employer();
        int employerColumnId = cursor.getColumnIndex(COLUMN_EMPLOYER_ID);
        if (employerColumnId > 0) {
            employer.setId(cursor.getInt(employerColumnId));
        }
        vacancy.setEmployer(employer);

        Department department = new Department();
        int departmentColumnId = cursor.getColumnIndex(COLUMN_DEPARTMENT_ID);
        if (departmentColumnId > 0) {
            department.setId(cursor.getString(departmentColumnId));
        }
        int departmentNameColumnId = cursor.getColumnIndex(COLUMN_DEPARTMENT_NAME);
        if (departmentNameColumnId > 0) {
            department.setName(cursor.getString(departmentNameColumnId));
        }
        vacancy.setDepartment(department);

        Type type = new Type();
        int typeColumnId = cursor.getColumnIndex(COLUMN_TYPE_ID);
        if (typeColumnId > 0) {
            type.setId(cursor.getString(typeColumnId));
        }
        int typeNameColumnId = cursor.getColumnIndex(COLUMN_TYPE_NAME);
        if (typeNameColumnId > 0) {
            type.setName(cursor.getString(typeNameColumnId));
        }
        vacancy.setType(type);

        vacancy.setUrl(cursor.getString(cursor.getColumnIndex(COLUMN_URL)));
        vacancy.setAlternateUrl(cursor.getString(cursor.getColumnIndex(COLUMN_ALTERNATE_URL)));
        vacancy.setApplyAlternateUrl(cursor.getString(cursor.getColumnIndex(COLUMN_APPLY_ALTERNATE_URL)));
        vacancy.setPublishedAt(cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHED_AT)));

        int responseLetterColumnId = cursor.getColumnIndex(COLUMN_RESPONSE_LETTER_REQUIRED);
        if (responseLetterColumnId > 0) {
            vacancy.setResponseLetterRequired(cursor.getInt(responseLetterColumnId) > 0);
        }
        int archivedColumnId = cursor.getColumnIndex(COLUMN_ARCHIVED);
        if (archivedColumnId > 0) {
            vacancy.setArchived(cursor.getInt(cursor.getInt(archivedColumnId)) > 0);
        }

        Snippet snippet = new Snippet();
        int snippetRequirementColumnId = cursor.getColumnIndex(COLUMN_SNIPPET_REQUIREMENT);
        if (snippetRequirementColumnId > 0) {
            snippet.setRequirement(cursor.getString(snippetRequirementColumnId));
        }
        int snippetResponsibilityColumnId = cursor.getColumnIndex(COLUMN_SNIPPET_RESPONSIBILITY);
        if (snippetResponsibilityColumnId > 0) {
            snippet.setResponsibility(cursor.getString(snippetResponsibilityColumnId));
        }
        vacancy.setSnippet(snippet);

        return vacancy;
    }
}
