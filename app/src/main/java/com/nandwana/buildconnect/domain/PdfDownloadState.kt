package com.nandwana.buildconnect.domain

import java.io.File

data class PdfDownloadState(
    val isDownloading: Boolean = false,
    val progress: Float = 0f,
    val message: String = "",
    val file: File? = null
)