package home.my.mypullup.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import home.my.mypullup.R;
import home.my.mypullup.obj.Attempt;
import home.my.mypullup.task.AsyncResponseGraph;
import home.my.mypullup.task.LoadGraphTask;

import java.util.List;

public class GraphTab extends CommonTab implements AsyncResponseGraph {
    private LoadGraphTask loadGraphTask = null;
    private GraphView view;

    public GraphTab() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = (GraphView) view;

        loadGraph();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.graph, container, false);
    }

    private DataPoint[] generateData(List<Attempt> attempts) {
        DataPoint[] values = new DataPoint[attempts.size()];
        for (int i = 0; i < attempts.size(); i++) {
            DataPoint v = new DataPoint(i, attempts.get(i).getSumAttempt());
            values[i] = v;
        }
        return values;
    }

    private void loadGraph() {
        if (loadGraphTask != null) {
            return;
        }


        loadGraphTask = new LoadGraphTask(this);
        loadGraphTask.execute((Void) null);

    }

    @Override
    public void onLoadGraph(List<Attempt> attempts) {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(generateData(attempts));

        view.getViewport().setYAxisBoundsManual(true);
        view.getViewport().setMinY(0);
        view.getViewport().setMaxY(series.getHighestValueY());

        view.getViewport().setXAxisBoundsManual(true);
        view.getViewport().setMinX(0);
        view.getViewport().setMaxX(series.getHighestValueX());

        // enable scaling and scrolling
        view.getViewport().setScalable(true);
        view.addSeries(series);
        loadGraphTask = null;


    }
}
