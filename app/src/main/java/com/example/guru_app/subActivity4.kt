package com.example.guru_app;

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.drawerlayout.widget.DrawerLayout
import com.example.guru_app.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.prolificinteractive.materialcalendarview.*
import kotlinx.android.synthetic.main.ex_diary.*
import java.util.*


class subActivity4 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var materialCalendarView: MaterialCalendarView
    lateinit var addDiaryBtn: Button
    lateinit var selctedDate: TextView
    lateinit var edit_text: TextView
    private lateinit var binding: ActivityMainBinding
    private val context = this
    //lateinit var dBmanager: DBmanager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var registDiary: Button
    lateinit var showDiary : TextView
    lateinit var content : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex_diary)

        selctedDate = findViewById(R.id.selected_date)
        edit_text = findViewById(R.id.edit_text)
        showDiary = findViewById(R.id.saved_diary)

        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너



        materialCalendarView = findViewById(R.id.materialCalendar)
        materialCalendarView.setSelectedDate(CalendarDay.today()) //처음 실행했을 때 오늘 날짜 선택된 상태
        materialCalendarView.addDecorator(TodayDecorator()) // 오늘 날짜 글자 색 바꿈
        materialCalendarView.setOnDateChangedListener(object: OnDateSelectedListener { //클릭하면 닷 추가
            override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
                materialCalendarView.addDecorator(EventDecorator(Collections.singleton(date)))
                val selcteDate : String
                selctedDate.setText(materialCalendarView.selectedDate.toString())
                var cursor : Cursor
            }

        })






        addDiaryBtn = findViewById(R.id.add_diary_btn)


        addDiaryBtn.setOnClickListener {
            val dialog = CustomDialog(this)
            dialog.showDialog()
            dialog.setOnClickListener(object : CustomDialog.OnDialogClickListener {
                override fun onClicked(name: String)
                {
                    edit_text.setText(name)
                }

            })
        }

        registDiary = findViewById(R.id.regi_diary_btn)

       // dBmanager = DBmanager(this,"diaryDB",null,1)

        registDiary.setOnClickListener {
           //sqlitedb = dBmanager.writableDatabase
            sqlitedb.execSQL("INSERT INTO diary VALUES('"+selctedDate+"', '"+edit_text+"')")
            sqlitedb.close()
        }






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
            R.id.menu4_exDiary-> Toast.makeText(this,"현재 페이지입니다.", Toast.LENGTH_SHORT).show()

        }
        return false

    }


}