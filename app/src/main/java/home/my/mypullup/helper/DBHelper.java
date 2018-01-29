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
        db.execSQL("CREATE TABLE " + TABLE + "(id integer primary key autoincrement,morning1 integer,morning2 integer,evening1 integer,evening2 integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void delete(int id) {
        db.delete(TABLE, "id=" + id, null);
    }

    public void insertMorning(Attempt attempt) {
        ContentValues cv = new ContentValues();
        cv.put("morning1", attempt.getAttempt1());
        cv.put("morning2", attempt.getAttempt2());
        db.insert(TABLE, null, cv);
    }


    public void insertEvening(Attempt attempt) {
        ContentValues cv = new ContentValues();
        cv.put("evening1", attempt.getAttempt1());
        cv.put("evening2", attempt.getAttempt2());
        db.insert(TABLE, null, cv);
    }

    public void setDB(SQLiteDatabase db) {
        this.db = db;
    }

    public Attempt getAttempt(boolean isMorning) {
        Cursor cursor = db.query(TABLE, null, "date = date('now', 'localtime')", null, null, null, null);
        if (cursor == null) {
            return null;
        }
        cursor.moveToFirst();

        int attempt1Index = isMorning ? cursor.getColumnIndex("morning1") : cursor.getColumnIndex("evening1");
        int attempt2Index = isMorning ? cursor.getColumnIndex("morning2") : cursor.getColumnIndex("evening2");
        Attempt attempt = new Attempt(cursor.getInt(attempt1Index), cursor.getInt(attempt2Index));
        cursor.close();
        return attempt;
    }

}