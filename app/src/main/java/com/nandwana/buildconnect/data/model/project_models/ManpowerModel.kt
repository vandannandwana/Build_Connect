package com.nandwana.buildconnect.data.model.project_models

data class ManpowerModel(
    val fromTime: String,
    val shift: String,
    val toTime: String,
    val totalWorkers: Int,
    val workHours: Int
)