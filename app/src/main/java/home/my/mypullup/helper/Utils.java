package home.my.mypullup.helper;

import android.text.InputFilter;
import android.widget.EditText;

import java.util.Arrays;

public class Utils {

    public static void setMaxValue(int value, EditText[] editTexts) {
        InputFilter[] inputFilter = new InputFilter[]{new InputFilterMinMax(0, value)};
        Arrays.stream(editTexts).forEach(editText -> editText.setFilters(inputFilter));
    }
}
