package ru.hh.headhunterclient.presenter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.List;

import ru.hh.headhunterclient.database.contract.VacanciesContract;
import ru.hh.headhunterclient.loaders.CacheDataAsyncTask;
import ru.hh.headhunterclient.model.SearchData;
import ru.hh.headhunterclient.model.Vacancy;
import ru.hh.headhunterclient.loaders.HttpLoader;
import ru.hh.headhunterclient.utils.PermissionsHelper;
import ru.hh.headhunterclient.view.IView;

/**
 * Created by alena on 06.05.2017.
 */

public class VacancySearchPresenter implements IPresenter {

    private static final String TAG = VacancySearchPresenter.class.getSimpleName();

    private IView mView;
    private Context mContext;
    private HttpLoader mHttpLoader;
    private CursorLoader mCursorLoader;

    public VacancySearchPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        initLoaders();
        loadData(false);
    }

    @Override
    public void attachView(IView view) {
        mView = view;
    }

    @Override
    public void requestJobs(String keyword) {
        initLoaders();
        mHttpLoader.setKeyword(keyword);
        mView.clearAdapters();
        loadData(false);
    }

    @Override
    public void loadMore(int page) {
        if (page*20 >= 2000){
            return;
        }
        initLoaders();
        mView.showProgress(true);
        mHttpLoader.setPage(page);
        loadData(true);
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
            mView.stopRefreshing();
            mView.showProgress(false);
            if (data != null) {
                List<Vacancy> items = data.getItems();
                if (items != null && items.size() > 0) {
                    mView.loadItemsToListAdapter(items);
                    mView.showResultsRecyclerView();
                    cacheData(items);
                } else {
                    mView.showNoResultsText();
                }
            } else {
                mView.showNoResultsText();
            }
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {
            mView.clearAdapters();
        }
    };

    private LoaderManager.LoaderCallbacks<Cursor> cursorCallback =
            new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(mContext, VacanciesContract.URI,
                    null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data != null && data.getCount() > 0) {
                mView.swapCursor(data);
                mView.showResultsRecyclerView();
            } else {
                mView.showNoInternetText();
            }
            mView.stopRefreshing();
            mView.showProgress(false);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mView.swapCursor(null);
        }
    };

    private void initLoaders() {
        LoaderManager lm = ((AppCompatActivity)mContext).getSupportLoaderManager();
        if (mHttpLoader == null) {
            mHttpLoader = (HttpLoader) lm.initLoader(0, null, httpCallback);
        }
        if (mCursorLoader == null) {
            mCursorLoader = (CursorLoader) lm.initLoader(2, null, cursorCallback);
        }
    }

    private boolean isNetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void loadData(final boolean isLoadMore) {
        if (isNetAvailable()) {
            mHttpLoader.forceLoad();
        } else {
            if (isLoadMore) {
                mView.onNetUnavailableMessage();
                mView.showProgress(false);
                mView.stopRefreshing();
            } else {
                loadFromDB();
            }
        }
    }

    private void loadFromDB() {
        PermissionsHelper.IRequestPermissionListener listener =
                new PermissionsHelper.IRequestPermissionListener() {
                    @Override
                    public void result(boolean granted) {
                        if (granted) {
                            mCursorLoader.forceLoad();
                        } else {
                            mView.showProgress(false);
                            mView.stopRefreshing();
                        }
                    }
                };
        if (PermissionsHelper.checkExternalStoragePermission(listener, (Activity) mContext)) {
            listener.result(true);
        }
    }

    private void cacheData(final List<Vacancy> items) {
        PermissionsHelper.IRequestPermissionListener listener =
                new PermissionsHelper.IRequestPermissionListener() {
                    @Override
                    public void result(boolean granted) {
                        if (granted){
                            new CacheDataAsyncTask(mContext)
                                    .executeContent(items);
                        }
                    }
                };
        if (PermissionsHelper.checkExternalStoragePermission(listener, (Activity) mContext)) {
            listener.result(true);
        }
    }
}
