package home.my.mypullup.task;

import home.my.mypullup.helper.DBHelper;
import home.my.mypullup.obj.Attempt;

public class AttemptLoadTask extends CommonTask<Void, Void, Attempt> {

    private final AsyncResponseEnter delegate;

    public AttemptLoadTask(AsyncResponseEnter delegate) {
        super(delegate.getContext());
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