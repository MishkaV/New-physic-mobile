package com.example.physics_lab.ui.create_class

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.network.ClassRepository
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.ui._base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateClassViewModel(context: Context) : BaseViewModel() {
    private val authService = AuthService(context)

    val newClassData = MutableLiveData<ClassRoomItem>()
    val className = MutableLiveData<String>()


    fun createClass() {
        val token = authService.token ?: return
        val className = className.value ?: return
        scopeMain.launch {
            val response = withContext(Dispatchers.IO) {
                ClassRepository.createClass(token, className)
            }
            if (response.singleItem == null && response.Message != null) {
                apiExceptionData.value = response.Message
            } else if (response.singleItem != null) {
                newClassData.value = response.singleItem
            }
        }

    }
}