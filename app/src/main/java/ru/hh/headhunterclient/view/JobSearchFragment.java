package ru.hh.headhunterclient.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import ru.hh.headhunterclient.R;
import ru.hh.headhunterclient.model.Vacancy;
import ru.hh.headhunterclient.presenter.IPresenter;
import ru.hh.headhunterclient.presenter.JobSearchPresenter;

/**
 * Created by alena on 06.05.2017.
 */

public class JobSearchFragment extends Fragment implements IView {

    private static final String TAG = JobSearchFragment.class.getName();

    private Activity mActivity;
    private JobListAdapter mJobListAdapter;
    private ProgressBar mProgressBar;
    private IPresenter mPresenter;

    public static JobSearchFragment newInstance() {
        return new JobSearchFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = getActivity();
        this.mPresenter = new JobSearchPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setRetainInstance(true);
        mJobListAdapter = new JobListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle state) {
        mPresenter.onCreate();

        View view = inflater.inflate(R.layout.fragment_job_search, container, false);

        Toolbar mActionBarToolbar = (Toolbar) view.findViewById(R.id.toolbar_actionbar);
        ((AppCompatActivity)mActivity).setSupportActionBar(mActionBarToolbar);

        RecyclerView mJobList = (RecyclerView) view.findViewById(R.id.job_list);
        mJobList.setLayoutManager(new LinearLayoutManager(mActivity));
        mJobList.setHasFixedSize(false);
        mJobList.setItemViewCacheSize(20);
        mJobList.setAdapter(mJobListAdapter);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void loadItems(List<Vacancy> vacancies) {
        if (mJobListAdapter != null) {
            mJobListAdapter.addData(vacancies);
        }
    }

    @Override
    public void showProgress(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}