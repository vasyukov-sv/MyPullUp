package home.my.mypullup.task;

import android.os.AsyncTask;
import home.my.mypullup.helper.DBHelper;
import home.my.mypullup.obj.Attempt;

public class AttemptLoadTask extends AsyncTask<Void, Void, Attempt> {
    private DBHelper dbHelper;

    public AttemptLoadTask(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    protected Attempt doInBackground(Void... voids) {
        return dbHelper.getAttempt();
    }
}