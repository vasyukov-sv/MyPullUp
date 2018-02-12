package home.my.mypullup;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import home.my.mypullup.fragment.AnaliticTab;
import home.my.mypullup.fragment.EnterResultTab;
import home.my.mypullup.fragment.GraphTab;


class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final String[] tabTitles;

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
                return new EnterResultTab();
            case 1:
                return new AnaliticTab();
            case 2:
                return new GraphTab();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
