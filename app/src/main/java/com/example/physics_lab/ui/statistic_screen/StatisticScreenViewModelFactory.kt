package com.example.physics_lab.ui.statistic_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.physics_lab.ui.lab_list.LabListViewModel

@Suppress("UNCHECKED_CAST")
class StatisticScreenViewModelFactory (private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StatisticScreenViewModel(context) as T
    }
}