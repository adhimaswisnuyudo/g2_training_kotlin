package com.g2academy.training

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.g2academy.training.models.LoginBody
import com.g2academy.training.models.ResponseMember
import com.g2academy.training.networks.LoginInterface
import com.g2academy.training.networks.Myconst.Companion.SP_LOGINDATA
import com.g2academy.training.networks.RetrofitInstance
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var username : String
    lateinit var password : String
    lateinit var txt_username : EditText
    lateinit var txt_password : EditText
    lateinit var btn_login : Button
    lateinit var btn_registration : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        try {
            this.supportActionBar!!.hide()
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        catch (e:Exception){}
        btn_login = findViewById(R.id.btn_login)
        btn_registration = findViewById(R.id.btn_registration)
        txt_username = findViewById(R.id.txt_name)
        txt_password = findViewById(R.id.txt_price)

        btn_login.setOnClickListener{
            login()
        }

        btn_registration.setOnClickListener{
            val intent = Intent(applicationContext,RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    fun login(){
        username = txt_username.text.toString()
        password = txt_password.text.toString()
        if(username.trim().length < 1){
            Toast.makeText(applicationContext,"Username Tidak Boleh Kosong",Toast.LENGTH_SHORT).show()
        }
        else if(password.trim().length < 1){
            Toast.makeText(applicationContext,"Password Tidak Boleh Kosong",Toast.LENGTH_SHORT).show()
        }
        else{
            try{
                val retrofitInstance = RetrofitInstance.getRetrofitInstance().create(LoginInterface::class.java)
                val loginBody = LoginBody(username, password)
                retrofitInstance.login(loginBody).enqueue(object : retrofit2.Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if(response.code()==200 && response.isSuccessful){
                            val responseBody = JSONObject(response.body()?.string())
                            if(responseBody["status"] == "success"){
                                var gson = Gson()
                                var responseMember = gson.fromJson(responseBody["member"].toString(),ResponseMember::class.java)
                                var sp = getSharedPreferences(SP_LOGINDATA, Context.MODE_PRIVATE)
                                var editor = sp.edit()

                                editor.putString("username",responseMember.username)
                                editor.putString("memberid",responseMember.memberid)
                                editor.putString("fullname",responseMember.fullname)
                                editor.putString("photo",responseMember.photo)

//                                menyimpan json kedalam sharedpref
                                editor.putString("alldata",gson.toJson(responseMember))

                                if(responseMember.islogin == true){
                                    editor.putBoolean("islogin",true)
                                }
                                else{
                                    editor.putBoolean("islogin",false)
                                }
                                editor.commit()
//                                Toast.makeText(applicationContext,
//                                    sp.getString("alldata","Unavailable"),
//                                    Toast.LENGTH_LONG).show()
                                val intent = Intent(applicationContext,MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            else{
                                Toast.makeText(applicationContext,responseBody["message"].toString(),Toast.LENGTH_LONG).show()
                            }

                        }
                    }
                })
            }
            catch (e:Exception){
                Toast.makeText(this@LoginActivity,e.toString(),Toast.LENGTH_LONG).show()
            }
        }
    }
}