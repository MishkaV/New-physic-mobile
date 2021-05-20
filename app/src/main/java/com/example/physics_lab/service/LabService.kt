package com.example.physics_lab.service

import android.content.Context
import android.content.SharedPreferences
import com.auth0.android.jwt.JWT
import com.example.physics_lab.utils.SharedPrefConstants

class LabService (context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        SharedPrefConstants.NAME,
        Context.MODE_PRIVATE
    )

    var labId: String? = null
    var description: String? = null
    var equipment: String? = null
    var name: String? = null
    var theme: String? = null
    var videoPath: String? = null
        private set

    init {
        loadDataFromLocal()
    }

    fun saveLabId(newLabId: String) {
        labId = newLabId
        sharedPreferences.edit()
            .putString(SharedPrefConstants.LAB_ID, newLabId)
            .apply()
    }

    fun saveDescription(newDescription: String) {
        description = newDescription
        sharedPreferences.edit()
            .putString(SharedPrefConstants.LAB_DESCRIPTION, newDescription)
            .apply()
    }

    fun saveEquipment(newEquipment: String) {
        equipment = newEquipment
        sharedPreferences.edit()
            .putString(SharedPrefConstants.LAB_EQUIP, newEquipment)
            .apply()
    }

    fun saveName(newName: String) {
        name = newName
        sharedPreferences.edit()
            .putString(SharedPrefConstants.LAB_NAME, newName)
            .apply()
    }

    fun saveTheme(newTheme: String) {
        theme = newTheme
        sharedPreferences.edit()
            .putString(SharedPrefConstants.LAB_THEME, newTheme)
            .apply()
    }

    fun saveVideoPath(newVideoPath: String) {
        videoPath = newVideoPath
        sharedPreferences.edit()
            .putString(SharedPrefConstants.LAB_VIDEO, newVideoPath)
            .apply()
    }

    private fun loadDataFromLocal() {
        labId =
            if (sharedPreferences.contains(SharedPrefConstants.LAB_ID)) sharedPreferences.getString(
                SharedPrefConstants.LAB_ID,
                null
            ) else null
        description =
            if (sharedPreferences.contains(SharedPrefConstants.LAB_DESCRIPTION)) sharedPreferences.getString(
                SharedPrefConstants.LAB_DESCRIPTION,
                null
            ) else null
        equipment =
            if (sharedPreferences.contains(SharedPrefConstants.LAB_EQUIP)) sharedPreferences.getString(
                SharedPrefConstants.LAB_EQUIP,
                null
            ) else null
        name =
            if (sharedPreferences.contains(SharedPrefConstants.LAB_NAME)) sharedPreferences.getString(
                SharedPrefConstants.LAB_NAME,
                null
            ) else null
        theme =
            if (sharedPreferences.contains(SharedPrefConstants.LAB_THEME)) sharedPreferences.getString(
                SharedPrefConstants.LAB_THEME,
                null
            ) else null
        videoPath =
            if (sharedPreferences.contains(SharedPrefConstants.LAB_VIDEO)) sharedPreferences.getString(
                SharedPrefConstants.LAB_VIDEO,
                null
            ) else null
    }

}