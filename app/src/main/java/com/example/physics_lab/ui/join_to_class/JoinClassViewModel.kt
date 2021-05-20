package com.example.physics_lab.ui.join_to_class

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.network.ClassRepository
import com.example.physics_lab.network.JoinRepository
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.ui._base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JoinClassViewModel(context: Context) : BaseViewModel() {
    val classData = MutableLiveData<ClassRoomItem>()
    val code = MutableLiveData<String>()
    private val authService = AuthService(context)

    fun joinToClass(code: String) {
        val token = authService.token
        if (token != null) {
            scopeMain.launch {
                val response = withContext(Dispatchers.IO) {
                    ClassRepository.joinToClass(token, code)
                }
                if (response.singleItem != null && response.Message == null) {
                    classData.value = response.singleItem
                } else if (response.Message != null) {
                    apiExceptionData.value = response.Message
                }
            }
        }

    }
}