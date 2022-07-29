package com.example.guru_app

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class eqList : AppCompatActivity() {

    lateinit var myHelper: myDBHelper
    lateinit var sqlDB:SQLiteDatabase
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eq_list)
        listView=findViewById(R.id.listView)


        myHelper=myDBHelper(this)

        sqlDB=myHelper.readableDatabase

        var cursor: Cursor
        cursor=sqlDB.rawQuery("SELECT *FROM listTBL",null)

        var strNames=ArrayList<String>()

        while (cursor.moveToNext()){
            strNames.add(cursor.getString(0))
        }
        val list_Adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,strNames)
        listView.adapter=list_Adapter
        cursor.close()



    }

    inner class myDBHelper(context: Context): SQLiteOpenHelper(context,"listDB",null,1){
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE listTBL(gName CHAR(20) PRIMARY KEY);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS listTBL")
            onCreate(db)

        }
    }
}

