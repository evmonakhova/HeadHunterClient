package ru.hh.headhunterclient.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import ru.hh.headhunterclient.R;
import ru.hh.headhunterclient.model.Vacancy;
import ru.hh.headhunterclient.presenter.IPresenter;
import ru.hh.headhunterclient.presenter.JobSearchPresenter;
import ru.hh.headhunterclient.view.recyclerview.DividerItemDecoration;
import ru.hh.headhunterclient.view.recyclerview.EndlessRecyclerViewScrollListener;
import ru.hh.headhunterclient.view.recyclerview.JobListAdapter;

/**
 * Created by alena on 06.05.2017.
 */

public class JobSearchFragment extends Fragment implements IView {

    private static final String TAG = JobSearchFragment.class.getName();

//    private static final String KEYWORD = "Android";

    private Activity mActivity;
    private JobListAdapter mJobListAdapter;
    private SwipeRefreshLayout mSwipeContainer;
    private ProgressBar mProgressBar;
    private IPresenter mPresenter;

    public static JobSearchFragment newInstance() {
        return new JobSearchFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = getActivity();
        this.mPresenter = new JobSearchPresenter(mActivity);
        mPresenter.attachView(this);
    }

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setRetainInstance(true);
        mJobListAdapter = new JobListAdapter();
        mPresenter.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle state) {
        View view = inflater.inflate(R.layout.fragment_job_search, container, false);

        Toolbar mActionBarToolbar = (Toolbar) view.findViewById(R.id.toolbar_actionbar);
        ((AppCompatActivity)mActivity).setSupportActionBar(mActionBarToolbar);

        mSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestJobs("");
            }
        });

        RecyclerView mJobList = (RecyclerView) view.findViewById(R.id.job_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        EndlessRecyclerViewScrollListener scrollListener
                = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page) {
                mPresenter.loadMore(page);
            }
        };
        mJobList.setLayoutManager(layoutManager);
        mJobList.setHasFixedSize(false);
        mJobList.setItemViewCacheSize(20);
        mJobList.setAdapter(mJobListAdapter);
        mJobList.addItemDecoration(new DividerItemDecoration(getActivity()));
        mJobList.addOnScrollListener(scrollListener);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);

        return view;
    }

    @Override
    public void clear() {
        if (mJobListAdapter != null) {
            mJobListAdapter.clear();
        }
    }

    @Override
    public void loadItems(List<Vacancy> vacancies) {
        if (mJobListAdapter != null) {
            mJobListAdapter.addAll(vacancies);
        }
    }

    @Override
    public void stopRefreshing() {
        mSwipeContainer.setRefreshing(false);
    }

    @Override
    public void showProgress(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
