package com.g2academy.training

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.g2academy.training.networks.AddproductInterface
import com.g2academy.training.networks.RetrofitInstance
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class AddProductActivity : AppCompatActivity() {
    lateinit var txt_name : EditText
    lateinit var txt_price : EditText
    lateinit var btn_add : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        btn_add = findViewById(R.id.btn_add)
        txt_name = findViewById(R.id.txt_name)
        txt_price = findViewById(R.id.txt_price)

        btn_add.setOnClickListener{
            addproduct()
        }
    }

    fun addproduct(){
        try{
            val ad = ProgressDialog(this@AddProductActivity)
            ad.setTitle("Please Wait")
            ad.setMessage("Send Data...")
            ad.show()
            val requestBody : RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name",txt_name.text.toString())
                .addFormDataPart("price",txt_price.text.toString())
                .build()
            val retrofitInstance = RetrofitInstance.getRetrofitInstance().create(AddproductInterface::class.java)
            retrofitInstance.addproduct(requestBody).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    ad.hide()
                    Log.d("TESTING",response.body().toString())
                    val responseBody = JSONObject(response.body()!!.string())
                    Toast.makeText(applicationContext, responseBody["message"].toString(), Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("TESTING",t.message.toString())
                    Toast.makeText(applicationContext,t.message,Toast.LENGTH_SHORT).show()
                }

            })
        }
        catch (e:Exception){
            Log.d("TESTING",e.toString())
        }
    }

}