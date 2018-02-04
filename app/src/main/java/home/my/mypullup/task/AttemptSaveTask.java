package home.my.mypullup.task;

import android.os.AsyncTask;
import home.my.mypullup.helper.DBHelper;
import home.my.mypullup.obj.Attempt;

public class AttemptSaveTask extends AsyncTask<Attempt, Void, Void> {
    private final AsyncResponseEnter delegate;
    private final DBHelper dbHelper;

    public AttemptSaveTask(DBHelper dbHelper, AsyncResponseEnter delegate) {
        this.delegate = delegate;
        this.dbHelper = dbHelper;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        delegate.onSaveAttempt();
    }

    @Override
    protected Void doInBackground(Attempt... attempts) {
        dbHelper.addAttempt(attempts[0]);
        return null;
    }
}