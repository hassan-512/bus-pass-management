package com.example.buspasssystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "buspass.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT UNIQUE, password TEXT, balance INTEGER)");
        db.execSQL("CREATE TABLE routes(id INTEGER PRIMARY KEY AUTOINCREMENT, from_city TEXT, to_city TEXT, fare INTEGER, seats INTEGER)");
        db.execSQL("CREATE TABLE passes(id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, route TEXT, fare INTEGER)");

        // Sample route
        db.execSQL("INSERT INTO routes(from_city,to_city,fare,seats) VALUES('City A','City B',200,15)");
        db.execSQL("INSERT INTO routes(from_city,to_city,fare,seats) VALUES('City B','City C',150,10)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS routes");
        db.execSQL("DROP TABLE IF EXISTS passes");
        onCreate(db);
    }

    // ---------------- Users ----------------

    public boolean insertUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email", email);
        cv.put("password", password);
        cv.put("balance", 0);
        return db.insert("users", null, cv) != -1;
    }

    public Cursor getUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM users WHERE email=? AND password=?", new String[]{email, password});
    }

    public Cursor getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM users WHERE id=?", new String[]{String.valueOf(userId)});
    }

    public boolean updateBalance(int userId, int newBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("balance", newBalance);
        return db.update("users", cv, "id=?", new String[]{String.valueOf(userId)}) > 0;
    }

    // ---------------- Routes ----------------

    public Cursor getRoutes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM routes", null);
    }

    public boolean updateSeats(int routeId, int newSeats) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("seats", newSeats);
        return db.update("routes", cv, "id=?", new String[]{String.valueOf(routeId)}) > 0;
    }

    // ---------------- Passes ----------------

    public boolean insertPass(int userId, String route, int fare) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", userId);
        cv.put("route", route);
        cv.put("fare", fare);
        return db.insert("passes", null, cv) != -1;
    }

    public Cursor getUserPasses(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM passes WHERE user_id=?", new String[]{String.valueOf(userId)});
    }
}
