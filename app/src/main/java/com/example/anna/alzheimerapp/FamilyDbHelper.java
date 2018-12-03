package com.example.anna.alzheimerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FamilyDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Family.db";
    public static final int DATABASE_VERSION = 1;
    public static final String CREATE_TABLE =
            "CREATE TABLE if not exists " + FamilyContract.FamilyEntry.TABLE_NAME +
                    "("
                    + FamilyContract.FamilyEntry.ID + " INTEGER PRIMARY KEY,"
//                    + FamilyContract.FamilyEntry.IMAGE + " TEXT,"
                    + FamilyContract.FamilyEntry.NAME + " TEXT,"
                    + FamilyContract.FamilyEntry.RELATIONSHIP + " TEXT);";



    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + FamilyContract.FamilyEntry.TABLE_NAME + ";";

    public FamilyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.d("DbMsg", "Table created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public long addMember(String name, String relationship, SQLiteDatabase sqLiteDatabase) {
        if (sqLiteDatabase.isOpen()) {
            Log.d("BASE", "OPEN!");
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(FamilyContract.FamilyEntry.NAME, name);
        contentValues.put(FamilyContract.FamilyEntry.RELATIONSHIP, relationship);
        return sqLiteDatabase.insert(FamilyContract.FamilyEntry.TABLE_NAME, null, contentValues);
    }


    public Cursor readMembers(SQLiteDatabase sqLiteDatabase, String name) {
        return sqLiteDatabase.query(
                FamilyContract.FamilyEntry.TABLE_NAME,
                null,
                 name != null ? "name='" + name + "'" : null,// jesli name nie jest nullem to szuka imienia a jak nie to daje nul  i szuka wszystkich
                null,
                null,
                null,
                null);
    }

}
