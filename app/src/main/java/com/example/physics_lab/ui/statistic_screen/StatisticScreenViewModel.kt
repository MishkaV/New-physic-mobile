package com.example.physics_lab.ui.statistic_screen

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.data.ClassUserData
import com.example.physics_lab.network.ClassRepository
import com.example.physics_lab.network.LabRepository
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.service.ClassService
import com.example.physics_lab.ui._base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class StatisticScreenViewModel(context: Context) : BaseViewModel()  {
    private val authService = AuthService(context)
    private val classService = ClassService(context)
    val classData = MutableLiveData<List<ClassRoomItem>>()
    val singleClassData = MutableLiveData<ClassUserData>()


    fun loadTeacherClasses() {
        val token = authService.token
        if (token != null) {
            scopeMain.launch {
                val response = withContext(Dispatchers.IO) {
                    ClassRepository.loadClassesForTeacher(token)
                }
                Timber.i("response $response")
                if (response != null) {
                    classData.value = response
                }
            }
        }

    }

    fun loadStudentClasses() {
        val token = authService.token
        if (token != null) {
            scopeMain.launch {
                val response = withContext(Dispatchers.IO) {
                    ClassRepository.loadClassesForStudent(token)
                }
                Timber.i("reponse $response")
                if (response != null) {
                    classData.value = response
                }
            }
        }
    }

    fun loadTeacherClass() {
        val token = authService.token
        val classId = classService.classId
        if (token != null && classId != null) {
            scopeMain.launch {
                val response = withContext(Dispatchers.IO) {
                    LabRepository.loadMoreLabsForTeacher(token, classId)
                }
                if(response == null){
                    apiExceptionData.value = "Невозможно получить запрос"
                }
                else {
                    singleClassData.value = response
                }
            }
        }
    }


    fun loadStudentClass() {
        val token = authService.token
        val classId = classService.classId
        if (token != null && classId != null) {
            scopeMain.launch {
                val response = withContext(Dispatchers.IO) {
                    LabRepository.loadMoreLabsForStudent(token, classId)
                }
                if(response == null){
                    apiExceptionData.value = "Невозможно получить запрос"
                }
                else {
                    singleClassData.value = response
                }
            }
        }
    }
}