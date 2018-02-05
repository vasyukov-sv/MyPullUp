package home.my.mypullup.task;

import android.os.AsyncTask;
import home.my.mypullup.helper.DBHelper;
import home.my.mypullup.obj.Analitic;
import home.my.mypullup.obj.Attempt;

import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.OptionalLong.of;

public class AttemptLoadAnaliticTask extends AsyncTask<Void, Void, Analitic> {

    private final AsyncResponseAnalitic delegate;

    public AttemptLoadAnaliticTask(AsyncResponseAnalitic delegate) {
        this.delegate = delegate;
    }

    private static String apply(Attempt attempt) {
        int sumAttempt = attempt.getMorning1() + attempt.getMorning2() + attempt.getEvening1() + attempt.getEvening2();
        return String.format("%s-%s-%s-%s  %d" + System.lineSeparator(), ofNullable(attempt.getMorning1().toString()).orElse("0"), ofNullable(attempt.getMorning2().toString()).orElse("0"), ofNullable(attempt.getEvening1().toString()).orElse("0"), ofNullable(attempt.getEvening2().toString()).orElse("0"), of(sumAttempt).orElse(0));
    }

    @Override
    protected void onPostExecute(Analitic result) {
        super.onPostExecute(result);
        delegate.onLoadAnalitic(result);
    }

    @Override
    protected Analitic doInBackground(Void... voids) {
        DBHelper.getInstance(delegate.getContext()).getLastAttempts().stream().map(AttemptLoadAnaliticTask::apply).collect(Collectors.joining());
        return null;
    }
}