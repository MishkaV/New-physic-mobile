package com.example.physics_lab.service

import android.content.Context
import android.content.SharedPreferences
import com.auth0.android.jwt.JWT
import com.example.physics_lab.utils.SharedPrefConstants

class StatisticService(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        SharedPrefConstants.NAME,
        Context.MODE_PRIVATE
    )

    var prevLayout: String? = null

        private set

    init {
        loadDataFromLocal()
    }

    fun savePrevLayout(newPrevLayout: String) {
        prevLayout = newPrevLayout
        sharedPreferences.edit()
            .putString(SharedPrefConstants.STATIC_PREV_LAYOUT, newPrevLayout)
            .apply()
    }

    private fun loadDataFromLocal() {
        prevLayout =
            if (sharedPreferences.contains(SharedPrefConstants.STATIC_PREV_LAYOUT)) sharedPreferences.getString(
                SharedPrefConstants.STATIC_PREV_LAYOUT,
                null
            ) else null
    }

}