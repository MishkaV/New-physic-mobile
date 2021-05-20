package com.example.physics_lab.ui.auth.signin

import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.network.AuthRepository
import com.example.physics_lab.ui._base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel : BaseViewModel() {

    val password = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val tokenData = MutableLiveData<String>()

    var fieldsAreEmpty = true

    fun signIn(email: String, password: String) {
        scopeMain.launch {
            val response = withContext(Dispatchers.IO) {
                AuthRepository.signIn(email, password)
            }
            if (response != null) {
                tokenData.value = response
            } else {
                apiExceptionData.value = "Что-то не так, проверьте корректность данных и подключение к интернету."
            }
        }
    }
}