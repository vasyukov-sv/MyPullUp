package home.my.mypullup;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import home.my.mypullup.fragment.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    public static final int DATABASE_VERSION = 3;
    public static final String TABLE = "tScore";
    public static final int DAYS_AGO = 10;
    public static final int DAYS_AGO_GRAPH = 60;
    public static final int MAX_VALUE_ATTEMPT = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onLoadAnalitic() {
//        getFragmentManager().findFragmentByTag();
//        getSupportFragmentManager
//        Fragment view = (Fragment) getView().findViewById(R.id.analiticfragment);
//        view.setText(item);
    }
}