package com.hk210.mapscheckin.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hk210.mapscheckin.Model.Check;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context,"checkin.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table checkinmade( timestamp TEXT primary key,longitude TEXT , latitude TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists checkinmade");

    }
    public Boolean insertData(String timestamp, String longitude, String latitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("timestamp", timestamp);
        contentValues.put("longitude", longitude);
        contentValues.put("latitude", latitude);
        long result = db.insert("checkinmade", null, contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    public ArrayList<Check> getAllData(){
        ArrayList<Check> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM checkinmade", null);
        if(cursor.moveToNext()){
            do {
                Check check = new Check(""+cursor.getString(cursor.getColumnIndex("timestamp")),
                        ""+cursor.getString(cursor.getColumnIndex("longitude")),
                        ""+cursor.getString(cursor.getColumnIndex("latitude")));
                arrayList.add(check);

            } while(cursor.moveToNext());

        }
        db.close();
        return arrayList;
    }





}
