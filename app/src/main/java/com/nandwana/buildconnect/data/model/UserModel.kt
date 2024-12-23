package com.nandwana.buildconnect.data.model

data class UserModel(
    val email:String,
    val name:String,
    val otp:String,
    val password :String
){
    constructor() : this("","", "", "")
}