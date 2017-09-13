package ru.hh.headhunterclient.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.hh.headhunterclient.R;
import ru.hh.headhunterclient.model.Vacancy;

/**
 * Created by alena on 06.05.2017.
 */

public class VacanciesListAdapter extends RecyclerView.Adapter<VacancyViewHolder> {

    private List<Vacancy> items;

    public VacanciesListAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public VacancyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vacancy, parent, false);
        return new VacancyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VacancyViewHolder holder, int position) {
        if(holder == null || items == null ||
                (position < 0 || position >= items.size())) {
            return;
        }
        Vacancy item = getItem(position);
        if (item != null) {
            holder.bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }


    public void addAll(List<Vacancy> vacancies) {
        items.addAll(vacancies);
        notifyDataSetChanged();
    }

    private Vacancy getItem(int position) {
        Vacancy item = null;
        if(position >= 0 && position < items.size()
                && items.get(position) != null) {
            item = items.get(position);
        }
        return item;
    }
}
