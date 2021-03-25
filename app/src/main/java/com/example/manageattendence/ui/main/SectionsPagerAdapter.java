package com.example.manageattendence.ui.main;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.manageattendence.R;
import com.example.manageattendence.ui.home.HomeFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private static final String[] title=new String[]{"Monday","Tuesday","Wednesday","Thrusday","Friday","Saturday"};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment=new monday();
                break;
            case 1:
                fragment=new tuesday();
                break;
            case 2:
                fragment=new wednesday();
                break;
            case 3:
                fragment=new thrusday();
                break;
            case 4:
                fragment=new friday();
                break;
            case 5:
                fragment=new saturday();
                break;
            default:
                fragment=null;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 6;
    }
}