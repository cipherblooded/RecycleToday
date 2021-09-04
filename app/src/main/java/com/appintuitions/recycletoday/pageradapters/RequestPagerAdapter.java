package com.appintuitions.recycletoday.pageradapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.appintuitions.recycletoday.R;
import com.appintuitions.recycletoday.ui.fragments.Complaint_History;
import com.appintuitions.recycletoday.ui.fragments.Complaint_Status;

public class RequestPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES =
            new int[] { R.string.complaint_status, R.string.complaint_history };
    private final Context mContext;
    public RequestPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Complaint_Status();
            case 1:
                return new Complaint_History();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }
}
