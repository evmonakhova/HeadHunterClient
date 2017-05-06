package ru.hh.headhunterclient.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import ru.hh.headhunterclient.R;

/**
 * Created by alena on 16.08.2016.
 */
public class FragmentHelper {

    private static final String TAG = FragmentHelper.class.getName();

    private FragmentManager fragmentManager;

    public FragmentHelper(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    public void addFragment(Fragment fragment, String tag){
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container, fragment, tag)
                    .commit();
        }
    }

    public void replaceFragment(Fragment fragment, String tag){
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, tag)
                    .commit();
        }
    }

    public void addFragmentToBackStack(Fragment fragment, String tag){
        if (fragment != null && fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container, fragment, tag)
                    .addToBackStack("MAIN_STACK")
                    .commit();
        }
    }

    public Fragment getFragmentByTag(String tag) {
        if (fragmentManager != null)
            return fragmentManager.findFragmentByTag(tag);
        else
            return null;
    }

    public void popBackStack(){
        if (fragmentManager != null) {
            int count = fragmentManager.getBackStackEntryCount();
            if (count != 0)
                fragmentManager.popBackStack();
        }
    }

}
