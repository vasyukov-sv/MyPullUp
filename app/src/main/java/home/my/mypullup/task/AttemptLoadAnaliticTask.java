package home.my.mypullup.task;

import android.os.AsyncTask;
import home.my.mypullup.helper.DBHelper;
import home.my.mypullup.obj.Attempt;

import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class AttemptLoadAnaliticTask extends AsyncTask<Void, Void, String> {
    private final DBHelper dbHelper;
    private final AsyncResponseEnter delegate;

    public AttemptLoadAnaliticTask(DBHelper dbHelper, AsyncResponseEnter delegate) {
        this.dbHelper = dbHelper;
        this.delegate = delegate;
    }

    private static String apply(Attempt attempt) {
        int sumAttempt = attempt.getMorning1() + attempt.getMorning2() + attempt.getEvening1() + attempt.getEvening2();
        return String.format("%s-%s-%s-%s" + System.lineSeparator(),
                ofNullable(attempt.getMorning1().toString()).orElse("0"),
                ofNullable(attempt.getMorning2().toString()).orElse("0"),
                ofNullable(attempt.getEvening1().toString()).orElse("0"),
                ofNullable(attempt.getEvening2().toString()).orElse("0")
        );
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
//        delegate.onLoadAnalitics(result);
    }

    @Override
    protected String doInBackground(Void... voids) {
        return dbHelper.getLastAttempts().stream().map(AttemptLoadAnaliticTask::apply).collect(Collectors.joining());
    }
}