package com.example.physics_lab.ui.lab_list.active_lab

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.data.ActiveFinishData
import com.example.physics_lab.network.ClassRepository
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.ui._base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActiveLabsViewModel (context: Context) : BaseViewModel() {
    private val authService = AuthService(context)
    val activeFinishData = MutableLiveData<ActiveFinishData>()

    fun loadActiveFinishLab() {
        val token = authService.token ?: return
        scopeMain.launch {
            val response = withContext(Dispatchers.IO) {
                ClassRepository.loadActiveFinishLab(token)
            }
            if(response == null){
                apiExceptionData.value = "Невозможно получить запрос"
            }
            else {
                activeFinishData.value = response
            }
        }
    }
}