package home.my.mypullup.helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import home.my.mypullup.obj.Attempt;

import static home.my.mypullup.MainActivity.DATABASE_VERSION;
import static home.my.mypullup.MainActivity.TABLE;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, Utils.getApplicationName(context), null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + "(id integer primary key autoincrement, date text,morning1 integer,morning2 integer,evening1 integer,evening2 integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void delete(String date) {
        db.delete(TABLE, "date=" + date, null);
    }

    public void addAttempt(Attempt attempt) {
        ContentValues cv = new ContentValues();
        cv.put("morning1", attempt.getMorning1());
        cv.put("morning2", attempt.getMorning2());
        cv.put("evening1", attempt.getEvening1());
        cv.put("evening2", attempt.getEvening2());
        cv.put("date", attempt.getDate());
        db.insert(TABLE, null, cv);
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

}