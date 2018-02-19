package home.my.mypullup.task;

import home.my.mypullup.helper.DBHelper;
import home.my.mypullup.obj.Analitic;

public class AttemptLoadAnaliticTask extends CommonTask<Void, Void, Analitic> {

    private final AsyncResponseAnalitic delegate;

    public AttemptLoadAnaliticTask(AsyncResponseAnalitic delegate) {
        super(delegate.getContext());
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