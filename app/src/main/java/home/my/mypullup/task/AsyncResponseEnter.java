package home.my.mypullup.task;

import home.my.mypullup.obj.Attempt;

public interface AsyncResponseEnter {
    void onLoadAttempt(Attempt attempt);

    void onSaveAttempt();
}
