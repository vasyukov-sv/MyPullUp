package home.my.mypullup.helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import home.my.mypullup.obj.Attempt;

import java.util.ArrayList;
import java.util.List;

import static home.my.mypullup.fragment.EnterResult.DATABASE_VERSION;
import static home.my.mypullup.fragment.EnterResult.DAYS_AGO;
import static home.my.mypullup.fragment.EnterResult.TABLE;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, Utils.getApplicationName(context), null, DATABASE_VERSION);
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

    public void setDB(SQLiteDatabase db) {
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

    public List<Attempt> getLastAttempts() {

        List<Attempt> attempts = new ArrayList<>();
        Cursor cursor = db.query(TABLE, null, "date > date('now', '-" + DAYS_AGO + " day','localtime')", null, null, null, "date");
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }

        while (!cursor.isAfterLast()) {
            attempts.add(new Attempt(cursor.getInt(cursor.getColumnIndex("morning1")), cursor.getInt(cursor.getColumnIndex("morning2")), cursor.getInt(cursor.getColumnIndex("evening1")), cursor.getInt(cursor.getColumnIndex("evening2"))));
            cursor.moveToNext();
        }
        cursor.close();

        return attempts;
    }

}