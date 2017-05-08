package ru.hh.headhunterclient.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.List;

import ru.hh.headhunterclient.cache.orm.AddressORM;
import ru.hh.headhunterclient.cache.orm.AreaORM;
import ru.hh.headhunterclient.cache.orm.EmployerORM;
import ru.hh.headhunterclient.cache.orm.MetroORM;
import ru.hh.headhunterclient.cache.orm.VacancyORM;
import ru.hh.headhunterclient.loaders.CacheDataAsyncTask;
import ru.hh.headhunterclient.model.Address;
import ru.hh.headhunterclient.model.SearchData;
import ru.hh.headhunterclient.model.Vacancy;
import ru.hh.headhunterclient.loaders.HttpLoader;
import ru.hh.headhunterclient.view.IView;

/**
 * Created by alena on 06.05.2017.
 */

public class JobSearchPresenter implements IPresenter, LoaderManager.LoaderCallbacks<String>{

    private static final String TAG = JobSearchPresenter.class.getSimpleName();

    private IView mView;
    private Context mContext;
    private HttpLoader mHttpLoader;

    public JobSearchPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        initHttpLoader();
        mHttpLoader.forceLoad();
    }

    @Override
    public void attachView(IView view) {
        mView = view;
    }

    @Override
    public void requestJobs(String keyword) {
//        mHttpLoader.setKeyword(keyword);
        initHttpLoader();
        mView.clear();
        mHttpLoader.forceLoad();
    }

    @Override
    public void loadMore(int page) {
        if (page*20 >= 2000){
            return;
        }
        initHttpLoader();
        mView.showProgress(true);
        mHttpLoader.setPage(page);
        mHttpLoader.forceLoad();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new HttpLoader(mContext);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String json) {
        Gson gson = new Gson();
        SearchData data = gson.fromJson(json, SearchData.class);
        List<Vacancy> items = data.getItems();
        mView.loadItems(items);
        mView.stopRefreshing();
        mView.showProgress(false);
        CacheDataAsyncTask cacheDataAsyncTask = new CacheDataAsyncTask(mContext);
        cacheDataAsyncTask.executeContent(items);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        mView.clear();
    }

    private void initHttpLoader() {
        if (mHttpLoader == null) {
            mHttpLoader = (HttpLoader) ((AppCompatActivity)mContext)
                    .getSupportLoaderManager()
                    .initLoader(0, null, this);
        }
    }
}
