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
import home.my.mypullup.helper.Utils;
import home.my.mypullup.obj.Attempt;
import home.my.mypullup.task.AsyncResponseEnter;
import home.my.mypullup.task.AttemptLoadTask;
import home.my.mypullup.task.SaveTask;

import java.util.Locale;

import static home.my.mypullup.MainActivity.MAX_VALUE_ATTEMPT;

public class EnterTab extends CommonTab implements AsyncResponseEnter {
    private SaveTask attemptSaveTask = null;
    private AttemptLoadTask attemptLoadTask = null;
    private EditText mMorning1;
    private EditText mMorning2;
    private EditText mEvening1;
    private EditText mEvening2;

    private OnFragmentInteractionListener mListener;

    public EnterTab() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener) context;
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

        loadAttempt();
    }

    private void loadAttempt() {
        if (attemptLoadTask == null) {
            attemptLoadTask = (AttemptLoadTask) new AttemptLoadTask(this).execute((Void) null);
        }
    }

    private boolean onEditorAction(TextView v, int actionId) {
        if (actionId != EditorInfo.IME_ACTION_DONE) {
            return false;
        }
        switch (v.getId()) {
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
        if (attemptSaveTask != null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        EditText attempt1 = isMorning ? mMorning1 : mEvening1;
        EditText attempt2 = isMorning ? mMorning2 : mEvening2;

        String value1 = attempt1.getText().toString();
        String value2 = attempt2.getText().toString();

        View focusView = null;

        if (TextUtils.isEmpty(value1)) {
            attempt1.setError(getString(R.string.error_field_required));
            focusView = attempt1;
        }

        if (TextUtils.isEmpty(value2)) {
            attempt2.setError(getString(R.string.error_field_required));
            focusView = attempt2;
        }

        if (focusView != null) {
            focusView.requestFocus();
            return;
        }

        Attempt attempt = isMorning ? new Attempt(Integer.parseInt(value1), Integer.parseInt(value2), null, null) : new Attempt(null, null, Integer.parseInt(value1), Integer.parseInt(value2));
        attemptSaveTask = (SaveTask) new SaveTask(this).execute(attempt);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.enter_result, container, false);
    }

    @Override
    public void onLoadAttempt(Attempt attempt) {
        if (attempt != null) {
            Locale locale = Locale.getDefault();
            String format = "%d";
            mMorning1.setText(String.format(locale, format, attempt.getMorning1()));
            mMorning2.setText(String.format(locale, format, attempt.getMorning2()));
            mEvening1.setText(String.format(locale, format, attempt.getEvening1()));
            mEvening2.setText(String.format(locale, format, attempt.getEvening2()));
        }
        attemptLoadTask = null;
    }

    @Override
    public void onSaveAttempt() {
        attemptSaveTask = null;
        mListener.onLoadAnalitic();
    }
}
