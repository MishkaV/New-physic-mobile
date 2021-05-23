package com.example.physics_lab.ui.lab_description

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.data.*
import com.example.physics_lab.network.ClassRepository
import com.example.physics_lab.network.LabRepository
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.service.ClassService
import com.example.physics_lab.service.LabService
import com.example.physics_lab.ui._base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class LabDescriptionViewModel (context: Context) : BaseViewModel() {
    private val authService = AuthService(context)
    private val labService = LabService(context)
    val labDescrData = MutableLiveData<Lab>()

    fun loadClassDescrStudent() {
        val token = authService.token ?: return
        val labId = labService.labId ?: return
            scopeMain.launch {
                val response = withContext(Dispatchers.IO) {
                    ClassRepository.loadLabDescr(token, labId)
                }
                if(response == null){
                    apiExceptionData.value = "Невозможно получить запрос"
                }
                else {
                    labDescrData.value = response
                }
            }
        }
}