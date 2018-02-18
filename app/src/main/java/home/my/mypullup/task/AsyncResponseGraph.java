package home.my.mypullup.task;

import home.my.mypullup.obj.Attempt;

import java.util.List;

public interface AsyncResponseGraph extends AsyncResponse {
    void onLoadGraph(List<Attempt> attempts);
}
