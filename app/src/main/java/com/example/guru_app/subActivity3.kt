package com.example.guru_app;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class subActivity3 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prepare_ex)

        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너

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
            R.id.menu3_preEx-> Toast.makeText(this,"현재 페이지입니다.",Toast.LENGTH_SHORT).show()
            R.id.menu4_exDiary-> {
                val intent4 = Intent(this, subActivity4::class.java)
                startActivity(intent4)
            }
        }
        return false
    }
}