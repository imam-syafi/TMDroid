package com.edts.tmdroid.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.AuthRepository
import com.edts.tmdroid.ui.model.LoginResult
import com.zhuinden.eventemitter.EventEmitter
import com.zhuinden.eventemitter.EventSource
import com.zhuinden.livedatacombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    // Backing properties
    private val name = MutableLiveData<String>()
    private val isNameValid = MutableLiveData<Boolean>()
    private val password = MutableLiveData<String>()
    private val isPasswordValid = MutableLiveData<Boolean>()

    val state: LiveData<LoginState> = combineTuple(name, isNameValid, password, isPasswordValid)
        .map { (name, isNameValid, password, isPasswordValid) ->
            LoginState(
                name = name ?: "",
                isNameValid = isNameValid ?: false,
                password = password ?: "",
                isPasswordValid = isPasswordValid ?: false,
            )
        }

    private val emitter: EventEmitter<LoginResult> = EventEmitter()
    val event: EventSource<LoginResult> = emitter

    fun onNameChange(name: String, isNameValid: Boolean) {
        this.name.value = name
        this.isNameValid.value = isNameValid
    }

    fun onPasswordChange(password: String, isPasswordValid: Boolean) {
        this.password.value = password
        this.isPasswordValid.value = isPasswordValid
    }

    fun onSubmit(name: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.login(name, password)
            emitter.emit(result)
        }
    }

    fun onRegister(name: String, password: String) {
        viewModelScope.launch {
            authRepository.register(name, password)
            onSubmit(name, password)
        }
    }
}
