package com.nandwana.buildconnect.data.network

import com.nandwana.buildconnect.data.model.ProjectPdfModel
import com.nandwana.buildconnect.data.model.project_models.ProjectModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ProjectService {

    @GET("projects")
    suspend fun getProjects():List<ProjectModel>

    @GET("projects/{projectId}")
    suspend fun getProjectById(@Path("projectId") projectId:String):ProjectModel

    @GET("projects/{projectId}/download-pdf")
    suspend fun getProjectPdf(@Path("projectId") projectId:String):ProjectPdfModel

}