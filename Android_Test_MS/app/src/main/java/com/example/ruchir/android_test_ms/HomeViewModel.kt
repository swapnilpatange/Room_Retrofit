package com.example.ruchir.android_test_ms

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log

class HomeViewModel: ViewModel() {

    private val mService  =  HomeService()
    fun getHomeData() : MutableLiveData<List<ApiDataModel>>? {
        Log.e("getAndroidData","yes")
        return mService.loadHomeData()
    }

}