package com.nandwana.buildconnect.data.network

import com.nandwana.buildconnect.data.model.ProfessionalModel
import retrofit2.http.GET
import retrofit2.http.Path

interface EngineerService {

    @GET("getAllEngineers")
    suspend fun getEngineers(): List<ProfessionalModel>

    @GET("experience/{years}")
    suspend fun getEngineerByExperience(@Path("years") years: Int): List<ProfessionalModel>

}