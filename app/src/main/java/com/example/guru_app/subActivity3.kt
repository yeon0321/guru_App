package com.example.guru_app;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class subActivity3 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    lateinit var preEx_body: Button
    lateinit var preEx_upperbody: Button
    lateinit var preEx_lowerbody: Button
    lateinit var preEx_stretch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prepare_ex)

        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너




        preEx_body = findViewById(R.id.preEx_body)
        preEx_upperbody = findViewById(R.id.preEx_upperbody)
        preEx_lowerbody = findViewById(R.id.preEx_lowerbody)
        preEx_stretch = findViewById(R.id.preEx_stretch)

        preEx_body.setOnClickListener({
            val intent_body = Intent(this, prepare_ex_body::class.java)
            startActivity(intent_body)
        })


        preEx_lowerbody.setOnClickListener({
            val intent_lowerbody = Intent(this, prepare_ex_lowerbody::class.java)
            startActivity(intent_lowerbody)
        })

        preEx_upperbody.setOnClickListener({
            val intent_upperbody = Intent(this, prepare_ex_upperbody::class.java)
            startActivity(intent_upperbody)
        })

        preEx_stretch.setOnClickListener({
            val intent_stretch = Intent(this, prepare_ex_stretching::class.java)
            startActivity(intent_stretch)
        })


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