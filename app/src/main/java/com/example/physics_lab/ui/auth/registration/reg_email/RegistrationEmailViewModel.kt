package com.example.physics_lab.ui.auth.registration.reg_email

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.ui._base.BaseViewModel

class RegistrationEmailViewModel : BaseViewModel(){
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    var fieldsAreEmpty = true
}