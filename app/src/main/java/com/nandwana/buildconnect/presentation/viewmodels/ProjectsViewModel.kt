package com.nandwana.buildconnect.presentation.viewmodels

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandwana.buildconnect.data.network.ProjectService
import com.nandwana.buildconnect.domain.DataResource
import com.nandwana.buildconnect.domain.PdfDownloadState
import com.nandwana.buildconnect.domain.ProjectState
import com.nandwana.buildconnect.domain.ProjectsState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val projectService: ProjectService
) : ViewModel() {

    val _projects = MutableStateFlow(ProjectsState())

    val projects = _projects.asStateFlow()

    private val _pdfDownloadState = MutableStateFlow(PdfDownloadState())
    val pdfDownloadState = _pdfDownloadState.asStateFlow()


    private val _project = MutableStateFlow(ProjectState())
    val project = _project.asStateFlow()

    init {
        viewModelScope.launch {
            getProjects()
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun viewPdf(projectId: String){
        viewModelScope.launch {

            _pdfDownloadState.value = _pdfDownloadState.value.copy(isDownloading = true)

            val result = projectService.getProjectPdf(projectId)
            Log.d("pdf", result.toString())
            val decodedBytes = Base64.decode(result.pdfData)
            val fileName = "project_pdf.pdf"
            val file = File(context.getExternalFilesDir(null), fileName)
            _pdfDownloadState.value = _pdfDownloadState.value.copy(isDownloading = false, file = file)

        }
    }


    @OptIn(ExperimentalEncodingApi::class)
    fun downloadPdf(projectId: String) {

        Log.d("pdf", "downloading pdf function call")

        viewModelScope.launch {

            _pdfDownloadState.value = _pdfDownloadState.value.copy(isDownloading = true)

            val result = projectService.getProjectPdf(projectId)
            Log.d("pdf", result.toString())
            val decodedBytes = Base64.decode(result.pdfData)
            val fileName = "project_pdf.pdf"
            val file = File(context.getExternalFilesDir(null), fileName)

            try {

                val resolver = context.contentResolver
                val pdfFileName = "project_pdf.pdf"

                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, pdfFileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/BuildConnect")
                }

                if (Build.VERSION.SDK_INT >= 29) {
                    val uri =
                        resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

                    uri?.let {
                        _pdfDownloadState.value = _pdfDownloadState.value.copy(file = file)
                        resolver.openOutputStream(it)?.use { outputStreme ->

                            outputStreme.write(decodedBytes)
                            outputStreme.flush()
                        }
                        Toast.makeText(context, "PDF saved in Downloads folder", Toast.LENGTH_SHORT).show()
                    } ?: run {
                        Toast.makeText(context, "Failed to save PDF", Toast.LENGTH_SHORT).show()
                    }

                }

                _pdfDownloadState.value = _pdfDownloadState.value.copy(isDownloading = false)
            } catch (e: Exception) {
                Toast.makeText(context, "Error saving PDF: ${e.localizedMessage}", Toast.LENGTH_LONG).show()

            }

        }


    }


    fun _getProjects(): Flow<DataResource<ProjectsState>> = flow {

        emit(DataResource.Loading())

        val result = projectService.getProjects()
        Log.d("projects", result.toString())

        emit(DataResource.Success(ProjectsState(projects = result)))

    }.catch {
        Log.d("projects", it.message.toString())
        emit(DataResource.Error(it.message.toString()))
    }

    suspend fun getProjects() {

        _getProjects().onEach {
            when (it) {
                is DataResource.Error -> _projects.value = _projects.value.copy(error = it.message)
                is DataResource.Loading -> _projects.value = _projects.value.copy(isLoading = true)
                is DataResource.Success -> {
                    if (it.data != null) {
                        _projects.value =
                            _projects.value.copy(projects = it.data.projects, isLoading = false)
                    }
                }
            }
        }.collect()

    }

    fun _getProject(projectId: String): Flow<DataResource<ProjectState>> = flow {

        emit(DataResource.Loading())

        val result = projectService.getProjectById(projectId)
        Log.d("project", result.toString())

        emit(DataResource.Success(ProjectState(project = result)))

    }.catch {
        Log.d("project", it.message.toString())
        emit(DataResource.Error(it.message.toString()))
    }

    suspend fun getProject(projectId: String) {

        _getProject(projectId = projectId).onEach {
            when (it) {
                is DataResource.Error -> _project.value = _project.value.copy(error = it.message)
                is DataResource.Loading -> _project.value = _project.value.copy(isLoading = true)
                is DataResource.Success -> {
                    if (it.data != null) {
                        _project.value =
                            _project.value.copy(project = it.data.project, isLoading = false)
                    }
                }
            }
        }.collect()

    }

}