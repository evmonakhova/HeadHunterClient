package ru.hh.headhunterclient.view.recyclerview;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.hh.headhunterclient.R;
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
 * Created by alena on 11.05.2017.
 */

public class VacanciesCursorAdapter extends CursorRecyclerViewAdapter<VacancyViewHolder> {

    private static final String TAG = VacanciesCursorAdapter.class.getSimpleName();

    private ContentResolver mResolver;

    public VacanciesCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mResolver = context.getContentResolver();
    }

    @Override
    public VacancyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vacancy, parent, false);
        return new VacancyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VacancyViewHolder viewHolder, Cursor cursor) {
        if (cursor == null || !cursor.moveToFirst()) {
            return;
        }
        Vacancy vacancy = VacanciesContract.cursorToVacancy(cursor);
        if (vacancy != null) {
            Area area = getAreaFromCursor(vacancy.getArea().getId());
            if (area != null) {
                vacancy.setArea(area);
            }
            Address address = getAddressFromCursor(vacancy.getAddress().getId());
            if (address != null) {
                vacancy.setAddress(address);
            }
            Employer employer = getEmployerFromCursor(vacancy.getEmployer().getId());
            if (employer != null) {
                vacancy.setEmployer(employer);
            }
            viewHolder.bind(vacancy);
        }
    }

    public void clear() {
        mResolver.delete(VacanciesContract.URI, null, null);
        mResolver.delete(AreaContract.URI, null, null);
        mResolver.delete(AddressContract.URI, null, null);
        mResolver.delete(MetroContract.URI, null, null);
        mResolver.delete(EmployerContract.URI, null, null);
    }

    private Area getAreaFromCursor(int id) {
        if (id <= 0) {
            return null;
        }
        Cursor cursor = mResolver.query(
                AreaContract.buildUri(id), null, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        Area area = AreaContract.cursorToArea(cursor);
        cursor.close();
        return area;
    }

    private Address getAddressFromCursor(int id) {
        if (id <= 0) {
            return null;
        }
        Cursor cursor = mResolver.query(
                AddressContract.buildUri(id), null, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        Address address = AddressContract.cursorToAddress(cursor);
        cursor.close();
        Metro metro = getMetroFromCursor(address.getMetro().getStationId());
        if (metro != null) {
            address.setMetro(metro);
        }
        return address;
    }

    private Metro getMetroFromCursor(Double stationId) {
        if (stationId <= 0) {
            return null;
        }
        Cursor cursor = mResolver.query(
                MetroContract.URI, null,
                MetroContract.STATION_ID + " = ?",
                new String[]{ stationId.toString() }, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        Metro metro = MetroContract.cursorToMetro(cursor);
        cursor.close();
        return metro;
    }

    private Employer getEmployerFromCursor(int id) {
        if (id <= 0) {
            return null;
        }
        Cursor cursor = mResolver.query(
                EmployerContract.buildUri(id), null, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        Employer employer = EmployerContract.cursorToEmployer(cursor);
        cursor.close();
        return employer;
    }
}
