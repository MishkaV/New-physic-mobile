package com.example.physics_lab.ui.user_list.add_user

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.physics_lab.ui.join_to_class.JoinClassViewModel

@Suppress("UNCHECKED_CAST")
class AddUserTeacherViewModelFactory (private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddUserTeacherViewModel(context) as T
    }
}