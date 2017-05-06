package ru.hh.headhunterclient;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.hh.headhunterclient.utils.FragmentHelper;
import ru.hh.headhunterclient.view.JobSearchFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentHelper fragmentHelper = new FragmentHelper(getSupportFragmentManager());
        String tag = "JOB_SEARCH_FR";
        Fragment fragment = fragmentHelper.getFragmentByTag(tag);

        if (fragment == null) {
            fragment = JobSearchFragment.newInstance();
            fragmentHelper.addFragment(fragment, tag);
        } else {
            fragmentHelper.replaceFragment(fragment, tag);
        }
    }
}
