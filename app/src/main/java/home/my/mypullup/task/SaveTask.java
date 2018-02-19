package home.my.mypullup.task;

import home.my.mypullup.helper.DBHelper;
import home.my.mypullup.obj.Attempt;

public class SaveTask extends CommonTask<Attempt, Void, Void> {
    private final AsyncResponseEnter delegate;

    public SaveTask(AsyncResponseEnter delegate) {
        super(delegate.getContext());
        this.delegate = delegate;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        delegate.onSaveAttempt();
    }

    @Override
    protected Void doInBackground(Attempt... attempts) {
        DBHelper.getInstance(delegate.getContext()).addAttempt(attempts[0]);
        return null;
    }
}