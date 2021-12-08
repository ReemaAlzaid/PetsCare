package com.example.petscarenew;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBPets extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "PetsList.db";
    public static final String TABLE_NAME ="Pets";
    public static final String COL_1 = "Pet_ID";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Age";
    public static final String COL_4 = "Description";

    public DBPets(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db =this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT" +
                ",Age TEXT,Description TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertdata (String name,String age, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,age);
        contentValues.put(COL_4,description);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }
        else
            return true;
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

}
