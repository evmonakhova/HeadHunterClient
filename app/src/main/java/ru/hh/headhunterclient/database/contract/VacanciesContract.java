package ru.hh.headhunterclient.database.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import ru.hh.headhunterclient.model.Address;
import ru.hh.headhunterclient.model.Area;
import ru.hh.headhunterclient.model.Department;
import ru.hh.headhunterclient.model.Employer;
import ru.hh.headhunterclient.model.Salary;
import ru.hh.headhunterclient.model.Snippet;
import ru.hh.headhunterclient.model.Type;
import ru.hh.headhunterclient.model.Vacancy;

/**
 * Created by alena on 11.05.2017.
 */

public class VacanciesContract implements Contract {

    private static final String TAG = VacanciesContract.class.getSimpleName();

    public static final String TABLE_VACANCIES = "vacancies";

    public static final String NAME = "name";
    private static final String SALARY_FROM = "salary_from";
    private static final String SALARY_TO = "salary_to";
    private static final String CURRENCY = "currency";
    private static final String AREA_ID = "area_id";
    private static final String ADDRESS_ID = "address_id";
    private static final String EMPLOYER_ID = "employer_id";
    private static final String DEPARTMENT_ID = "department_id";
    private static final String DEPARTMENT_NAME = "department_name";
    private static final String TYPE_ID = "type_id";
    private static final String TYPE_NAME = "type_name";
    private static final String URL = "url";
    private static final String ALTERNATE_URL = "alternate_url";
    private static final String APPLY_ALTERNATE_URL = "apply_alternate_url";
    private static final String PUBLISHED_AT = "published_at";
    private static final String RESPONSE_LETTER_REQUIRED = "response_letter_required";
    private static final String ARCHIVED = "archived";
    private static final String SNIPPET_REQUIREMENT = "snippet_requirement";
    private static final String SNIPPET_RESPONSIBILITY = "snippet_responsibility";

    public static final Uri URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(TABLE_VACANCIES).build();
    public static final String DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + CONTENT_AUTHORITY + "/" + TABLE_VACANCIES;
    public static final String ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + CONTENT_AUTHORITY + "/" + TABLE_VACANCIES;

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_VACANCIES + " (" +
                    _ID + " " + TYPE_INTEGER_PRIMARY_KEY + COMMA_SEP +
                    NAME + " " + TYPE_TEXT + COMMA_SEP +
                    SALARY_FROM + " " + TYPE_INTEGER + COMMA_SEP +
                    SALARY_TO + " " + TYPE_INTEGER + COMMA_SEP +
                    CURRENCY + " " + TYPE_TEXT + COMMA_SEP +
                    AREA_ID + " " + TYPE_INTEGER + COMMA_SEP +
                    ADDRESS_ID + " " + TYPE_INTEGER+ COMMA_SEP +
                    EMPLOYER_ID + " " + TYPE_INTEGER + COMMA_SEP +
                    DEPARTMENT_ID + " " + TYPE_TEXT + COMMA_SEP +
                    DEPARTMENT_NAME + " " + TYPE_TEXT + COMMA_SEP +
                    TYPE_ID + " " + TYPE_TEXT + COMMA_SEP +
                    TYPE_NAME + " " + TYPE_TEXT + COMMA_SEP +
                    URL + " " + TYPE_TEXT + COMMA_SEP +
                    ALTERNATE_URL + " " + TYPE_TEXT + COMMA_SEP +
                    APPLY_ALTERNATE_URL + " " + TYPE_TEXT + COMMA_SEP +
                    PUBLISHED_AT + " " + TYPE_TEXT + COMMA_SEP +
                    RESPONSE_LETTER_REQUIRED + " " + TYPE_INTEGER + COMMA_SEP +
                    ARCHIVED + " " + TYPE_INTEGER + COMMA_SEP +
                    SNIPPET_REQUIREMENT + " " + TYPE_TEXT + COMMA_SEP +
                    SNIPPET_RESPONSIBILITY + " " + TYPE_TEXT +
                    ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_VACANCIES;

    public static final String SQL_DELETE_SEQUENCE =
            "DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
            + TABLE_VACANCIES + "'";


    public static Uri buildUri(long id){
        return ContentUris.withAppendedId(URI, id);
    }

