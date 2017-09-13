package ru.hh.headhunterclient.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.hh.headhunterclient.R;
import ru.hh.headhunterclient.model.Address;
import ru.hh.headhunterclient.model.Area;
import ru.hh.headhunterclient.model.Department;
import ru.hh.headhunterclient.model.Employer;
import ru.hh.headhunterclient.model.Metro;
import ru.hh.headhunterclient.model.Salary;
import ru.hh.headhunterclient.model.Vacancy;

/**
 * Created by alena on 13.09.2017.
 */

class VacancyViewHolder extends RecyclerView.ViewHolder {

    private TextView mVacancyTitle;
    private TextView mSalary;
    private TextView mCompanyInfo;

    VacancyViewHolder(View itemView) {
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
            if (salary == null || salary.isSalaryUnkhown()) {
                mSalary.setText(R.string.unknown_salary);
            } else {
                mSalary.setText(salary.toString());
            }

            StringBuffer sbInfo = new StringBuffer();
            if (employer != null) {
                sbInfo.append(employer.getName());
            }
            if (department != null && department.getName() != null
                    && !department.getName().equals("")){
                sbInfo.append("::").append(department.getName());
            }
            sbInfo.append(", ");
            if (area != null) {
                sbInfo.append(area.getName());
            }
            if (address != null) {
                Metro metro = address.getMetro();
                if (metro != null){
                    String metroName = metro.getStationName();
                    if (metroName != null) {
                        sbInfo.append(", м. ").append(metroName);
                    }
                }
            }
            mCompanyInfo.setText(sbInfo);
        }
    }
}
