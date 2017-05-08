package ru.hh.headhunterclient.loaders;

import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by alena on 09.05.2017.
 */

public abstract class AbstrAsyncTask<T, V, Q> extends AsyncTask<T, V, Q> {

    public void executeContent(T... content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, content);
        }
        else {
            this.execute(content);
        }
    }
}