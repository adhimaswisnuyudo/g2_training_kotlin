package com.g2academy.training.networks

import com.g2academy.training.models.LoginBody
import com.g2academy.training.models.RegisterBody
import com.g2academy.training.networks.Myconst.Companion.CONTENT_TYPE
import com.g2academy.training.networks.Myconst.Companion.DEFAULT_HEADER
import com.g2academy.training.networks.Myconst.Companion.EP_ADDPRODUCT
import com.g2academy.training.networks.Myconst.Companion.EP_ALLPRODUCTS
import com.g2academy.training.networks.Myconst.Companion.EP_LOGIN
import com.g2academy.training.networks.Myconst.Companion.EP_REGISTER
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class RetrofitInstance {
    companion object{
        val BASE_URL : String = Myconst.BASE_URL
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply{
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client : OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        fun getRetrofitInstance():Retrofit{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}

interface LoginInterface{
    @Headers(DEFAULT_HEADER,CONTENT_TYPE)
    @POST(EP_LOGIN)
    fun login(@Body data : LoginBody): retrofit2.Call<ResponseBody>
}

interface RegistrationInterface{
    @Headers(DEFAULT_HEADER, CONTENT_TYPE)
    @POST(EP_REGISTER)
    fun register(@Body data : RegisterBody):retrofit2.Call<ResponseBody>
}

interface AllproductsInterface{
    @GET(EP_ALLPRODUCTS)
    fun allproducts():retrofit2.Call<ResponseBody>
}

interface AddproductInterface{
//    @Multipart
    @Headers(DEFAULT_HEADER)
    @POST(EP_ADDPRODUCT)
    fun addproduct(@Body data : RequestBody):retrofit2.Call<ResponseBody>
}