package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "UserContactsData", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserDetails(name TEXT primary key,number TEXT ,photo BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {//i->oldversion,ii->newversion
        DB.execSQL("drop Table if exists UserDetails");
        onCreate(DB);
    }

    public Boolean insertuserdata(String name, String number) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("number", number);
     //   contentValues.put("photo", photoByteArray);
        long result = DB.insert("Userdetails", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    public void updateData(String originalname,String name1,String number1) {
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name1);
        contentValues.put("number", number1);
        DB.update("Userdetails", contentValues, "name=?", new String[]{originalname});
        DB.close();
    }

    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails", null);
        return cursor;
    }


    public void deleteItem(String contactname){
        SQLiteDatabase DB=this.getWritableDatabase();
        DB.delete("Userdetails", "name=?", new String[]{contactname});
        DB.close();
    }
    public byte[] getPhotoById(String name){
        SQLiteDatabase DB= this.getReadableDatabase();
        Cursor cursor=DB.query("Userdetails", new String[] {"photo"}, "name=?", new String[]{name}, null, null, null);
        if(cursor.moveToFirst()){
            return cursor.getBlob(3);
        }
        cursor.close();
        return null;
    }

}
