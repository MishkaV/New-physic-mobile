package com.example.physics_lab.ui.lab_description

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.physics_lab.ui.lab_list.LabListViewModel

class LabDescriptionViewModelFactory (private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LabDescriptionViewModel(context) as T
    }
}