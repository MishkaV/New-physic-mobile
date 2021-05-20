package com.example.physics_lab.ui.auth.registration.reg_role

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.network.AuthRepository
import com.example.physics_lab.network.RegistrationRepository
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.service.ClassService
import com.example.physics_lab.service.RegistrationService
import com.example.physics_lab.ui._base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationRoleViewModel (context: Context) : BaseViewModel(){
    private val registrationService = RegistrationService(context)
    private val authService = AuthService(context)
    var fieldsAreEmpty = true
    val tokenData = MutableLiveData<String>()


    fun registrationStudnet() {
        val email = registrationService.email
        val password = registrationService.password
        val name = registrationService.full_name
        if(email != null && password != null && name != null)
        {
            scopeMain.launch {
                val response = withContext(Dispatchers.IO) {
                    RegistrationRepository.registrationStudent(email, password, name)
                }
                if (response != null) {
                    tokenData.value = response
                } else {
                    apiExceptionData.value = "Somethin wrong. Please, try again."
                }
            }
        }
    }

    fun registrationTeacher() {
        val email = registrationService.email
        val password = registrationService.password
        val name = registrationService.full_name
        if(email != null && password != null && name != null)
        {
            scopeMain.launch {
                val response = withContext(Dispatchers.IO) {
                    RegistrationRepository.registrationTeacher(email, password, name)
                }
                if (response != null) {
                    tokenData.value = response
                } else {
                    apiExceptionData.value = "Somethin wrong. Please, try again."
                }
            }
        }
    }
}