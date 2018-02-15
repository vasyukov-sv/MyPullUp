package home.my.mypullup.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import home.my.mypullup.R;
import home.my.mypullup.TabActivity;
import home.my.mypullup.task.AsyncResponse;

public class ProgressView {
    private final View mProgressView;

    public ProgressView(AsyncResponse task) {
        this.mProgressView = ((TabActivity) task.getContext()).findViewById(R.id.save_progress);
    }

    public void showProgress(final boolean show) {
        int shortAnimTime = mProgressView.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}