package com.example.physics_lab.ui.user_list.add_user

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.network.ClassRepository
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.service.ClassService
import com.example.physics_lab.ui._base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddUserTeacherViewModel (context: Context) : BaseViewModel() {
    val email = MutableLiveData<String>()
    private val authService = AuthService(context)
    private val classService = ClassService(context)
    val postUserResponse = MutableLiveData<Unit>()

    fun joinStudentToClass(email: String) {
        val token = authService.token ?: return
        val classId = classService.classId ?: return
        Log.d("TAG",token)
        Log.d("TAG",classId)
        Log.d("TAG",email)
            scopeMain.launch {
                val response = withContext(Dispatchers.IO) {
                    ClassRepository.postUserToClass(token, classId, email)
                }
                Log.d("TAG", response.toString())
                if (response != true) {
                    apiExceptionData.value = "Что-то пошло не так. Попробуйте ещё раз."
                } else {
                    postUserResponse.value = Unit
                }
            }

    }
}