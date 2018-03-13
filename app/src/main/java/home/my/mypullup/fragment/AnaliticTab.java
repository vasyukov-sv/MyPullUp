package home.my.mypullup.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class AnaliticTab extends CommonTab implements AsyncResponseAnalitic {
    private TextView avgWeek;
    private TextView avgMonth;
    private TextView avgAll;

    private TextView maxWeek;
    private TextView maxMonth;
    private TextView maxAll;

    private TextView lastAttempts;
    private AttemptLoadAnaliticTask attemptLoadAnaliticTask = null;

    public AnaliticTab() {
    }

    private static String apply(Attempt attempt) {
        return String.format("%s:  %-3d - %-3d - %-3d - %-3d  %-3d" + System.lineSeparator(), attempt.getHumanDate(), attempt.getMorning1(), attempt.getMorning2(), attempt.getEvening1(), attempt.getEvening2(), attempt.getSumAttempt());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        avgWeek = (TextView) view.findViewById(R.id.avgweek);
        avgMonth = (TextView) view.findViewById(R.id.avgmonth);
        avgAll = (TextView) view.findViewById(R.id.avgall);
        maxWeek = (TextView) view.findViewById(R.id.maxweek);
        maxMonth = (TextView) view.findViewById(R.id.maxmonth);
        maxAll = (TextView) view.findViewById(R.id.maxall);
        lastAttempts = (TextView) view.findViewById(R.id.lastattempts);
        loadAnalitic();
    }

    public void loadAnalitic() {
        if (attemptLoadAnaliticTask == null) {
            attemptLoadAnaliticTask = (AttemptLoadAnaliticTask) new AttemptLoadAnaliticTask(this).execute((Void) null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.analitic, container, false);
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
            if (analitic.getAttemptList() != null) {
                lastAttempts.setText(analitic.getAttemptList().stream().map(AnaliticTab::apply).collect(Collectors.joining()));
            }
        }
        attemptLoadAnaliticTask = null;
    }
}