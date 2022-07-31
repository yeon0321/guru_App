package com.example.guru_app

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class eqList : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var myHelper: myDBHelper
    lateinit var sqlDB:SQLiteDatabase
    lateinit var listView: ListView
    //추가
    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eq_list)
        listView=findViewById(R.id.listView)

        //추가
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너

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

    // 툴바 메뉴 버튼이 클릭 됐을 때 실행하는 함수
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // 클릭한 툴바 메뉴 아이템 id 마다 다르게 실행하도록 설정
        when(item.itemId){
            android.R.id.home->{
                // 햄버거 버튼 클릭시 네비게이션 드로어 열기
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 드로어 내 아이템 클릭 이벤트 처리하는 함수
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu1_parkMap-> {
                val intent1 = Intent(this, MainActivity::class.java)
                startActivity(intent1)
            }
            R.id.menu2_exList-> {
                val intent2 = Intent(this, eqList::class.java)
                startActivity(intent2)
            }
            R.id.menu3_preEx-> {
                val intent3 = Intent(this, subActivity3::class.java)
                startActivity(intent3)
            }
            R.id.menu4_exDiary-> {
                val intent4 = Intent(this, subActivity4::class.java)
                startActivity(intent4)
            }
        }
        return false
    }


}

