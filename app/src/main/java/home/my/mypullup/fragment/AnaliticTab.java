package home.my.mypullup.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import home.my.mypullup.R;
import home.my.mypullup.obj.Analitic;
import home.my.mypullup.task.AsyncResponseAnalitic;
import home.my.mypullup.task.AttemptLoadAnaliticTask;

import static java.util.Optional.ofNullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnaliticTab extends Fragment implements AsyncResponseAnalitic {

    private TextView avgWeek;
    private TextView avgMonth;
    private TextView avgAll;

    private TextView maxWeek;
    private TextView maxMonth;
    private TextView maxAll;

    private TextView lastAttempts;
    private AttemptLoadAnaliticTask attemptLoadAnaliticTask = null;

    private View mProgressView;

    public AnaliticTab() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avgWeek = (TextView) getView().findViewById(R.id.avgweek);
        avgMonth = (TextView) getView().findViewById(R.id.avgmonth);
        avgAll = (TextView) getView().findViewById(R.id.avgall);
        maxWeek = (TextView) getView().findViewById(R.id.maxweek);
        maxMonth = (TextView) getView().findViewById(R.id.maxmonth);
        maxAll = (TextView) getView().findViewById(R.id.maxall);

        lastAttempts = (TextView) getView().findViewById(R.id.lastattempts);

//        loadAnalitic();
    }

    private void loadAnalitic() {
        if (attemptLoadAnaliticTask != null) {
            return;
        }
        showProgress(true);
        attemptLoadAnaliticTask = new AttemptLoadAnaliticTask(this);
        attemptLoadAnaliticTask.execute((Void) null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.analitic, container, false);
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onLoadAnalitic(Analitic analitic) {
        if (analitic != null) {
            avgWeek.setText(String.format("%s", ofNullable(analitic.getAvgWeek().toString()).orElse("")));
            avgMonth.setText(String.format("%s", ofNullable(analitic.getAvgMonth().toString()).orElse("")));
            avgAll.setText(String.format("%s", ofNullable(analitic.getAvgAll().toString()).orElse("")));
            maxWeek.setText(String.format("%s", ofNullable(analitic.getMaxWeek().toString()).orElse("")));
            maxMonth.setText(String.format("%s", ofNullable(analitic.getMaxMonth().toString()).orElse("")));
            maxAll.setText(String.format("%s", ofNullable(analitic.getMaxAll().toString()).orElse("")));

            lastAttempts.setText(String.format("%s", ofNullable(analitic.getAttemptList().toString()).orElse("")));
        }
        attemptLoadAnaliticTask = null;
        showProgress(false);
    }
}
