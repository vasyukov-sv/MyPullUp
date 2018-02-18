package home.my.mypullup.task;

import android.os.AsyncTask;
import home.my.mypullup.helper.DBHelper;
import home.my.mypullup.obj.Attempt;

import java.util.List;

public class LoadGraphTask extends AsyncTask<Void, Void, List<Attempt>> {

    private final AsyncResponseGraph delegate;

    public LoadGraphTask(AsyncResponseGraph delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPostExecute(List<Attempt> attempts) {
        super.onPostExecute(attempts);
        delegate.onLoadGraph(attempts);
    }

    @Override
    protected List<Attempt> doInBackground(Void... voids) {
        return DBHelper.getInstance(delegate.getContext()).getGraph();
    }
}