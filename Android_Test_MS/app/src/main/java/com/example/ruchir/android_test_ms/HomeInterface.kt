package com.example.ruchir.android_test_ms

import retrofit2.Call
import retrofit2.http.GET

interface HomeInterface {

    @GET("posts")
    fun getData(): Call<List<ApiDataModel>>
}