    public static ContentValues vacancyToContentValues(Vacancy vacancy) {
        ContentValues values = new ContentValues();
        values.put(_ID, vacancy.getId());
        values.put(NAME, vacancy.getName());

        Salary salary = vacancy.getSalary();
        if (salary != null) {
            if (salary.getFrom() != null) {
                values.put(SALARY_FROM, salary.getFrom());
            }
            if (salary.getTo() != null) {
                values.put(SALARY_TO, salary.getTo());
            }
            values.put(CURRENCY, salary.getCurrency());
        }
        Area area = vacancy.getArea();
        if (area != null) {
            values.put(AREA_ID, vacancy.getArea().getId());
        }
        Address address = vacancy.getAddress();
        if (address != null) {
            values.put(ADDRESS_ID, address.getId());
        }
        Employer employer = vacancy.getEmployer();
        if (employer != null) {
            values.put(EMPLOYER_ID, employer.getId());
        }
        Department department = vacancy.getDepartment();
        if (department != null) {
            values.put(DEPARTMENT_ID, department.getId());
            values.put(DEPARTMENT_NAME, department.getName());
        }
        Type type = vacancy.getType();
        if (type != null) {
            values.put(TYPE_ID, type.getId());
            values.put(TYPE_NAME, type.getName());
        }

        if (vacancy.getUrl() != null) {
            values.put(URL, vacancy.getUrl());
        }
        if (vacancy.getAlternateUrl() != null) {
            values.put(ALTERNATE_URL, vacancy.getAlternateUrl());
        }
        if (vacancy.getApplyAlternateUrl() != null) {
            values.put(APPLY_ALTERNATE_URL, vacancy.getApplyAlternateUrl());
        }
        if (vacancy.getPublishedAt() != null) {
            values.put(PUBLISHED_AT, vacancy.getPublishedAt());
        }
        values.put(RESPONSE_LETTER_REQUIRED, vacancy.isResponseLetterRequired() ? 1 : 0);
        values.put(ARCHIVED, vacancy.isArchived() ? 1 : 0);

        Snippet snippet = vacancy.getSnippet();
        if (snippet != null) {
            if (snippet.getRequirement() != null) {
                values.put(SNIPPET_REQUIREMENT, snippet.getRequirement());
            }
            if (snippet.getResponsibility() != null) {
                values.put(SNIPPET_RESPONSIBILITY, snippet.getResponsibility());
            }
        }

        return values;
    }

    public static Vacancy cursorToVacancy(Cursor cursor) {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
        vacancy.setName(cursor.getString(cursor.getColumnIndex(NAME)));

        Salary salary = new Salary();
        int columnSalaryFrom = cursor.getColumnIndex(SALARY_FROM);
        if (columnSalaryFrom > 0) {
            salary.setFrom(cursor.getInt(columnSalaryFrom));
        }
        int columnSalaryTo = cursor.getColumnIndex(SALARY_TO);
        if (columnSalaryTo > 0) {
            salary.setTo(cursor.getInt(columnSalaryTo));
        }
        int columnCurrency = cursor.getColumnIndex(CURRENCY);
        if (columnCurrency > 0) {
            salary.setCurrency(cursor.getString(columnCurrency));
        }
        vacancy.setSalary(salary);

        Area area = new Area();
        int columnAreaId = cursor.getColumnIndex(AREA_ID);
        if (columnAreaId > 0) {
            area.setId(cursor.getInt(columnAreaId));
        }
        vacancy.setArea(area);

        Address address = new Address();
        int addressColumnId = cursor.getColumnIndex(ADDRESS_ID);
        if (addressColumnId > 0){
            address.setId(cursor.getInt(addressColumnId));
        }
        vacancy.setAddress(address);

        Employer employer = new Employer();
        int employerColumnId = cursor.getColumnIndex(EMPLOYER_ID);
        if (employerColumnId > 0) {
            employer.setId(cursor.getInt(employerColumnId));
        }
        vacancy.setEmployer(employer);

        Department department = new Department();
        int departmentColumnId = cursor.getColumnIndex(DEPARTMENT_ID);
        if (departmentColumnId > 0) {
            department.setId(cursor.getString(departmentColumnId));
        }
        int departmentNameColumnId = cursor.getColumnIndex(DEPARTMENT_NAME);
        if (departmentNameColumnId > 0) {
            department.setName(cursor.getString(departmentNameColumnId));
        }
        vacancy.setDepartment(department);

        Type type = new Type();
        int typeColumnId = cursor.getColumnIndex(TYPE_ID);
        if (typeColumnId > 0) {
            type.setId(cursor.getString(typeColumnId));
        }
        int typeNameColumnId = cursor.getColumnIndex(TYPE_NAME);
        if (typeNameColumnId > 0) {
            type.setName(cursor.getString(typeNameColumnId));
        }
        vacancy.setType(type);

        vacancy.setUrl(cursor.getString(cursor.getColumnIndex(URL)));
        vacancy.setAlternateUrl(cursor.getString(cursor.getColumnIndex(ALTERNATE_URL)));
        vacancy.setApplyAlternateUrl(cursor.getString(cursor.getColumnIndex(APPLY_ALTERNATE_URL)));
        vacancy.setPublishedAt(cursor.getString(cursor.getColumnIndex(PUBLISHED_AT)));

        int responseLetterColumnId = cursor.getColumnIndex(RESPONSE_LETTER_REQUIRED);
        if (responseLetterColumnId > 0) {
            vacancy.setResponseLetterRequired(cursor.getInt(responseLetterColumnId) > 0);
        }
        int archivedColumnId = cursor.getColumnIndex(ARCHIVED);
        if (archivedColumnId > 0) {
            vacancy.setArchived(cursor.getInt(cursor.getInt(archivedColumnId)) > 0);
        }

        Snippet snippet = new Snippet();
        int snippetRequirementColumnId = cursor.getColumnIndex(SNIPPET_REQUIREMENT);
        if (snippetRequirementColumnId > 0) {
            snippet.setRequirement(cursor.getString(snippetRequirementColumnId));
        }
        int snippetResponsibilityColumnId = cursor.getColumnIndex(SNIPPET_RESPONSIBILITY);
        if (snippetResponsibilityColumnId > 0) {
            snippet.setResponsibility(cursor.getString(snippetResponsibilityColumnId));
        }
        vacancy.setSnippet(snippet);
        return vacancy;
    }
}
