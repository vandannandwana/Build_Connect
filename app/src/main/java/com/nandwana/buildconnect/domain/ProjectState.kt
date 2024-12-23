package com.nandwana.buildconnect.domain

import com.nandwana.buildconnect.data.model.project_models.ProjectModel

data class ProjectState(
    val project: ProjectModel? = null,
    val isLoading:Boolean = false,
    val error:String? = null
)
