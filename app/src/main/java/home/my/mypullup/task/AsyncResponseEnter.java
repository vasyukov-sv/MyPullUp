package home.my.mypullup.task;

import home.my.mypullup.obj.Attempt;

public interface AsyncResponseEnter extends AsyncResponse {
    void onLoadAttempt(Attempt attempt);

    void onSaveAttempt();
}
