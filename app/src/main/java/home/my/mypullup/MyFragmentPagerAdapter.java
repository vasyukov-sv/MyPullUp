package home.my.mypullup;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import home.my.mypullup.fragment.EnterResult;
import home.my.mypullup.fragment.Graph;
import home.my.mypullup.fragment.LastResult;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[];

    MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        tabTitles = new String[]{context.getString(R.string.tab_enter), context.getString(R.string.last10), context.getString(R.string.graph)};
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EnterResult();
            case 1:
                return new LastResult();
            case 2:
                return new Graph();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
