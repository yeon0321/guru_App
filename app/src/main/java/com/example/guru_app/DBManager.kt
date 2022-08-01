package com.example.guru_app

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBManager (
    context: Context?,
    name:String?,
    factory: SQLiteDatabase.CursorFactory?,
    version:Int
): SQLiteOpenHelper(context,name,factory,version){
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE listTBL(name text,content text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }

}