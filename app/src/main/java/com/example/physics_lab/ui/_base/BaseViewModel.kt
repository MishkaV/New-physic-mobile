package com.example.physics_lab.ui._base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

open class BaseViewModel: ViewModel() {
    val scopeMain = CoroutineScope(Dispatchers.Main)
    val apiExceptionData = MutableLiveData<String>()
}