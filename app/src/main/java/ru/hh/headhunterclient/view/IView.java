package ru.hh.headhunterclient.view;

import java.util.List;

import ru.hh.headhunterclient.model.Vacancy;

/**
 * Created by alena on 06.05.2017.
 */

public interface IView {

    void clear();

    void loadItems(List<Vacancy> vacancies);

    void showProgress(boolean show);

    void stopRefreshing();

    void showNoResultsText();

    void showNoInternetText();

    void showResultsList();

    void onNetUnavailableMessage();
}
