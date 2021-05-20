package com.example.physics_lab.ui.class_list

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.network.ClassRepository
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.ui._base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ClassListViewModel(context: Context) : BaseViewModel() {
    private val authService = AuthService(context)
    val classData = MutableLiveData<List<ClassRoomItem>>()

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
}