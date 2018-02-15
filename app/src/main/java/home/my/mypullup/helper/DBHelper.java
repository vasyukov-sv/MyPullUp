package home.my.mypullup.helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;
import home.my.mypullup.obj.Analitic;
import home.my.mypullup.obj.Attempt;

import java.util.ArrayList;
import java.util.List;

import static home.my.mypullup.TabActivity.*;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper sInstance;

    private SQLiteDatabase db;

    private DBHelper(Context context) {
        super(context, Utils.getApplicationName(context), null, DATABASE_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        sInstance.setDB(sInstance.getWritableDatabase());
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + "(id integer primary key autoincrement, date text ,morning1 integer,morning2 integer,evening1 integer,evening2 integer, CONSTRAINT date_unique UNIQUE (date));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void addAttempt(Attempt attempt) {
        ContentValues cv = new ContentValues();
        if (attempt.getMorning1() != null) {
            cv.put("morning1", attempt.getMorning1());
        }
        if (attempt.getMorning2() != null) {
            cv.put("morning2", attempt.getMorning2());
        }
        if (attempt.getEvening1() != null) {
            cv.put("evening1", attempt.getEvening1());
        }
        if (attempt.getEvening2() != null) {
            cv.put("evening2", attempt.getEvening2());
        }
        cv.put("date", attempt.getDate());
        if (db.insertWithOnConflict(TABLE, null, cv, SQLiteDatabase.CONFLICT_IGNORE) == -1) {
            db.update(TABLE, cv, "date =?", new String[]{attempt.getDate()});
        }
    }

    private void setDB(SQLiteDatabase db) {
        this.db = db;
    }

    public Attempt getAttempt() {
        Cursor cursor = db.query(TABLE, null, "date = date('now', 'localtime')", null, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }


        Attempt attempt = new Attempt(cursor.getInt(cursor.getColumnIndex("morning1")), cursor.getInt(cursor.getColumnIndex("morning2")), cursor.getInt(cursor.getColumnIndex("evening1")), cursor.getInt(cursor.getColumnIndex("evening2")));
        cursor.close();
        return attempt;
    }

    private List<Attempt> getLastAttempts() {
        List<Attempt> attempts = new ArrayList<>();
        Cursor cursor = db.query(TABLE, null, "date > date('now', '-" + DAYS_AGO + " day','localtime')", null, null, null, "date");
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        while (!cursor.isAfterLast()) {
            attempts.add(new Attempt(cursor.getInt(cursor.getColumnIndex("morning1")), cursor.getInt(cursor.getColumnIndex("morning2")), cursor.getInt(cursor.getColumnIndex("evening1")), cursor.getInt(cursor.getColumnIndex("evening2")), cursor.getString(cursor.getColumnIndex("date"))));
            cursor.moveToNext();
        }
        cursor.close();
        return attempts;
    }

    public Analitic getAnalitic() {
        Pair<Double, Integer> pairWeek = getAvgAndMax(Period.WEEK);
        Pair<Double, Integer> pairMonth = getAvgAndMax(Period.MONTH);
        Pair<Double, Integer> pairAll = getAvgAndMax(Period.ALL);
        return new Analitic().setAttemptList(getLastAttempts()).setAvgWeek((pairWeek) != null ? pairWeek.first : null).setMaxWeek(pairWeek != null ? pairWeek.second : null).setAvgMonth(pairMonth != null ? pairMonth.first : null).setMaxMonth(pairMonth.second).setAvgAll(pairAll.first).setMaxAll(pairAll.second);
    }

    private Pair<Double, Integer> getAvgAndMax(Period period) {
        String selection;
        switch (period) {
            case WEEK:
                selection = "date >= date('now','-7 days','weekday 1')";
                break;
            case MONTH:
                selection = " date >= date('now','start of month')";
                break;
            case ALL:
            default:
                selection = "";
        }
        String[] columns = new String[]{"avg(morning1+morning2+evening1+evening2) as avgperiod", "max(morning1+morning2+evening1+evening2) as maxperiod"};

        Cursor cursor = db.query(TABLE, columns, selection, null, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        Pair<Double, Integer> pair = new Pair<>(cursor.getDouble(cursor.getColumnIndex("avgperiod")), cursor.getInt(cursor.getColumnIndex("maxperiod")));
        cursor.close();
        return pair;
    }

}