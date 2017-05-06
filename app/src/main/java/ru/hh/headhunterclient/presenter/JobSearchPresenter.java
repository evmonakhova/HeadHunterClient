package ru.hh.headhunterclient.presenter;

import java.util.ArrayList;
import java.util.List;

import ru.hh.headhunterclient.model.Vacancy;
import ru.hh.headhunterclient.view.IView;

/**
 * Created by alena on 06.05.2017.
 */

public class JobSearchPresenter implements IPresenter {

    private IView mView;
    List<Vacancy> vacancies = new ArrayList<>();

    public JobSearchPresenter() {
    }

    @Override
    public void onCreate() {
        vacancies.add(new Vacancy("1 Android developer", "from 100000 to 150000 RU",
                "HeadHunter", "Moscow", "Alekseevskaya"));
        vacancies.add(new Vacancy("2 Android developer", "from 80000 to 120000 RU",
                "I-FREE", "Saint-Petersburg", "Chkalovskaya"));
        vacancies.add(new Vacancy("3 Android developer, Java/C++", "from 90000 to 140000 RU",
                "CYBRUS", "Moscow", "Paveletskaya"));
        vacancies.add(new Vacancy("4 Android developer", "from 100000 to 150000 RU",
                "HeadHunter", "Moscow", "Alekseevskaya"));
        vacancies.add(new Vacancy("5 Android developer", "from 80000 to 120000 RU",
                "I-FREE", "Saint-Petersburg", "Chkalovskaya"));
        vacancies.add(new Vacancy("6 Android developer, Java/C++", "from 90000 to 140000 RU",
                "CYBRUS", "Moscow", "Paveletskaya"));
        vacancies.add(new Vacancy("7 Android developer", "from 100000 to 150000 RU",
                "HeadHunter", "Moscow", "Alekseevskaya"));
        vacancies.add(new Vacancy("8 Android developer", "from 80000 to 120000 RU",
                "I-FREE", "Saint-Petersburg", "Chkalovskaya"));
        vacancies.add(new Vacancy("9 Android developer, Java/C++", "from 90000 to 140000 RU",
                "CYBRUS", "Moscow", "Paveletskaya"));
        if (mView != null) {
            mView.loadItems(vacancies);
        }
    }

    @Override
    public void onStart() {
    }

    @Override
    public void attachView(IView view) {
        mView = view;
    }
}
