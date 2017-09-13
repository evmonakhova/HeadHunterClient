package ru.hh.headhunterclient;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.hh.headhunterclient.utils.FragmentHelper;
import ru.hh.headhunterclient.utils.PermissionsHelper;
import ru.hh.headhunterclient.view.VacancySearchFragment;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG = "VACANCY_SEARCH_FR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentHelper fragmentHelper = new FragmentHelper(getSupportFragmentManager());
        Fragment fragment = fragmentHelper.getFragmentByTag(FRAGMENT_TAG);

        if (fragment == null) {
            fragment = VacancySearchFragment.newInstance();
            fragmentHelper.addFragment(fragment, FRAGMENT_TAG);
        } else {
            fragmentHelper.replaceFragment(fragment, FRAGMENT_TAG);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsHelper.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
