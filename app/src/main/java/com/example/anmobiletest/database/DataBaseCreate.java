package com.example.anmobiletest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import static android.content.Context.MODE_PRIVATE;

public class DataBaseCreate {

    public static void createDB (Context context)
    {
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT, password TEXT, url TEXT, status INTEGET)");
        db.execSQL("INSERT OR REPLACE INTO users (id, login, password, url, status) VALUES (1, 'root', 'root', 'http://try.axxonsoft.com:8000/asip-api/', 2)");
    }

}
