package com.example.physics_lab.service

import android.content.Context
import android.content.SharedPreferences
import com.auth0.android.jwt.JWT
import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.utils.SharedPrefConstants

class ClassService (context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        SharedPrefConstants.NAME,
        Context.MODE_PRIVATE
    )

    var classId: String? = null
    var title: String? = null
    var code: String? = null
        private set

    init {
        loadDataFromLocal()
    }

    fun saveClassId(newClassId: String) {
        classId = newClassId
        sharedPreferences.edit()
            .putString(SharedPrefConstants.CLASS_ID, newClassId)
            .apply()
    }

    fun saveTitle(newTitle: String) {
        title = newTitle
        sharedPreferences.edit()
            .putString(SharedPrefConstants.CLASS_TITLE, newTitle)
            .apply()
    }

    fun saveCode(newCode: String) {
        code = newCode
        sharedPreferences.edit()
            .putString(SharedPrefConstants.CLASS_CODE, newCode)
            .apply()
    }

    private fun loadDataFromLocal() {
        classId =
            if (sharedPreferences.contains(SharedPrefConstants.CLASS_ID)) sharedPreferences.getString(
                SharedPrefConstants.CLASS_ID,
                null
            ) else null
        title =
            if (sharedPreferences.contains(SharedPrefConstants.CLASS_TITLE)) sharedPreferences.getString(
                SharedPrefConstants.CLASS_TITLE,
                null
            ) else null
        code =
            if (sharedPreferences.contains(SharedPrefConstants.CLASS_CODE)) sharedPreferences.getString(
                SharedPrefConstants.CLASS_CODE,
                null
            ) else null
    }


    fun extractJwt(jwt: String): Map<String, Any> {
        val token = JWT(jwt)
        val header = token.header.toString()
        val payload = token.claims
        return mapOf("header" to header, "payload" to payload)
    }
}