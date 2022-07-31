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
import android.widget.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_eq_list.*

class eqList : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var myHelper: DBManager
    lateinit var sqlDB:SQLiteDatabase
    lateinit var eqbutton:Button
    lateinit var layout:LinearLayout
    //추가
    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eq_list)
        layout=findViewById(R.id.content)
        eqbutton=findViewById(R.id.eqButton)

        //추가
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너


        myHelper = DBManager(this, "eqListDB", null, 1)

        sqlDB = myHelper.readableDatabase

        val cursor: Cursor
        cursor = sqlDB.rawQuery("SELECT *FROM listTBL", null)
        eqbutton.setOnClickListener {

            var num: Int = 0
            while (cursor.moveToNext()) {
                val str_name = cursor.getString(0)

                var layout_item: LinearLayout = LinearLayout(this)
                layout_item.orientation = LinearLayout.VERTICAL
                layout_item.setPadding(20, 10, 20, 10)
                layout_item.id = num

                var tvName: TextView = TextView(this)
                tvName.text = str_name
                tvName.textSize=20F
                layout_item.addView(tvName)

                layout_item.setOnClickListener {
                    val intent = Intent(this, contentActivity::class.java)
                    intent.putExtra("intent_name", str_name)
                    startActivity(intent)
                }
                layout.addView(layout_item)
                num++;
            }

            cursor.close()
            sqlDB.close()


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
            R.id.menu2_exList-> Toast.makeText(this,"현재 페이지입니다.", Toast.LENGTH_SHORT).show()
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

