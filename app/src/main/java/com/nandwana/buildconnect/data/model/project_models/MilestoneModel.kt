package com.nandwana.buildconnect.data.model.project_models

data class MilestoneModel(
    val fromTime: String,
    val name: String,
    val toTime: String,
    val totalTime: Int
)