package ru.hh.headhunterclient.presenter;

import ru.hh.headhunterclient.view.IView;

/**
 * Created by alena on 06.05.2017.
 */

public class JobSearchPresenter implements IPresenter {

    private IView mView;

    public JobSearchPresenter() {

    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void attachView(IView view) {
        mView = view;
    }
}
