package com.example.physics_lab.ui.lab_list

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.data.ClassUserData
import com.example.physics_lab.data.LabListData
import com.example.physics_lab.network.ClassRepository
import com.example.physics_lab.network.LabRepository
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.service.ClassService
import com.example.physics_lab.ui._base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LabListViewModel(context: Context) : BaseViewModel() {
    private val authService = AuthService(context)
    private val classService = ClassService(context)
    val classData = MutableLiveData<ClassUserData>()
    val removeResponse = MutableLiveData<Unit>()
    val lostConnect = MutableLiveData<Boolean>()

    fun loadTeacherClass() {
        val token = authService.token
        val classId = classService.classId
        if (token != null && classId != null) {
            scopeMain.launch {
                val response = withContext(Dispatchers.IO) {
                    LabRepository.loadMoreLabsForTeacher(token, classId)
                }
                if(response == null){
                    lostConnect.value = true
                    apiExceptionData.value = "Невозможно получить запрос"
                }
                else {
                    lostConnect.value = false
                    classData.value = response
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
                    lostConnect.value = true
                    apiExceptionData.value = "Невозможно получить запрос"
                }
                else {
                    lostConnect.value = false
                    classData.value = response
                }
            }
        }
    }

//    fun loadTeacherClass() {
//        val token = authService.token
//        val classId = classService.classId
//        if (token != null && classId != null) {
//            scopeMain.launch {
//                val response = withContext(Dispatchers.IO) {
//                    LabRepository.loadMoreLabsForTeacher(token, classId)
//                }
//
//                if (response.singleItem != null && response.Message == null) {
//                    lostConnect.value = false
//                    classData.value = response.singleItem
//                } else if (response.Message != null) {
//                    apiExceptionData.value = response.Message
//                }
//                if (response.singleItem == null) {
//                    lostConnect.value = true
//                }
//            }
//        }
//    }


//    fun loadStudentClass() {
//        val token = authService.token
//        val classId = classService.classId
//        if (token != null && classId != null) {
//            scopeMain.launch {
//                val response = withContext(Dispatchers.IO) {
//                    LabRepository.loadLabsForStudent(token, classId)
//                }
//                if (response.singleItem != null && response.Message == null) {
//                    lostConnect.value = false
//                    classData.value = response.singleItem
//                } else if (response.Message != null) {
//                    apiExceptionData.value = response.Message
//                }
//                if (response.singleItem == null) {
//                    lostConnect.value = true
//                }
//            }
//        }
//    }

    fun removeClass() {
        val token = authService.token ?: return
        val classId = classService.classId ?: return
        scopeMain.launch {
            val response = withContext(Dispatchers.IO) {
                ClassRepository.deleteClass(token, classId)
            }
            if (response != true) {
                apiExceptionData.value = "Что-то пошло не так. Попробуйте ещё раз."
            } else {
                removeResponse.value = Unit
            }
        }
    }
}