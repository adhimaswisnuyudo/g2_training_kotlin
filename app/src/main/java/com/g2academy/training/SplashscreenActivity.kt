package com.g2academy.training

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.g2academy.training.networks.Myconst.Companion.SP_LOGINDATA
import java.util.*

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        try{
            this.supportActionBar!!.hide()
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            Timer().schedule(object :TimerTask(){
                override fun run() {
                    if(checkLogin()){
                        val intent = Intent(applicationContext,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        val intent = Intent(applicationContext,LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            },2000)
        }
        catch(e: Exception){
            print(e)
        }
    }
    fun checkLogin():Boolean{
        val sp = getSharedPreferences(SP_LOGINDATA, MODE_PRIVATE)
        val isLogin = sp.getBoolean("islogin",false)
        return isLogin
    }

    fun checkInternet(context : Context):Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectivityManager.activeNetwork?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network)?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }
        }
        else{
            @Suppress("DEPRECATION") val networkInfo = connectivityManager.activeNetworkInfo?:return false
            @Suppress("DEPRECATION") return networkInfo.isConnected

        }

    }
}