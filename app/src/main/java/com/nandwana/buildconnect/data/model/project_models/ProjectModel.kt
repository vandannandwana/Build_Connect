package com.nandwana.buildconnect.data.model.project_models

data class ProjectModel(
    val costComparison: String,
    val currentPhase: String,
    val estimatedExpenditure: String,
    val manpower: ManpowerModel,
    val materials: List<MaterialModel>,
    val progress: Double,
    val projectId: String,
    val projectImages: List<ProjectImageModel>,
    val projectName: String,
    val scheduling: List<SchedulingModel>,
    val totalExpenditureNow: String,
    val upcomingPhases: List<String>
)