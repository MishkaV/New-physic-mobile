package com.example.physics_lab.ui.lab_list.active_lab

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.physics_lab.ui.lab_list.finish_lab.FinishLabViewModel

@Suppress("UNCHECKED_CAST")
class ActiveLabsViewModelFactory (private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ActiveLabsViewModel(context) as T
    }
}
