package ru.hh.headhunterclient.presenter;

import ru.hh.headhunterclient.view.IView;

/**
 * Created by alena on 06.05.2017.
 */

public interface IPresenter<T extends IView> {

    void onCreate();

    void onStart();

    void attachView(T view);

}
