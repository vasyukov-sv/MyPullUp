package home.my.mypullup.task;

import android.os.AsyncTask;
import home.my.mypullup.helper.DBHelper;
import home.my.mypullup.obj.Analitic;

public class AttemptLoadAnaliticTask extends AsyncTask<Void, Void, Analitic> {

    private final AsyncResponseAnalitic delegate;

    public AttemptLoadAnaliticTask(AsyncResponseAnalitic delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPostExecute(Analitic result) {
        super.onPostExecute(result);
        delegate.onLoadAnalitic(result);
    }

    @Override
    protected Analitic doInBackground(Void... voids) {
        return DBHelper.getInstance(delegate.getContext()).getAnalitic();
    }
}