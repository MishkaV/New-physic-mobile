package com.example.physics_lab.ui.user_list

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.data.ClassUserData
import com.example.physics_lab.network.ClassRepository
import com.example.physics_lab.network.LabRepository
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.service.ClassService
import com.example.physics_lab.ui._base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserListViewModel(context: Context) : BaseViewModel(){
    private val authService = AuthService(context)
    private val classService = ClassService(context)
    val classData = MutableLiveData<ClassUserData>()
    val removeResponse = MutableLiveData<Unit>()

    fun loadTeacherClass() {
        val token = authService.token
        val classId = classService.classId
        if (token != null && classId != null) {
            scopeMain.launch {
                val response = withContext(Dispatchers.IO) {
                    LabRepository.loadRawLabsForTeacher(token, classId)
                }
                if(response == null){
                    apiExceptionData.value = "Невозможно получить запрос"
                }
                else {
                    classData.value = response
                }
            }
        }
    }

    fun deleteStudentFromClass(userId : String) {
        val token = authService.token ?: return
        val classId = classService.classId ?: return
            scopeMain.launch {
                val response = withContext(Dispatchers.IO) {
                    ClassRepository.deleteUserFromClass(token, classId, userId)
                }
                if (response != true) {
                    apiExceptionData.value = "Что-то пошло не так. Попробуйте ещё раз."
                } else {
                    removeResponse.value = Unit
                }
            }
    }


}