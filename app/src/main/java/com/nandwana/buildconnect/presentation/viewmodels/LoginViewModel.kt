package com.nandwana.buildconnect.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nandwana.buildconnect.data.model.TemporaryUser
import com.nandwana.buildconnect.data.network.UserService
import com.nandwana.buildconnect.domain.DataResource
import com.nandwana.buildconnect.domain.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val userServices: UserService) : ViewModel() {

    val _loginState = MutableStateFlow(DataState<String>())
    val loginState = _loginState.asStateFlow()


    fun _loginUser(temporaryUser: TemporaryUser): Flow<DataResource<DataState<String>>> = flow {
        emit(DataResource.Loading())

        val result = userServices.loginUser(temporaryUser)

        if (result.message == "Login Successful") {
            emit(DataResource.Success(DataState(data = result.message)))
        } else {
            emit(DataResource.Error(message = result.message))
        }

    }.catch {
        Log.d("Logining User", it.message.toString())
        emit(DataResource.Error(it.message))
    }


    suspend fun loginUser(temporaryUser: TemporaryUser){

        _loginUser(temporaryUser).onEach {

            when(it){
                is DataResource.Error -> _loginState.value = DataState(error = it.message)
                is DataResource.Loading -> _loginState.value = DataState(isLoading = true)
                is DataResource.Success -> _loginState.value = DataState(isLoading = false, data = it.data?.data)
            }

        }.collect()

    }


}