package com.nandwana.buildconnect.data.model

data class ProfessionalModel(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val profilePhoto: String,
    val latitude: Double,
    val longitude: Double,
    val yearsOfExperience: Int,
    val workImages: List<String>
)