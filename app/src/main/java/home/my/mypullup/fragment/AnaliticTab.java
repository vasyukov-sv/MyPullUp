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
import home.my.mypullup.obj.Attempt;
import home.my.mypullup.task.AsyncResponseAnalitic;
import home.my.mypullup.task.AttemptLoadAnaliticTask;

import java.util.stream.Collectors;

import static home.my.mypullup.helper.Utils.doubleToString;
import static home.my.mypullup.helper.Utils.integerToString;
import static java.util.Optional.ofNullable;
import static java.util.OptionalLong.of;

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

    private static String apply(Attempt attempt) {
        int sumAttempt = attempt.getMorning1() + attempt.getMorning2() + attempt.getEvening1() + attempt.getEvening2();
        return String.format("%s:  %s - %s - %s - %s    %d" + System.lineSeparator(), attempt.getHumanDate(), ofNullable(attempt.getMorning1().toString()).orElse("0"), ofNullable(attempt.getMorning2().toString()).orElse("0"), ofNullable(attempt.getEvening1().toString()).orElse("0"), ofNullable(attempt.getEvening2().toString()).orElse("0"), of(sumAttempt).orElse(0));
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

        loadAnalitic();
    }

    private void loadAnalitic() {
        if (attemptLoadAnaliticTask != null) {
            return;
        }
//        showProgress(true);
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
            avgWeek.setText(doubleToString(analitic.getAvgWeek()));
            avgMonth.setText(doubleToString(analitic.getAvgMonth()));
            avgAll.setText(doubleToString(analitic.getAvgAll()));

            maxWeek.setText(integerToString(analitic.getMaxWeek()));
            maxMonth.setText(integerToString(analitic.getMaxMonth()));
            maxAll.setText(integerToString(analitic.getMaxAll()));

            lastAttempts.setText(analitic.getAttemptList().stream().map(AnaliticTab::apply).collect(Collectors.joining()));
        }
        attemptLoadAnaliticTask = null;
//        showProgress(false);
    }
}
