package ru.hh.headhunterclient.loaders;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.hh.headhunterclient.database.contract.AddressContract;
import ru.hh.headhunterclient.database.contract.AreaContract;
import ru.hh.headhunterclient.database.contract.EmployerContract;
import ru.hh.headhunterclient.database.contract.MetroContract;
import ru.hh.headhunterclient.database.contract.VacanciesContract;
import ru.hh.headhunterclient.model.Address;
import ru.hh.headhunterclient.model.Area;
import ru.hh.headhunterclient.model.Employer;
import ru.hh.headhunterclient.model.Metro;
import ru.hh.headhunterclient.model.Vacancy;

/**
 * Created by alena on 09.05.2017.
 */

public class CacheDataAsyncTask extends AbstrAsyncTask<List<Vacancy>, Void, Void> {

    private Context mContext;

    public CacheDataAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(List<Vacancy>... lists) {
        ContentResolver resolver = mContext.getContentResolver();

        for (List<Vacancy> vacancies: lists) {
            ContentValues[] vacancyCV = new ContentValues[vacancies.size()];
            List<ContentValues> areaCV = new ArrayList<>();
            List<ContentValues> addressCV = new ArrayList<>();
            List<ContentValues> metroCV = new ArrayList<>();
            List<ContentValues> employerCV = new ArrayList<>();

            for (int i = 0; i < vacancies.size(); i++) {
                Vacancy vacancy = vacancies.get(i);
                vacancyCV[i] = VacanciesContract.vacancyToContentValues(vacancy);

                addAreaToContentValues(areaCV, vacancy.getArea());
                addAddressToContentValues(addressCV, metroCV, vacancy.getAddress());
                addEmployerToContentValues(employerCV, vacancy.getEmployer());
            }

            resolver.bulkInsert(VacanciesContract.URI, vacancyCV);
            resolver.bulkInsert(AreaContract.URI,
                    areaCV.toArray(new ContentValues[areaCV.size()]));
            resolver.bulkInsert(AddressContract.URI,
                    addressCV.toArray(new ContentValues[addressCV.size()]));
            resolver.bulkInsert(MetroContract.URI,
                    metroCV.toArray(new ContentValues[metroCV.size()]));
            resolver.bulkInsert(EmployerContract.URI,
                    employerCV.toArray(new ContentValues[employerCV.size()]));
        }
        return null;
    }

    private void addAreaToContentValues(List<ContentValues> cvList, Area area) {
        if (area != null) {
            ContentValues cv = AreaContract.areaToContentValues(area);
            if (!cvList.contains(cv)) {
                cvList.add(cv);
            }
        }
    }

    private void addAddressToContentValues(List<ContentValues> cvList,
                               List<ContentValues> metroCvList, Address address) {
        if (address != null) {
            ContentValues cv = AddressContract.addressToContentValues(address);
            if (!cvList.contains(cv)) {
                cvList.add(cv);
            }
            addMetroToContentValues(metroCvList, address.getMetro());
        }
    }

    private void addMetroToContentValues(List<ContentValues> cvList, Metro metro) {
        if (metro != null) {
            ContentValues cv = MetroContract.metroToContentValues(metro);
            if (!cvList.contains(cv)) {
                cvList.add(cv);
            }
        }
    }

    private void addEmployerToContentValues(List<ContentValues> cvList,
                                            Employer employer) {
        if (employer != null) {
            ContentValues cv = EmployerContract.employerToContentValues(employer);
            if (!cvList.contains(cv)) {
                cvList.add(cv);
            }
        }
    }

}
