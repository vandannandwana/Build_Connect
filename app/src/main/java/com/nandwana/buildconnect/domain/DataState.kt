package com.nandwana.buildconnect.domain

data class DataState<T>(
    val data: T? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)