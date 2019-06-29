package com.example.ruchir.android_test_ms

class ExpandableDataModel {
    var body: String
    var title: String
    var id: String

    constructor(body: String = "", title: String = "", id: String = "") {
        this.body = body
        this.title = title
        this.id = id
    }
}