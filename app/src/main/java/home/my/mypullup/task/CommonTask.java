package home.my.mypullup.task;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import home.my.mypullup.R;
import home.my.mypullup.TabActivity;

public abstract class CommonTask<T, U, V> extends AsyncTask<T, U, V> {
    private final View mProgressView;

    CommonTask(Context context) {
        this.mProgressView = ((TabActivity) context).findViewById(R.id.save_progress);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(V v) {
        super.onPostExecute(v);
        mProgressView.setVisibility(View.GONE);
    }
}