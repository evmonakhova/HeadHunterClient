package ru.hh.headhunterclient.loaders;

import android.content.Context;

import java.util.List;

import ru.hh.headhunterclient.database.orm.AddressORM;
import ru.hh.headhunterclient.database.orm.AreaORM;
import ru.hh.headhunterclient.database.orm.EmployerORM;
import ru.hh.headhunterclient.database.orm.MetroORM;
import ru.hh.headhunterclient.database.orm.VacancyORM;
import ru.hh.headhunterclient.model.Address;
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
        for (List<Vacancy> vacancies: lists) {
            for (Vacancy vacancy : vacancies) {
                VacancyORM.insertVacancy(mContext, vacancy);
                AreaORM.insertArea(mContext, vacancy.getArea());
                Address address = vacancy.getAddress();
                AddressORM.insertAddress(mContext, address);
                if (address != null) {
                    MetroORM.insertMetro(mContext, address.getMetro());
                }
                EmployerORM.insertEmployer(mContext, vacancy.getEmployer());
            }
        }
        return null;
    }

}
