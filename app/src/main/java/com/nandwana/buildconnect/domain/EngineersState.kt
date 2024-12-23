package com.nandwana.buildconnect.domain

import com.nandwana.buildconnect.data.model.ProfessionalModel

data class EngineersState (
    val engineers: List<ProfessionalModel> = emptyList(),
    val isLoading:Boolean = false,
    val error:String? = null
)