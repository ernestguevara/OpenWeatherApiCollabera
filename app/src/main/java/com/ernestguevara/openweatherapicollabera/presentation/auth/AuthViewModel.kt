package com.ernestguevara.openweatherapicollabera.presentation.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernestguevara.openweatherapicollabera.domain.repository.AuthRepository
import com.ernestguevara.openweatherapicollabera.util.Constants.ERROR_INPUTS
import com.ernestguevara.openweatherapicollabera.util.RequestState
import com.ernestguevara.openweatherapicollabera.util.Resource
import com.ernestguevara.openweatherapicollabera.util.isNotNullOrNotEmpty
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    private val _state: MutableLiveData<RequestState> = MutableLiveData(null)
    val state: MutableLiveData<RequestState> = _state

    private val _loginData = MutableLiveData<FirebaseUser?>()
    val loginData: MutableLiveData<FirebaseUser?> = _loginData

    private val _signupData = MutableLiveData<FirebaseUser?>()
    val signupData: MutableLiveData<FirebaseUser?> = _signupData

    private val _errorData = MutableLiveData<String>()
    val errorData: MutableLiveData<String> = _errorData

    val currentUser: FirebaseUser?
        get() = repository.currentUser

    init {
        if (repository.currentUser != null) {
            _loginData.value = repository.currentUser
        }
    }

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        _state.postValue(RequestState.Loading)
        if (email.isNotNullOrNotEmpty() && password.isNotNullOrNotEmpty()) {
            val result = repository.login(email, password)

            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        _loginData.postValue(it)
                        _state.postValue(RequestState.Finished)
                    }
                }

                is Resource.Error -> {
                    result.message?.let {
                        _errorData.postValue(it)
                        _state.postValue(RequestState.Failed)
                    }
                }

                is Resource.Loading -> {
                    _state.postValue(RequestState.Loading)
                }
            }
        } else {
            _state.postValue(RequestState.Failed)
            _errorData.postValue(ERROR_INPUTS)
        }
    }

    fun signupUser(email: String, password: String) = viewModelScope.launch {
        _state.postValue(RequestState.Loading)
        if (email.isNotNullOrNotEmpty() && password.isNotNullOrNotEmpty()) {
            val result = repository.signup(email, password)

            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        _signupData.postValue(it)
                    }
                }

                is Resource.Error -> {
                    result.message?.let {
                        _errorData.postValue(it)
                    }
                }

                is Resource.Loading -> {
                    _state.postValue(RequestState.Loading)
                }

            }
        } else {
            _state.postValue(RequestState.Finished)
            _errorData.postValue(ERROR_INPUTS)
        }
    }

    fun logout() {
        repository.logout()
        _loginData.value = null
        _signupData.value = null
    }

}