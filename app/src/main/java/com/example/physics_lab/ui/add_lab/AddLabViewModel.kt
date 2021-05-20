package com.example.physics_lab.ui.add_lab

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.data.Lab
import com.example.physics_lab.data.LabTask
import com.example.physics_lab.network.ClassRepository
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.ui._base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class AddLabViewModel(context: Context) : BaseViewModel() {
    private val authService = AuthService(context)

    val labTaskData = MutableLiveData<List<LabTask>>()
    val newLabData = MutableLiveData<Lab>()
    var deadlineDate: String? = null
    fun loadLabTasks() {
        val token = authService.token ?: return
        scopeMain.launch {
            val response = withContext(Dispatchers.IO) {
                ClassRepository.loadLabTasks(token)
            }
            if (response?.tasks == null) {
                apiExceptionData.value = "Something went wrong"
            } else {
                labTaskData.value = response.tasks
            }
        }
    }

    fun assignLab(title: String, taskLabId: Int, classRoomId: Int, deadline : String) {
        val token = authService.token ?: return
        scopeMain.launch {
            val response = withContext(Dispatchers.IO) {
                ClassRepository.assignLab(token, title, taskLabId, classRoomId, deadline = deadline)
            }
            Timber.i("assign lab response $response")
            if (response.lab != null) {
                newLabData.value = response.lab
            } else if (response.Message != null) {
                apiExceptionData.value = response.Message
            }
        }
    }
}