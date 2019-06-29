package com.example.ruchir.android_test_ms

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.logOut -> {
                prefManager.saveLogin(false)
                finish()
                var intent: Intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    lateinit var parentDataModels: ArrayList<ParentDataModel>
    lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        prefManager = PrefManager(applicationContext)
        logOut.setOnClickListener(this)
        parentDataModels = ArrayList<ParentDataModel>()
        getHomeData()


    }

    private fun getHomeData() {
        val homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.getHomeData()?.observe(this, Observer<List<ApiDataModel>> { data ->
            Log.e("list", data?.size.toString())

            var expandableDataModels: ArrayList<ExpandableDataModel>
            expandableDataModels = ArrayList()
            for (i in 0..data!!.size - 1) {
                if (i != data.size - 1 && data.get(i).userId.equals(data.get(i + 1).userId)) {
                    var expandableDataModel: ExpandableDataModel = ExpandableDataModel()
                    expandableDataModel.id = data.get(i).id
                    expandableDataModel.body = data.get(i).body
                    expandableDataModel.title = data.get(i).title

                    expandableDataModels.add(expandableDataModel)
                } else {

                    var expandableDataModel: ExpandableDataModel = ExpandableDataModel()
                    expandableDataModel.id = data.get(i).id
                    expandableDataModel.body = data.get(i).body
                    expandableDataModel.title = data.get(i).title

                    expandableDataModels.add(expandableDataModel)

                    var parentDataModel: ParentDataModel = ParentDataModel(data.get(i).userId, expandableDataModels)
                    parentDataModels.add(parentDataModel)
                    expandableDataModels = ArrayList()
                }
            }
            expandableList.setAdapter(ExpandableAdapter(parentDataModels, this))

            Log.d("hello", "")
        })
    }
}
