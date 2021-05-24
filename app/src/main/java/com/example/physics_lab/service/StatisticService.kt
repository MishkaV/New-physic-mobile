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
    var countClasses: Float? = null
    var countLabs: Float? = null
    var countUsersInClass: Float? = null
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

    fun saveCountClasses(newCountClasses: Float) {
        countClasses = newCountClasses
        sharedPreferences.edit()
            .putFloat(SharedPrefConstants.STATIC_COUNT_CLASSES, newCountClasses)
            .apply()
    }

    fun saveCountLabs(newCountLabs: Float) {
        countLabs= newCountLabs
        sharedPreferences.edit()
            .putFloat(SharedPrefConstants.STATIC_COUNT_LABS, newCountLabs)
            .apply()
    }

    fun saveCountUsersInClass(newCountUsersInClass: Float) {
        countUsersInClass= newCountUsersInClass
        sharedPreferences.edit()
            .putFloat(SharedPrefConstants.STATIC_COUNT_USERS, newCountUsersInClass)
            .apply()
    }

    private fun loadDataFromLocal() {
        prevLayout =
            if (sharedPreferences.contains(SharedPrefConstants.STATIC_PREV_LAYOUT)) sharedPreferences.getString(
                SharedPrefConstants.STATIC_PREV_LAYOUT,
                null
            ) else null
        countClasses =
            if (sharedPreferences.contains(SharedPrefConstants.STATIC_COUNT_CLASSES)) sharedPreferences.getFloat(
                SharedPrefConstants.STATIC_COUNT_CLASSES,
                0F
            ) else null
        countLabs =
            if (sharedPreferences.contains(SharedPrefConstants.STATIC_COUNT_LABS)) sharedPreferences.getFloat(
                SharedPrefConstants.STATIC_COUNT_LABS,
                0F
            ) else null
        countUsersInClass =
            if (sharedPreferences.contains(SharedPrefConstants.STATIC_COUNT_USERS)) sharedPreferences.getFloat(
                SharedPrefConstants.STATIC_COUNT_USERS,
                0F
            ) else null
    }

}