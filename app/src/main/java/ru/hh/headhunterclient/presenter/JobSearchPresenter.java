package ru.hh.headhunterclient.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.List;

import ru.hh.headhunterclient.loaders.CacheDataAsyncTask;
import ru.hh.headhunterclient.loaders.DBLoader;
import ru.hh.headhunterclient.model.SearchData;
import ru.hh.headhunterclient.model.Vacancy;
import ru.hh.headhunterclient.loaders.HttpLoader;
import ru.hh.headhunterclient.view.IView;

/**
 * Created by alena on 06.05.2017.
 */

public class JobSearchPresenter implements IPresenter {

    private static final String TAG = JobSearchPresenter.class.getSimpleName();

    private IView mView;
    private Context mContext;
    private HttpLoader mHttpLoader;
    private DBLoader mDbLoader;

    public JobSearchPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        initLoaders();
        if (isNetAvailable()) {
            mHttpLoader.forceLoad();
        } else {
            mDbLoader.forceLoad();
        }
    }

    @Override
    public void attachView(IView view) {
        mView = view;
    }

    @Override
    public void requestJobs(String keyword) {
//        mHttpLoader.setKeyword(keyword);
        initLoaders();
        mView.clear();
        if (isNetAvailable()) {
            mHttpLoader.forceLoad();
        } else {
            mDbLoader.forceLoad();
        }
    }

    @Override
    public void loadMore(int page) {
        if (page*20 >= 2000){
            return;
        }
        initLoaders();
        mView.showProgress(true);
        mHttpLoader.setPage(page);
        if (isNetAvailable()) {
            mHttpLoader.forceLoad();
        } else {
            mView.showProgress(false);
        }
    }

    private LoaderManager.LoaderCallbacks<String> httpCallback =
            new LoaderManager.LoaderCallbacks<String>() {
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
    };

    private LoaderManager.LoaderCallbacks<List<Vacancy>> dbCallback =
            new LoaderManager.LoaderCallbacks<List<Vacancy>>() {
        @Override
        public Loader<List<Vacancy>> onCreateLoader(int id, Bundle args) {
            return new DBLoader(mContext);
        }

        @Override
        public void onLoadFinished(Loader<List<Vacancy>> loader, List<Vacancy> data) {
            mView.loadItems(data);
            mView.stopRefreshing();
            mView.showProgress(false);
        }

        @Override
        public void onLoaderReset(Loader<List<Vacancy>> loader) {
            mView.clear();
        }
    };

    private void initLoaders() {
        if (mHttpLoader == null) {
            mHttpLoader = (HttpLoader) ((AppCompatActivity)mContext)
                    .getSupportLoaderManager()
                    .initLoader(0, null, httpCallback);
        }
        if (mDbLoader == null) {
            mDbLoader = (DBLoader) ((AppCompatActivity)mContext)
                    .getSupportLoaderManager()
                    .initLoader(1, null, dbCallback);
        }
    }

    private boolean isNetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
