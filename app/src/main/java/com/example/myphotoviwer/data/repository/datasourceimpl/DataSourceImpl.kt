package com.example.myphotoviwer.data.repository.datasourceimpl

import com.example.myphotoviwer.data.api.ApiConstants
import com.example.myphotoviwer.data.api.ApiService
import com.example.myphotoviwer.data.repository.datasource.DataSource
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DataSourceImpl  : DataSource {

    private var apiServiceInstance : ApiService? = null
    private var currentBaseUrl : String = ""

    private fun apiService() : ApiService{
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().writeTimeout(2, TimeUnit.MINUTES).addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .client(client)
                .baseUrl(ApiConstants.BASE_URL)
                .build()
            apiServiceInstance = retrofit.create(ApiService::class.java)

        return apiServiceInstance as ApiService
    }
    override suspend fun getPhotoList(){
       // return apiService().getDistricts()
    }
}