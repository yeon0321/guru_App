package com.example.guru_app

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class contentActivity : AppCompatActivity() {
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var tvEquip: TextView

    lateinit var str_name:String
    lateinit var str_content:String



    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)



        tvEquip=findViewById(R.id.eqcontent)

        val intent=intent
        str_name=intent.getStringExtra("intent_name").toString()

        dbManager= DBManager(this,"eqListDB",null,1)
        sqlitedb=dbManager.readableDatabase

        var cursor: Cursor
        cursor=sqlitedb.rawQuery("SELECT * FROM listTBL WHERE name='"+str_name+"';",null)

        if(cursor.moveToNext()){
            str_content=cursor.getString(1)
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        tvEquip.text=str_content+"\n"



    }
}
