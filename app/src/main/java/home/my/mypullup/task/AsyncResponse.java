package home.my.mypullup.task;

import home.my.mypullup.obj.Attempt;

public interface AsyncResponse {
    void onLoadAttempt(Attempt attempt);

    void onSaveAttempt();

    void onLoadAnalitics(String result);
}
