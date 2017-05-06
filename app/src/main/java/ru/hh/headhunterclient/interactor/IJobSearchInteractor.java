package ru.hh.headhunterclient.interactor;

import java.util.List;

import ru.hh.headhunterclient.model.Vacancy;

/**
 * Created by alena on 06.05.2017.
 */

public interface IJobSearchInteractor {

    interface OnJobListReceivedListener {
        void onServerError(String message);
        void onSuccess(List<Vacancy> vacancies);
    }

    void jobRequest(String keyword, OnJobListReceivedListener onJobsReceived);

}
