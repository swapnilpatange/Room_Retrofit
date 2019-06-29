package com.example.ruchir.android_test_ms

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeService {

    val liveUserResponse: MutableLiveData<List<ApiDataModel>> = MutableLiveData()
    companion object Factory {
        var gson = GsonBuilder().setLenient().create()
        fun create(): HomeInterface {
            Log.e("retrofit","create")
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .build()
            return retrofit.create(HomeInterface::class.java)
        }
    }
    fun loadHomeData(): MutableLiveData<List<ApiDataModel>>? {
        Log.e("loadAndroidData","yes")
        val retrofitCall  = create().getData()
        retrofitCall.enqueue(object : Callback<List<ApiDataModel>> {
            override fun onFailure(call: Call<List<ApiDataModel>>, t: Throwable?) {
                Log.e("on Failure :", "retrofit error")
            }
            override fun onResponse(call: Call<List<ApiDataModel>>, response: retrofit2.Response<List<ApiDataModel>>) {
                val list  = response.body()
                liveUserResponse?.value = list

            }
        })
        return liveUserResponse
    }
}