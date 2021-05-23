package com.example.physics_lab.ui.solved_works.set_marks

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.physics_lab.ui.solved_works.SolvedWorksViewModel

@Suppress("UNCHECKED_CAST")
class SetMarkViewModelFactory (private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SetMarkViewModel(context) as T
    }
}