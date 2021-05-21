package com.example.physics_lab.ui.lab_list.finish_lab

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class FinishLabViewModelFactory (private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FinishLabViewModel(context) as T
    }
}
