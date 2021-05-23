package com.example.physics_lab.service

import android.content.Context
import android.content.SharedPreferences
import com.example.physics_lab.utils.SharedPrefConstants

class SolutionService (context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        SharedPrefConstants.NAME,
        Context.MODE_PRIVATE
    )

    var checkedUserId :String? = null
    var name: String? = null
    var result: String? = null
    var dateOfDownload: String? = null
    var videoPath: String? = null
        private set

    init {
        loadDataFromLocal()
    }

    fun saveCheckedUserId(newCheckedUserId: String) {
        checkedUserId = newCheckedUserId
        sharedPreferences.edit()
            .putString(SharedPrefConstants.SOLUTION_USER_ID, newCheckedUserId)
            .apply()
    }


    fun saveName(newName: String) {
        name = newName
        sharedPreferences.edit()
            .putString(SharedPrefConstants.SOLUTION_NAME, newName)
            .apply()
    }

    fun saveResult(newResult: String) {
        result = newResult
        sharedPreferences.edit()
            .putString(SharedPrefConstants.SOLUTION_RESULT, newResult)
            .apply()
    }

    fun saveDateOfDownload(newDateOfDownload: String) {
        dateOfDownload = newDateOfDownload
        sharedPreferences.edit()
            .putString(SharedPrefConstants.SOLUTION_DATE, newDateOfDownload)
            .apply()
    }

    fun saveVideoPath(newVideoPath: String) {
        videoPath = newVideoPath
        sharedPreferences.edit()
            .putString(SharedPrefConstants.SOLUTION_VIDEO, newVideoPath)
            .apply()
    }

    private fun loadDataFromLocal() {
        checkedUserId =
            if (sharedPreferences.contains(SharedPrefConstants.SOLUTION_USER_ID)) sharedPreferences.getString(
                SharedPrefConstants.SOLUTION_USER_ID,
                null
            ) else null
        name =
            if (sharedPreferences.contains(SharedPrefConstants.SOLUTION_NAME)) sharedPreferences.getString(
                SharedPrefConstants.SOLUTION_NAME,
                null
            ) else null
        result =
            if (sharedPreferences.contains(SharedPrefConstants.SOLUTION_RESULT)) sharedPreferences.getString(
                SharedPrefConstants.SOLUTION_RESULT,
                null
            ) else null
        dateOfDownload =
            if (sharedPreferences.contains(SharedPrefConstants.SOLUTION_DATE)) sharedPreferences.getString(
                SharedPrefConstants.SOLUTION_DATE,
                null
            ) else null
        videoPath =
            if (sharedPreferences.contains(SharedPrefConstants.SOLUTION_VIDEO)) sharedPreferences.getString(
                SharedPrefConstants.SOLUTION_VIDEO,
                null
            ) else null
    }

}