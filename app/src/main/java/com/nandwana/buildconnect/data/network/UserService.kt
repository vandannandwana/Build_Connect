package com.nandwana.buildconnect.data.network

import com.nandwana.buildconnect.data.model.ResponseModel
import com.nandwana.buildconnect.data.model.TemporaryUser
import com.nandwana.buildconnect.data.model.UserModel
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("/api/users/otpsend")
    suspend fun sendOtp(@Body temporaryUser: TemporaryUser): ResponseModel

    @POST("/api/users/verifyotp")
    suspend fun verifyOtp(@Body temporaryUser: TemporaryUser): ResponseModel

    @POST("/api/users/register")
    suspend fun registerUser(@Body user: UserModel): ResponseModel

    @POST("/api/users/login")
    suspend fun loginUser(@Body temporaryUser: TemporaryUser): ResponseModel

    @POST("/api/users/forgot-password")
    suspend fun forgotPassword(@Body temporaryUser: TemporaryUser): ResponseModel

    @POST("/api/users/reset-password")
    suspend fun resetPassword(@Body temporaryUser: TemporaryUser): ResponseModel


}