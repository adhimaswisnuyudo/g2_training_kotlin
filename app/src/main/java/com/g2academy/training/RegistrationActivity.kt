package com.g2academy.training

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.g2academy.training.models.*
import com.g2academy.training.networks.RegistrationInterface
import com.g2academy.training.networks.RetrofitInstance
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class RegistrationActivity : AppCompatActivity() {
    lateinit var txt_fullname : EditText
    lateinit var txt_username : EditText
    lateinit var txt_password : EditText
    lateinit var txt_dob : TextView
    lateinit var btn_dob : ImageButton
    lateinit var spinner_gender : Spinner
    lateinit var rg_status : RadioGroup
    lateinit var rb_mariage : RadioButton
    lateinit var rb_single : RadioButton
    lateinit var btn_registration : Button
    var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        txt_fullname = findViewById(R.id.txt_fullname)
        txt_username = findViewById(R.id.txt_name)
        txt_password = findViewById(R.id.txt_price)
        txt_dob = findViewById(R.id.txt_dob)
        btn_dob = findViewById(R.id.btn_dob)
        spinner_gender = findViewById(R.id.spinner_gender)
        rg_status = findViewById(R.id.rg_status)
        rb_mariage = findViewById(R.id.rb_mariage)
        rb_single = findViewById(R.id.rb_single)
        btn_registration = findViewById(R.id.btn_registration)

//  append arraylist to spinner
        val adapter_gender = ArrayAdapter.createFromResource(this,R.array.list_gender,
        android.R.layout.simple_spinner_item)
        adapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_gender.adapter = adapter_gender

//  Date Picker
        val datesetListener = object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                calendar.set(Calendar.YEAR,year)
                calendar.set(Calendar.MONTH,month)
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                setSdf()
            }

        }
        btn_dob.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                DatePickerDialog(this@RegistrationActivity,
                datesetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

        btn_registration.setOnClickListener{
            val fullname = txt_fullname.text.toString()
            val username = txt_username.text.toString()
            val password = txt_password.text.toString()
            val dob = txt_dob.text.toString()
            val gender = spinner_gender.selectedItem.toString()
            var status : String
            if(rb_mariage.isChecked){
                status = rb_mariage.text.toString()
            }
            else if(rb_single.isChecked){
                status = rb_single.text.toString()
            }
            else{
                status = "-"
            }

            try{
//                val pd = ProgressDialog(this@RegistrationActivity)
//                pd.setTitle("Mohon Tunggu...")
//                pd.setMessage("Mengirim Data...")
//                pd.show()
                val ad = AlertDialog.Builder(applicationContext)
                ad.setTitle("Please Wait...")
                ad.setMessage("Mengirim Data...")
                ad.setCancelable(false)
                ad.show()
                val retrofitInstance = RetrofitInstance.getRetrofitInstance().create(RegistrationInterface::class.java)
                val registerBody = RegisterBody(username,password,fullname,gender,status,dob)
                retrofitInstance.register(registerBody).enqueue(object : retrofit2.Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@RegistrationActivity, t.message, Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                        pd.hide()
                        if(response.code()==200 && response.isSuccessful){
                            val responseBody = JSONObject(response.body()?.string())
                            var gson = Gson()
                            var responseMember = gson.fromJson(responseBody.toString(),
                                ResponseMember2::class.java)

                            if(responseMember.status=="success"){
                                Toast.makeText(applicationContext,"Registrasi Sukses",Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(applicationContext,responseMember.message,Toast.LENGTH_SHORT).show()
                            }

                        }
                    }
                })
            }
            catch (e:Exception){
                Toast.makeText(this@RegistrationActivity,e.toString(),Toast.LENGTH_LONG).show()
            }
        }
    }

    fun setSdf(){
        val base_format = "yyyy/MM/dd"
        val sdf = SimpleDateFormat(base_format,Locale.US)
        txt_dob.text = sdf.format(calendar.getTime()).toString()
    }
}