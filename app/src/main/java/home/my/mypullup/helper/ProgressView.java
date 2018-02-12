package home.my.mypullup.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import home.my.mypullup.R;
import home.my.mypullup.TabActivity;
import home.my.mypullup.task.AsyncResponse;

public class ProgressView {
    private static ProgressView sInstance;
    private final View mProgressView;
    private AsyncResponse task;

    private ProgressView(View mProgressView) {
        this.mProgressView = mProgressView;

    }

    public static synchronized ProgressView getInstance(AsyncResponse task) {
        if (sInstance == null) {
            sInstance = new ProgressView(((TabActivity) task.getContext()).findViewById(R.id.save_progress));
        }
        sInstance.setTask(task);
        return sInstance;
    }

    private void setTask(AsyncResponse task) {
        this.task = task;
    }

    public void showProgress(final boolean show) {
        int shortAnimTime = task.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}
