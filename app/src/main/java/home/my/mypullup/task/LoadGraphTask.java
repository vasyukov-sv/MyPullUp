package home.my.mypullup.task;

import home.my.mypullup.helper.DBHelper;
import home.my.mypullup.obj.Attempt;

import java.util.List;

public class LoadGraphTask extends CommonTask<Void, Void, List<Attempt>> {

    private final AsyncResponseGraph delegate;

    public LoadGraphTask(AsyncResponseGraph delegate) {
        super(delegate.getContext());
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