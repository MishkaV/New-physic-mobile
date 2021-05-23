package com.example.physics_lab.ui.solved_works

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.physics_lab.ui.lab_description.LabDescriptionViewModel

@Suppress("UNCHECKED_CAST")
class SolvedWorksViewModelFactory (private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SolvedWorksViewModel(context) as T
    }
}