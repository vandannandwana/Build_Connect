package com.nandwana.buildconnect.data.model.project_models

data class MaterialModel(
    val materialId: String,
    val name: String,
    val remainingQuantity: Double,
    val totalCost: Double,
    val totalQuantity: Double
)