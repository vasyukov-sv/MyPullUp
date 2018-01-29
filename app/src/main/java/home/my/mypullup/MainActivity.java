package home.my.mypullup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import home.my.mypullup.helper.DBHelper;
import home.my.mypullup.helper.Utils;
import home.my.mypullup.obj.Attempt;
import home.my.mypullup.task.AttemptLoadTask;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "tScore";
    private static final int MAX_VALUE_ATTEMPT = 12;

    private AttemptSaveTask mAuthTask = null;
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

        findViewById(R.id.save_morning_button).setOnClickListener(view -> saveRow(mMorning1, mMorning2));
        findViewById(R.id.save_evening_button).setOnClickListener(view -> saveRow(mEvening1, mEvening2));




        mProgressView = findViewById(R.id.save_progress);

        mMorning2.setOnEditorActionListener(this::onEditorAction);
        mEvening2.setOnEditorActionListener(this::onEditorAction);

        dbHelper = new DBHelper(this);
        dbHelper.setDB(dbHelper.getWritableDatabase());

        loadAttempt();
    }

    private void loadAttempt() {
        if (attemptLoadTask != null) {
            return;
        }
        attemptLoadTask = new AttemptLoadTask(dbHelper);
        Attempt attempt = null;
        try {
            attempt = attemptLoadTask.execute((Void) null).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (attempt !=null) {
            mMorning1.setText(attempt.getMorning1());
            mMorning2.setText(attempt.getMorning2());
            mEvening1.setText(attempt.getEvening1());
            mEvening2.setText(attempt.getEvening2());
        }
    }

    private void saveRow(EditText attempt1, EditText attempt2) {
        Toast.makeText(getApplicationContext(), "saveRow", Toast.LENGTH_SHORT).show();

        String value1 = attempt1.getText().toString();
        String value2 = attempt2.getText().toString();
        if (mAuthTask != null) {
            return;
        }
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
            mAuthTask = new AttemptSaveTask(value1, value2);
            mAuthTask.execute((Void) null);
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

    private boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId != EditorInfo.IME_ACTION_DONE) {
            return false;
        }
        switch (v.getId()) {
            case R.id.morning2:
                saveRow(mMorning1, mMorning2);
                break;
            case R.id.evening2:
                saveRow(mEvening1, mEvening2);
                break;
            default:
                return false;
        }
        return true;
    }


    public class AttemptSaveTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        AttemptSaveTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(mEmail)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(mPassword);
//                }
//            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

