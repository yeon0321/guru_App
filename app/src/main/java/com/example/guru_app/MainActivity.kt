package com.example.guru_app

import com.example.guru_app.databinding.ActivityMainBinding
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager


import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import androidx.core.view.GravityCompat

import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener

import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK 028cd290b4ee13520c279c6760d11944"
    }

    val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>( Manifest.permission.ACCESS_FINE_LOCATION)

    private lateinit var binding : ActivityMainBinding
    private val listItems = arrayListOf<ListLayout>()
    private val listAdapter = ListAdapter(listItems)
    private var pageNumber = 1
    private var keyword = ""
    private lateinit var mapView : MapView


    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
/////////////???????????????///////////////////
       /* val toolbar: Toolbar = findViewById(R.id.toolbar) // toolBar??? ?????? App Bar ??????
        setSupportActionBar(toolbar) // ?????? ??????

        supportActionBar?.setDisplayHomeAsUpEnabled(true) // ???????????? ?????? ??? ?????? ?????????
        supportActionBar?.setHomeAsUpIndicator(R.drawable.navi_menu) // ????????? ????????? ??????
        supportActionBar?.setDisplayShowTitleEnabled(false) // ????????? ????????? ????????????
*/
        // ??????????????? ????????? ??????
        drawerLayout = findViewById(R.id.drawer_layout)

        // ??????????????? ????????? ???????????? ????????? ???????????? ???????????? ?????? ??????
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this) //navigation ?????????




        /////////////////////////////////////////////////////
        mapView = binding.mapView

        binding.rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = listAdapter
        listAdapter.setItemClickListener(object: ListAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val mapPoint = MapPoint.mapPointWithGeoCoord(listItems[position].y, listItems[position].x)
                binding.mapView.setMapCenterPointAndZoomLevel(mapPoint, 1, true)
            }
        })


        binding.btnSearch.setOnClickListener {
            keyword = binding.etSearchField.text.toString()
            pageNumber = 1
            searchKeyword(keyword, pageNumber)
        }

        binding.btnPrevPage.setOnClickListener {
            pageNumber--
            binding.tvPageNumber.text = pageNumber.toString()
            searchKeyword(keyword, pageNumber)
        }

        binding.btnNextPage.setOnClickListener {
            pageNumber++
            binding.tvPageNumber.text = pageNumber.toString()
            searchKeyword(keyword, pageNumber)
        }

        //??? ?????? ??????
        binding.btnstart.setOnClickListener {
            val permissionCheck =
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                try {
                    val userNowLocation: Location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
                    val uLatitude = userNowLocation.latitude
                    val uLongitude = userNowLocation.longitude
                    val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)
                    mapView.setMapCenterPoint(uNowPosition, true)
                    //?????????
                    binding.mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading

                    val marker = MapPOIItem()
                    marker.apply {
                        itemName = "??? ??????"
                        marker.mapPoint = uNowPosition
                        marker.markerType = MapPOIItem.MarkerType.RedPin
                    }
                    binding.mapView.addPOIItem(marker)

                } catch (e: NullPointerException) {
                    Log.e("LOCATION_ERROR", e.toString())
                    ActivityCompat.finishAffinity(this)

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    System.exit(0)
                }

            } else {
                Toast.makeText(this, "?????? ????????? ????????????.", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
            }
        }


    }

    private fun searchKeyword(keyword: String, page: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPI::class.java)
        val call = api.getSearchKeyword(API_KEY, keyword, page)

        call.enqueue(object: Callback<ResultSearchKeyword> {
            override fun onResponse(call: Call<ResultSearchKeyword>, response: Response<ResultSearchKeyword>) {
                addItemsAndMarkers(response.body())
            }
            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                Log.w("LocalSearch", "?????? ??????: ${t.message}")
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addItemsAndMarkers(searchResult: ResultSearchKeyword?) {
        if (!searchResult?.documents.isNullOrEmpty()) {
            listItems.clear()
            binding.mapView.removeAllPOIItems()
            for (document in searchResult!!.documents) {
                val item = ListLayout(document.place_name,
                    document.road_address_name,
                    document.address_name,
                    document.x.toDouble(),
                    document.y.toDouble())
                listItems.add(item)

                val point = MapPOIItem()
                point.apply {
                    itemName = document.place_name
                    mapPoint = MapPoint.mapPointWithGeoCoord(document.y.toDouble(),
                        document.x.toDouble())
                    markerType = MapPOIItem.MarkerType.BluePin
                    selectedMarkerType = MapPOIItem.MarkerType.RedPin
                }
                binding.mapView.addPOIItem(point)
            }
            listAdapter.notifyDataSetChanged()

            binding.btnNextPage.isEnabled = !searchResult.meta.is_end
            binding.btnPrevPage.isEnabled = pageNumber != 1

        } else {
            Toast.makeText(this, "?????? ????????? ????????????", Toast.LENGTH_SHORT).show()
        }
    }




    // ?????? ?????? ????????? ?????? ?????? ??? ???????????? ??????
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // ????????? ?????? ?????? ????????? id ?????? ????????? ??????????????? ??????
        when(item.itemId){
            android.R.id.home->{
                // ????????? ?????? ????????? ??????????????? ????????? ??????
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // ????????? ??? ????????? ?????? ????????? ???????????? ??????
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu1_parkMap-> Toast.makeText(this,"?????? ??????????????????.",Toast.LENGTH_SHORT).show()

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