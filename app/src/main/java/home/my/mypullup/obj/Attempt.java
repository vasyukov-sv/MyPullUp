package home.my.mypullup.obj;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Attempt {
    private int morning1;
    private int morning2;
    private int evening1;
    private int evening2;

    private String date;

    public Attempt(int morning1, int morning2, int evening1, int evening2) {
        this.morning1 = morning1;
        this.morning2 = morning2;
        this.evening1 = evening1;
        this.evening2 = evening2;
        this.date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    public int getMorning1() {
        return morning1;
    }

    public void setMorning1(int morning1) {
        this.morning1 = morning1;
    }

    public int getMorning2() {
        return morning2;
    }

    public void setMorning2(int morning2) {
        this.morning2 = morning2;
    }

    public int getEvening1() {
        return evening1;
    }

    public void setEvening1(int evening1) {
        this.evening1 = evening1;
    }

    public int getEvening2() {
        return evening2;
    }

    public void setEvening2(int evening2) {
        this.evening2 = evening2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}