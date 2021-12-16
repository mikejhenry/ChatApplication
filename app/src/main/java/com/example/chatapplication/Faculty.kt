package com.example.chatapplication

open class Faculty {
    var firstname: String? = null
    var lastname: String? = null
    var email: String? = null
    var id: String? = null
    var department: String? = null
    var uid: String? = null
    var rollnumber: String? = null

    constructor(){

    }

    constructor(firstname: String?, lastname: String?, id: String?, rollnumber: String?, department: String?, email: String?, uid: String?){
        this.firstname = firstname
        this.lastname = lastname
        this.id = id
        this.email = email
        this.uid = uid
        this.rollnumber = rollnumber
        this.department = department
    }
}