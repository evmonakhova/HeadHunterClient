package ru.hh.headhunterclient.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.hh.headhunterclient.R;
import ru.hh.headhunterclient.model.Address;
import ru.hh.headhunterclient.model.Area;
import ru.hh.headhunterclient.model.Department;
import ru.hh.headhunterclient.model.Employer;
import ru.hh.headhunterclient.model.Metro;
import ru.hh.headhunterclient.model.Salary;
import ru.hh.headhunterclient.model.Vacancy;

/**
 * Created by alena on 06.05.2017.
 */

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {

    private List<Vacancy> items;

    public JobListAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vacancy, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder == null || items == null ||
                (position < 0 || position >= items.size())) {
            return;
        }
        Vacancy item = getItem(position);
        if(item != null) {
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

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mVacancyTitle;
        TextView mSalary;
        TextView mCompanyInfo;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setFocusable(true);

            mVacancyTitle = (TextView) itemView.findViewById(R.id.vacancy_title);
            mSalary = (TextView) itemView.findViewById(R.id.salary);
            mCompanyInfo = (TextView) itemView.findViewById(R.id.company_info);
        }

        void bind(Vacancy item) {
            if (item != null) {
                Salary salary = item.getSalary();
                Employer employer = item.getEmployer();
                Department department = item.getDepartment();
                Area area = item.getArea();
                Address address = item.getAddress();

                mVacancyTitle.setText(item.getName());
                if (salary == null) {
                    mSalary.setText(R.string.unknown_salary);
                } else {
                    mSalary.setText(salary.toString());
                }

                StringBuffer sbInfo = new StringBuffer();
                if (employer != null) {
                    sbInfo.append(employer.getName());
                }
                if (department != null){
                    sbInfo.append("::").append(department.getName());
                }
                sbInfo.append(", ");
                if (area != null) {
                    sbInfo.append(area.getName());
                }
                if (address != null) {
                    Metro metro = address.getMetro();
                    if (metro != null){
                        String metroName = metro.getName();
                        if (metroName != null) {
                            sbInfo.append(", ").append(metroName);
                        }
                    }
                }
                mCompanyInfo.setText(sbInfo);
            }
        }
    }
}
