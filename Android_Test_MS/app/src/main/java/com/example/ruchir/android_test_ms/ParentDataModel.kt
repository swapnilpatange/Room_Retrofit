package com.example.ruchir.android_test_ms

class ParentDataModel {
    var expandableDataModels: List<ExpandableDataModel>
    var userid: String

    constructor(userid: String = "",expandableDataModels: List<ExpandableDataModel> ) {
        this.expandableDataModels = expandableDataModels
        this.userid = userid
    }
}