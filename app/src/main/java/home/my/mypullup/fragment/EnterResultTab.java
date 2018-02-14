package home.my.mypullup.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import home.my.mypullup.R;
import home.my.mypullup.helper.ProgressView;
import home.my.mypullup.helper.Utils;
import home.my.mypullup.obj.Attempt;
import home.my.mypullup.task.AsyncResponseEnter;
import home.my.mypullup.task.AttemptLoadTask;
import home.my.mypullup.task.AttemptSaveTask;

import static home.my.mypullup.TabActivity.MAX_VALUE_ATTEMPT;
import static java.util.Optional.ofNullable;

public class EnterResultTab extends CommonTab implements AsyncResponseEnter {

    private ProgressView progressView;
    private AttemptSaveTask attemptSaveTask = null;
    private AttemptLoadTask attemptLoadTask = null;
    private EditText mMorning1;
    private EditText mMorning2;
    private EditText mEvening1;
    private EditText mEvening2;

    public EnterResultTab() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMorning1 = (EditText) view.findViewById(R.id.morning1);
        mMorning2 = (EditText) view.findViewById(R.id.morning2);
        mEvening1 = (EditText) view.findViewById(R.id.evening1);
        mEvening2 = (EditText) view.findViewById(R.id.evening2);
        Utils.setMaxValue(MAX_VALUE_ATTEMPT, new EditText[]{mMorning1, mMorning2, mEvening1, mEvening2});

        view.findViewById(R.id.save_morning_button).setOnClickListener(v -> saveRow(v, true));
        view.findViewById(R.id.save_evening_button).setOnClickListener(v -> saveRow(v, false));

        mMorning2.setOnEditorActionListener((v, actionId, event) -> onEditorAction(v, actionId));
        mEvening2.setOnEditorActionListener((v, actionId, event) -> onEditorAction(v, actionId));
        progressView = ProgressView.getInstance(this);
        loadAttempt();
    }

    private void loadAttempt() {
        if (attemptLoadTask != null) {
            return;
        }
        progressView.showProgress(true);
        attemptLoadTask = new AttemptLoadTask(this);
        attemptLoadTask.execute((Void) null);
    }


    private boolean onEditorAction(TextView v, int actionId) {
        if (actionId != EditorInfo.IME_ACTION_DONE) {
            return false;
        }
        int i = v.getId();
        switch (i) {
            case R.id.morning2:
                saveRow(v, true);
                break;
            case R.id.evening2:
                saveRow(v, false);
                break;
            default:
                return false;
        }
        return true;
    }

    private void saveRow(View v, boolean isMorning) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

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
            progressView.showProgress(true);
            attemptSaveTask = new AttemptSaveTask(this);
            Attempt attempt = isMorning ? new Attempt(Integer.parseInt(value1), Integer.parseInt(value2), null, null) : new Attempt(null, null, Integer.parseInt(value1), Integer.parseInt(value2));
            attemptSaveTask.execute(attempt);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.enter_result, container, false);
    }

    @Override
    public void onLoadAttempt(Attempt attempt) {
        if (attempt != null) {
            mMorning1.setText(String.format("%s", ofNullable(attempt.getMorning1().toString()).orElse("")));
            mMorning2.setText(String.format("%s", ofNullable(attempt.getMorning2().toString()).orElse("")));
            mEvening1.setText(String.format("%s", ofNullable(attempt.getEvening1().toString()).orElse("")));
            mEvening2.setText(String.format("%s", ofNullable(attempt.getEvening2().toString()).orElse("")));
        }
        attemptLoadTask = null;
        progressView.showProgress(false);
    }

    @Override
    public void onSaveAttempt() {
        attemptSaveTask = null;
        progressView.showProgress(false);
    }
}
