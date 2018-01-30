package home.my.mypullup.task;

import android.os.AsyncTask;
import home.my.mypullup.helper.DBHelper;
import home.my.mypullup.obj.Attempt;

import java.util.stream.Collectors;

public class AttemptLoadAnaliticTask extends AsyncTask<Void, Void, String> {
    private final DBHelper dbHelper;
    private final AsyncResponse delegate;

    public AttemptLoadAnaliticTask(DBHelper dbHelper, AsyncResponse delegate) {
        this.dbHelper = dbHelper;
        this.delegate = delegate;
    }

    private static String apply(Attempt attempt) {
        return String.valueOf(attempt.getMorning1()) + "-" + attempt.getMorning2() + "-" + attempt.getEvening1() + "-" + attempt.getMorning2() + "\n";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        delegate.onLoadAnalitics(result);
    }

    @Override
    protected String doInBackground(Void... voids) {
        return dbHelper.getLastAttempts().stream().map(AttemptLoadAnaliticTask::apply).collect(Collectors.joining());
    }
}