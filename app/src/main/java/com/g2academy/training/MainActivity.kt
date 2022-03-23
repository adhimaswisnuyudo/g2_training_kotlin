package com.g2academy.training

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.g2academy.training.networks.Myconst.Companion.SP_LOGINDATA
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var doubleBackToExitPressedOnce = false
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val productFragment = ProductFragment()

        changeFragment(homeFragment)

        findViewById<BottomNavigationView>(R.id.btmnavigationview).setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_home->changeFragment(homeFragment)
                R.id.menu_profile->changeFragment(profileFragment)
                R.id.menu_product->changeFragment(productFragment)
                R.id.menu_note->startActivity(Intent(applicationContext,NoteActivity::class.java))
            }
            true
        }

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh)


        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.setOnRefreshListener {
            val currentFragment = supportFragmentManager.fragments.last()
            if(currentFragment==homeFragment){
                swipeRefreshLayout.isEnabled = true
                Toast.makeText(applicationContext,"Refresh Dashboard",Toast.LENGTH_SHORT).show()
            }
            swipeRefreshLayout.isRefreshing = false
        }


    }

    fun changeFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout,fragment)
            commit()
        }

    override fun onBackPressed() {
        if(doubleBackToExitPressedOnce){
            super.onBackPressed()
            System.exit(0)
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this,"Click back to exit app",Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({doubleBackToExitPressedOnce = false},2000)
    }

    fun logout(){
        val sp = getSharedPreferences(SP_LOGINDATA, MODE_PRIVATE)
        sp.edit().clear().commit()
        startActivity(Intent(applicationContext,SplashscreenActivity::class.java))
        finish()
    }
}