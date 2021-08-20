package com.example.anmobiletest.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anmobiletest.user.User;



public class UserData {


    public static User getUser() {
        User user = new User();
        SQLiteDatabase.CursorFactory cursorFactory = null;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase ("/data/data/com.example.anmobiletest/databases/app.db", cursorFactory, null); //Костыль для доступа к базе без контекста
        Cursor query = db.rawQuery("SELECT * FROM users ORDER BY id DESC LIMIT 1;", null);

        if (query.moveToFirst()) {

            user.setLogin(query.getString(1));
            user.setPassword(query.getString(2));
            user.setUrl(query.getString(3));
            user.setStatus(query.getInt(4));

        }

        query.close();
        return user;
    }


    public static void addUser(Context context, String login, String password, String url) {
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", Context.MODE_PRIVATE, null);
        db.execSQL("INSERT OR REPLACE INTO users (login, password, url, status) VALUES ('"+login+"', '"+password+"', '"+url+"', 0)");
    }
}
