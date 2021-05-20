package com.example.physics_lab.ui.auth.registration.reg_full_name

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.ui._base.BaseViewModel

class RegistrationFullNameViewModel  : BaseViewModel(){
    val name = MutableLiveData<String>()
    val surname = MutableLiveData<String>()
    val patronymic = MutableLiveData<String>()

    var fieldsAreEmpty = true
}