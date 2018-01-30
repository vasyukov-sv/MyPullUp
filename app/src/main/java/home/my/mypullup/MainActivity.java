package home.my.mypullup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import home.my.mypullup.helper.DBHelper;
import home.my.mypullup.helper.Utils;
import home.my.mypullup.obj.Attempt;
import home.my.mypullup.task.AsyncResponse;
import home.my.mypullup.task.AttemptLoadTask;
import home.my.mypullup.task.AttemptSaveTask;

public class MainActivity extends AppCompatActivity implements AsyncResponse {
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE = "tScore";
    private static final int MAX_VALUE_ATTEMPT = 12;

    private AttemptSaveTask attemptSaveTask = null;
    private AttemptLoadTask attemptLoadTask = null;

    private EditText mMorning1;
    private EditText mMorning2;
    private EditText mEvening1;
    private EditText mEvening2;

    private View mProgressView;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMorning1 = (EditText) findViewById(R.id.morning1);
        mMorning2 = (EditText) findViewById(R.id.morning2);
        mEvening1 = (EditText) findViewById(R.id.evening1);
        mEvening2 = (EditText) findViewById(R.id.evening2);

        Utils.setMaxValue(MAX_VALUE_ATTEMPT, new EditText[]{mMorning1, mMorning2, mEvening1, mEvening2});

        findViewById(R.id.save_morning_button).setOnClickListener(view -> saveRow(true));
        findViewById(R.id.save_evening_button).setOnClickListener(view -> saveRow(false));


        mProgressView = findViewById(R.id.save_progress);

        mMorning2.setOnEditorActionListener((v, actionId, event) -> onEditorAction(v, actionId));
        mEvening2.setOnEditorActionListener((v, actionId, event) -> onEditorAction(v, actionId));

        dbHelper = new DBHelper(this);
        dbHelper.setDB(dbHelper.getWritableDatabase());
        dbHelper.addAttempt(new Attempt(2, 3, 4, 5));
        loadAttempt();
    }

    private void loadAttempt() {
        if (attemptLoadTask != null) {
            return;
        }
        showProgress(true);
        attemptLoadTask = new AttemptLoadTask(dbHelper, this);
        attemptLoadTask.execute((Void) null);
    }

    private void saveRow(boolean isMorning) {
        if (attemptSaveTask != null) {
            return;
        }

        EditText attempt1 = isMorning ? mMorning1 : mEvening1;
        EditText attempt2 = isMorning ? mMorning2 : mEvening2;

        String value1 = attempt1.getText().toString();
        String value2 = attempt2.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(value1)) {
            attempt1.setError(getString(R.string.error_field_required));
            focusView = attempt1;
            cancel = true;
        }

        if (TextUtils.isEmpty(value2)) {
            attempt2.setError(getString(R.string.error_field_required));
            focusView = attempt2;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            attemptSaveTask = new AttemptSaveTask(dbHelper, this);
            Attempt attempt = isMorning ? new Attempt(Integer.parseInt(value1), Integer.parseInt(value2), null, null)
                    : new Attempt(null, null, Integer.parseInt(value1), Integer.parseInt(value2));
            attemptSaveTask.execute(attempt);
        }
    }


    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    private boolean onEditorAction(TextView v, int actionId) {
        if (actionId != EditorInfo.IME_ACTION_DONE) {
            return false;
        }
        int i = v.getId();
        switch (i) {
            case R.id.morning2:
                saveRow(true);
                break;
            case R.id.evening2:
                saveRow(false);
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void onLoadAttempt(Attempt attempt) {
        mMorning1.setText(attempt.getMorning1());
        mMorning2.setText(attempt.getMorning2());
        mEvening1.setText(attempt.getEvening1());
        mEvening2.setText(attempt.getEvening2());
        attemptLoadTask = null;
        showProgress(false);
    }

    @Override
    public void onSaveAttempt() {
        attemptSaveTask = null;
        showProgress(false);
    }
}