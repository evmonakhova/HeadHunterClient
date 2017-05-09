package ru.hh.headhunterclient.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.util.List;

import ru.hh.headhunterclient.cache.orm.AddressORM;
import ru.hh.headhunterclient.cache.orm.AreaORM;
import ru.hh.headhunterclient.cache.orm.EmployerORM;
import ru.hh.headhunterclient.cache.orm.MetroORM;
import ru.hh.headhunterclient.cache.orm.VacancyORM;
import ru.hh.headhunterclient.model.Address;
import ru.hh.headhunterclient.model.Metro;
import ru.hh.headhunterclient.model.Vacancy;

/**
 * Created by alena on 07.05.2017.
 */

public class DBLoader extends AsyncTaskLoader<List<Vacancy>> {

    private static final String TAG = DBLoader.class.getSimpleName();

    private Context mContext;

    public DBLoader(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public List<Vacancy> loadInBackground() {
        List<Vacancy> vacancies = VacancyORM.getVacancies(mContext);
        for (Vacancy vacancy: vacancies) {
            vacancy.setArea(AreaORM.findAreaById(mContext, vacancy.getArea().getId()));
            int addressId = vacancy.getAddress().getId();
            Address address = AddressORM.findAddressById(mContext, addressId);
            if (address != null) {
                Metro metro = address.getMetro();
                if (metro != null) {
                    address.setMetro(MetroORM.findMetroById(mContext, metro.getStationId()));
                }
                vacancy.setAddress(address);
            }
            vacancy.setEmployer(EmployerORM.findEmployerById(mContext, vacancy.getEmployer().getId()));
        }
        return vacancies;
    }

    @Override
    public void deliverResult(List<Vacancy> data) {
        super.deliverResult(data);
    }

}
