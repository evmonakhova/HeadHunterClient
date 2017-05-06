package ru.hh.headhunterclient.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.hh.headhunterclient.R;
import ru.hh.headhunterclient.model.Vacancy;

/**
 * Created by alena on 06.05.2017.
 */

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {

    private List<Vacancy> mVacancies;

    public JobListAdapter() {
        mVacancies = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vacancy, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder == null || mVacancies == null ||
                (position < 0 || position >= mVacancies.size())) {
            return;
        }
        Vacancy item = getItem(position);
        if(item != null) {
            holder.bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return mVacancies.size();
    }

    public void addData(List<Vacancy> vacancies) {
        mVacancies.addAll(vacancies);
        notifyDataSetChanged();
    }

    public Vacancy getItem(int position) {
        Vacancy item = null;
        if(position >= 0 && position < mVacancies.size()
                && mVacancies.get(position) != null) {
            item = mVacancies.get(position);
        }
        return item;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mJobTitle;
        TextView mSalary;
        TextView mCompanyInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            mJobTitle = (TextView) itemView.findViewById(R.id.vacancy_title);
            mSalary = (TextView) itemView.findViewById(R.id.salary);
            mCompanyInfo = (TextView) itemView.findViewById(R.id.company_info);
        }

        public void bind(Vacancy item) {
            if (item != null) {
                mJobTitle.setText(item.getJobTitle());
                mSalary.setText(item.getSalary());
                StringBuffer sbInfo = new StringBuffer();
                sbInfo.append(item.getCompanyName())
                        .append(", ").append(item.getCity())
                        .append(", ").append(item.getSubwayStation());
                mCompanyInfo.setText(sbInfo);
            }
        }
    }
}
