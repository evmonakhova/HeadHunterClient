package ru.hh.headhunterclient.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.hh.headhunterclient.model.Vacancy;
import ru.hh.headhunterclient.net.HttpLoader;
import ru.hh.headhunterclient.view.IView;

/**
 * Created by alena on 06.05.2017.
 */

public class JobSearchPresenter implements IPresenter, LoaderManager.LoaderCallbacks<String>{

    private static final String TAG = JobSearchPresenter.class.getSimpleName();

    private IView mView;
    private Context mContext;
    private HttpLoader mHttpLoader;
    private List<Vacancy> vacancies = new ArrayList<>();

    public JobSearchPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        mHttpLoader = (HttpLoader) ((AppCompatActivity)mContext)
                .getSupportLoaderManager()
                .initLoader(0, null, this);
    }

    @Override
    public void attachView(IView view) {
        mView = view;
    }

    @Override
    public void requestJobs(String keyword) {
//        mHttpLoader.setKeyword(keyword);
        mHttpLoader.forceLoad();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new HttpLoader(mContext);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        //todo: parse json
        //mView.loadItems(vacancies);
        Log.d(TAG, String.format("\n ------------ \n onLoadFinished. data: \n %s \n", data));
        mView.stopRefreshing();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
