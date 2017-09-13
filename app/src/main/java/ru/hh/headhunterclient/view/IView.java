package ru.hh.headhunterclient.view;

import android.database.Cursor;

import java.util.List;

import ru.hh.headhunterclient.model.Vacancy;

/**
 * Created by alena on 06.05.2017.
 */

public interface IView {

    void clearAdapters();

    void loadItemsToListAdapter(List<Vacancy> vacancies);

    void swapCursor(Cursor cursor);

    void showProgress(boolean show);

    void stopRefreshing();

    void showNoResultsText();

    void showNoInternetText();

    void showResultsRecyclerView();

    void onNetUnavailableMessage();
}
