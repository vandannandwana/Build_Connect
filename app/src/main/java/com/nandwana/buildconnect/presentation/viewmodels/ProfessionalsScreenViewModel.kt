package com.nandwana.buildconnect.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandwana.buildconnect.data.network.EngineerService
import com.nandwana.buildconnect.domain.DataResource
import com.nandwana.buildconnect.domain.EngineersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfessionalsScreenViewModel @Inject constructor(
    private val engineerService: EngineerService
):ViewModel() {

    private val _engineers = MutableStateFlow(EngineersState())

    val engineers = _engineers.asStateFlow()

    init {

        viewModelScope.launch {
            getEngineers()
        }

    }

    private fun _getEngineers(): Flow<DataResource<EngineersState>> = flow {
        emit(DataResource.Loading())
        val result = engineerService.getEngineers()
        Log.d("Engineers", result.toString())
        emit(DataResource.Success(EngineersState(engineers = result)))

    }.catch {
        Log.d("Engineers", it.message.toString())
        emit(DataResource.Error(it.message.toString()))
    }

    private suspend fun getEngineers() {

        _getEngineers().onEach {

            when (it) {
                is DataResource.Error -> _engineers.value =
                    _engineers.value.copy(error = it.message.toString())

                is DataResource.Loading -> _engineers.value =
                    _engineers.value.copy(isLoading = true)

                is DataResource.Success -> {
                    if(it.data != null){
                        _engineers.value = _engineers.value.copy(engineers = it.data.engineers)
                    }

                }
            }

        }.collect()

    }




}