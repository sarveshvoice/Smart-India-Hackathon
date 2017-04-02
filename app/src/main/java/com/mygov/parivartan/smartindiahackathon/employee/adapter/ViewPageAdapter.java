package com.mygov.parivartan.smartindiahackathon.employee.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mygov.parivartan.smartindiahackathon.R;
import com.mygov.parivartan.smartindiahackathon.employee.fragment.EmployeeSettting;
import com.mygov.parivartan.smartindiahackathon.employee.fragment.JobSearch;
import com.mygov.parivartan.smartindiahackathon.employee.fragment.JobSuggestion;

/**
 * Created by deepak on 01-04-2017.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public ViewPageAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new JobSearch();
        }
        else if(position ==1 ){
            return new JobSuggestion();
        }
        else if(position ==2 ){
            return new EmployeeSettting();
        }
        return new JobSearch();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position ==0){
            return mContext.getString(R.string.search);
        }
        else if(position ==1){
            return mContext.getString(R.string.jobs);
        }
        else if(position ==2)
        {
            return mContext.getString(R.string.Profile);
        }
        else
            return mContext.getString(R.string.Profile);

    }
}
