package com.nandwana.buildconnect.domain

import com.nandwana.buildconnect.data.model.project_models.ProjectModel

data class ProjectsState(
    val projects: List<ProjectModel> = emptyList(),
    val isLoading:Boolean = false,
    val error:String? = null

)