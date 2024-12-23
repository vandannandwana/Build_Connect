package com.nandwana.buildconnect.presentation.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.nandwana.buildconnect.data.model.TemporaryUser
import com.nandwana.buildconnect.data.model.UserModel
import com.nandwana.buildconnect.data.network.UserService
import com.nandwana.buildconnect.domain.DataResource
import com.nandwana.buildconnect.domain.DataState
import com.nandwana.buildconnect.domain.EmailSubmissionState
import com.nandwana.buildconnect.domain.OtpVerificationState
import com.nandwana.buildconnect.domain.UserRegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @Named("userPreference")
    private val userPreference: SharedPreferences,
    private val userService: UserService
):ViewModel() {


    private val _emailSubmitted = MutableStateFlow(EmailSubmissionState())
    val emailSubmitted = _emailSubmitted.asStateFlow()

    private val _isOtpVerified = MutableStateFlow(OtpVerificationState())
    val isOtpVerified = _isOtpVerified.asStateFlow()

    private val _userRegister = MutableStateFlow(UserRegisterState())
    val userRegister = _userRegister.asStateFlow()


    fun _submitEmail(temporaryUser: TemporaryUser):Flow<DataResource<EmailSubmissionState>> = flow {

        emit(DataResource.Loading())
        val result = userService.sendOtp(temporaryUser = temporaryUser)
        if(result.message != null){
            Log.d("Email Submit",result.message)
            emit(DataResource.Success(EmailSubmissionState(emailSubmissionSuccess = true, isLoading = false)))
        }

    }.catch {
        emit(DataResource.Error(it.message.toString()))
    }

    suspend fun submitEmail(temporaryUser: TemporaryUser) {

        _submitEmail(temporaryUser).onEach {
            when(it){
                is DataResource.Error -> _emailSubmitted.value = EmailSubmissionState(emailSubmissionError = it.message)
                is DataResource.Loading -> _emailSubmitted.value = EmailSubmissionState(isLoading = true)
                is DataResource.Success -> _emailSubmitted.value = EmailSubmissionState(emailSubmissionSuccess = true, isLoading = false)
            }
        }.collect()

    }


    fun _verifyOtp(temporaryUser: TemporaryUser): Flow<DataResource<OtpVerificationState>> = flow {

        emit(DataResource.Loading())

        val result = userService.verifyOtp(temporaryUser = temporaryUser)
        if(result.message != null){
            Log.d("Otp Verify",result.message)
            result.let {
                if(result.message == "OTP verified Successfully") {
                    emit(DataResource.Success(OtpVerificationState(otpVerificationSuccess = true, isLoading = false)))
                }
            }
        }

    }.catch {
        emit(DataResource.Error(it.message.toString()))
    }



    suspend fun verifyOtp(temporaryUser: TemporaryUser){

        _verifyOtp(temporaryUser).onEach {
            when(it){
                is DataResource.Error -> _isOtpVerified.value = OtpVerificationState(otpVerificationError = it.message)
                is DataResource.Loading -> _isOtpVerified.value = OtpVerificationState(isLoading = true)
                is DataResource.Success -> _isOtpVerified.value = OtpVerificationState(otpVerificationSuccess = true, isLoading = false)
            }
        }.collect()

    }

    fun _registerUser(user: UserModel): Flow<DataResource<UserRegisterState>> = flow {
        emit(DataResource.Loading())
        val result = userService.registerUser(user = user)
        if(result.message != null){
            Log.d("User Register",result.message)
            result.let {
                if(result.message == "User Registered Successfully") {
                    emit(DataResource.Success(UserRegisterState(userRegisterSuccess = true, isLoading = false)))
                }
            }
        }
    }.catch {
        emit(DataResource.Error(it.message.toString()))
    }


    suspend fun registerUser(user: UserModel) {

        _registerUser(user).onEach {
            when(it){
                is DataResource.Error -> _userRegister.value = UserRegisterState(userRegisterError = it.message)
                is DataResource.Loading -> _userRegister.value = UserRegisterState(isLoading = true)
                is DataResource.Success -> _userRegister.value = UserRegisterState(userRegisterSuccess = true, isLoading = false)
            }
        }.collect()

    }

    fun _loginUser(user: TemporaryUser,context: Context): Flow<DataResource<UserRegisterState>> = flow {
        emit(DataResource.Loading())
        val result = userService.loginUser(user)
        if(result.message != null){
            Log.d("User Login",result.message)
            result.let {
                if(result.message != "") {
                    userPreference.edit().putString("token", user.email).apply()
                    emit(DataResource.Success(UserRegisterState(userRegisterSuccess = true, isLoading = false)))
                }
            }
        }

    }.catch {
        emit(DataResource.Error(it.message.toString()))
        Log.d("User Login",it.message.toString())
    }


    suspend fun loginUser(user: TemporaryUser,context: Context) {

        _loginUser(user,context).onEach {
            when(it){
                is DataResource.Error -> _userRegister.value = UserRegisterState(userRegisterError = it.message)
                is DataResource.Loading -> _userRegister.value = UserRegisterState(isLoading = true)
                is DataResource.Success -> _userRegister.value = UserRegisterState(userRegisterSuccess = true, isLoading = false)
            }
        }.collect()

    }



}