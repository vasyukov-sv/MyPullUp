package home.my.mypullup.obj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Attempt {
    private static final SimpleDateFormat baseSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final SimpleDateFormat humanSimpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private final Integer morning1;
    private final Integer morning2;
    private final Integer evening1;
    private final Integer evening2;
    private final String date;

    public Attempt(Integer morning1, Integer morning2, Integer evening1, Integer evening2) {
        this.morning1 = morning1;
        this.morning2 = morning2;
        this.evening1 = evening1;
        this.evening2 = evening2;
        this.date = baseSimpleDateFormat.format(new Date());
    }

    public Attempt(Integer morning1, Integer morning2, Integer evening1, Integer evening2, String date) {
        this.morning1 = morning1;
        this.morning2 = morning2;
        this.evening1 = evening1;
        this.evening2 = evening2;
        this.date = date;
    }

    public Integer getMorning1() {
        return morning1;
    }

    public Integer getMorning2() {
        return morning2;
    }

    public Integer getEvening1() {
        return evening1;
    }

    public Integer getEvening2() {
        return evening2;
    }

    public String getDate() {
        return date;
    }

    public String getHumanDate() {
        try {
            return humanSimpleDateFormat.format(baseSimpleDateFormat.parse(getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

}