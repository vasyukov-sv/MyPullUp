package home.my.mypullup.helper;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.InputFilter;
import android.widget.EditText;

import java.util.Arrays;

public class Utils {

    public static void setMaxValue(int value, EditText[] editTexts) {
        InputFilter[] inputFilter = new InputFilter[]{new InputFilterMinMax(value)};
        Arrays.stream(editTexts).forEach(editText -> editText.setFilters(inputFilter));
    }

    static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }

    public static String doubleToString(Double value) {
        return value != null ? Double.toString(value) : "";
    }

    public static String integerToString(Integer value) {
        return value != null ? Integer.toString(value) : "";
    }
}
