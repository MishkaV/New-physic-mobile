package com.example.physics_lab.ui.solved_works

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.data.ActiveFinishData
import com.example.physics_lab.data.ActiveSolutionData
import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.data.Lab
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

class SolvedWorksViewModel (context: Context) : BaseViewModel() {
    private val authService = AuthService(context)
    private val labService = LabService(context)
    private val classService = ClassService(context)
    val activeSolution = MutableLiveData<ActiveSolutionData>()
    val classData = MutableLiveData<ClassRoomItem>()
    val removeResponse = MutableLiveData<Unit>()

    fun loadActiveSolution() {
        val token = authService.token ?: return
        val labId = labService.labId ?: return
        scopeMain.launch {
            val response = withContext(Dispatchers.IO) {
                ClassRepository.loadActiveSolution(token, labId)
            }
            if(response == null){
                apiExceptionData.value = "Невозможно получить запрос"
            }
            else {
                activeSolution.value = response
            }
        }
    }

    fun loadTeacherClass() {
        val token = authService.token
        val classId = classService.classId
        if (token != null && classId != null) {
            scopeMain.launch {
                val response = withContext(Dispatchers.IO) {
                    LabRepository.loadLabsForTeacher(token, classId)
                }
                if (response.singleItem != null && response.Message == null) {
                    classData.value = response.singleItem
                } else if (response.Message != null) {
                    apiExceptionData.value = response.Message
                }
            }
        }
    }

    fun deleteLab() {
        val token = authService.token ?: return
        val labId = labService.labId ?: return
            scopeMain.launch {
                val response = withContext(Dispatchers.IO) {
                    LabRepository.deleteLabTeacher(token, labId)
                }
                if (response != true) {
                    apiExceptionData.value = "Что-то пошло не так. Попробуйте ещё раз."
                } else {
                    removeResponse.value = Unit
                }
            }
        }

}