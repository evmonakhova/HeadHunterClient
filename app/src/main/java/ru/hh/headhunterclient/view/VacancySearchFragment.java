package ru.hh.headhunterclient.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.hh.headhunterclient.R;
import ru.hh.headhunterclient.database.contract.VacanciesContract;
import ru.hh.headhunterclient.model.Vacancy;
import ru.hh.headhunterclient.presenter.IPresenter;
import ru.hh.headhunterclient.presenter.VacancySearchPresenter;
import ru.hh.headhunterclient.view.recyclerview.DividerItemDecoration;
import ru.hh.headhunterclient.view.recyclerview.EndlessRecyclerViewScrollListener;
import ru.hh.headhunterclient.view.recyclerview.VacanciesCursorAdapter;
import ru.hh.headhunterclient.view.recyclerview.VacanciesListAdapter;

/**
 * Created by alena on 06.05.2017.
 */

public class VacancySearchFragment extends Fragment implements IView {

    private static final String TAG = VacancySearchFragment.class.getName();

    private Activity mActivity;
    private EditText mSearchEdit;
    private SwipeRefreshLayout mSwipeContainer;
    private RecyclerView mVacanciesRecyclerView;
    private VacanciesListAdapter mListAdapter;
    private VacanciesCursorAdapter mCursorAdapter;
    private TextView mNoResultsMessage;
    private ProgressBar mProgressBar;
    private IPresenter mPresenter;
    private String keyword = "";

    public static VacancySearchFragment newInstance() {
        return new VacancySearchFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = getActivity();
        this.mPresenter = new VacancySearchPresenter(mActivity);
        mPresenter.attachView(this);
    }

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setRetainInstance(true);
        mListAdapter = new VacanciesListAdapter();
        mCursorAdapter = new VacanciesCursorAdapter(getContext(), null);
        mPresenter.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle state) {
        View view = inflater.inflate(R.layout.fragment_job_search, container, false);
        initToolbar(view);

        mNoResultsMessage = (TextView) view.findViewById(R.id.no_results_view);

        mVacanciesRecyclerView = (RecyclerView) view.findViewById(R.id.job_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        EndlessRecyclerViewScrollListener scrollListener
                = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page) {
                mPresenter.loadMore(page);
            }
        };
        mVacanciesRecyclerView.setLayoutManager(layoutManager);
        mVacanciesRecyclerView.setHasFixedSize(false);
        mVacanciesRecyclerView.setItemViewCacheSize(20);
        mVacanciesRecyclerView.setAdapter(mListAdapter);
        mVacanciesRecyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity()));
        mVacanciesRecyclerView.addOnScrollListener(scrollListener);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);

        return view;
    }

    private void initToolbar(View view) {
        Toolbar mActionBarToolbar = (Toolbar) view.findViewById(R.id.toolbar_actionbar);
        ((AppCompatActivity)mActivity).setSupportActionBar(mActionBarToolbar);

        mSearchEdit = (EditText) mActionBarToolbar.findViewById(R.id.search_input);
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                keyword = s.toString().trim();
                mPresenter.requestJobs(keyword);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        mSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager in = (InputMethodManager) getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(mSearchEdit.getWindowToken(), 0);
                return true;
            }
        });

        mSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestJobs(keyword);
            }
        });
    }

    @Override
    public void clearAdapters() {
        if (mListAdapter != null) {
            mListAdapter.clear();
        }
        if (mCursorAdapter != null) {
            mCursorAdapter.clear();
        }
    }

    @Override
    public void loadItemsToListAdapter(List<Vacancy> vacancies) {
        if (mListAdapter != null) {
            mVacanciesRecyclerView.setAdapter(mListAdapter);
            mListAdapter.addAll(vacancies);
        }
    }

    @Override
    public void swapCursor(Cursor cursor) {
        if (mCursorAdapter != null) {
            mVacanciesRecyclerView.setAdapter(mCursorAdapter);
            mCursorAdapter.changeCursor(cursor);
        }
    }

    @Override
    public void stopRefreshing() {
        if (mSwipeContainer != null) {
            mSwipeContainer.setRefreshing(false);
        }
    }

    @Override
    public void showNoResultsText() {
        if (mNoResultsMessage != null && mVacanciesRecyclerView != null) {
            mNoResultsMessage.setVisibility(View.VISIBLE);
            mNoResultsMessage.setText(R.string.no_results);
            mVacanciesRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNoInternetText() {
        if (mNoResultsMessage != null && mVacanciesRecyclerView != null) {
            mNoResultsMessage.setVisibility(View.VISIBLE);
            mNoResultsMessage.setText(R.string.no_internet_no_results);
            mVacanciesRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showResultsRecyclerView() {
        if (mNoResultsMessage != null && mVacanciesRecyclerView != null) {
            mNoResultsMessage.setVisibility(View.GONE);
            mVacanciesRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNetUnavailableMessage() {
        Toast.makeText(getContext(),
                R.string.network_unavailable,
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showProgress(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
