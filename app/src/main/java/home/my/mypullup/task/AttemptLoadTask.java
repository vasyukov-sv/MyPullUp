package home.my.mypullup.task;

import android.os.AsyncTask;
import home.my.mypullup.helper.DBHelper;
import home.my.mypullup.obj.Attempt;

public class AttemptLoadTask extends AsyncTask<Void, Void, Attempt> {

    private final AsyncResponseEnter delegate;

    public AttemptLoadTask(AsyncResponseEnter delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPostExecute(Attempt attempt) {
        super.onPostExecute(attempt);
        delegate.onLoadAttempt(attempt);
    }

    @Override
    protected Attempt doInBackground(Void... voids) {
        return DBHelper.getInstance(delegate.getContext()).getAttempt();
    }
}