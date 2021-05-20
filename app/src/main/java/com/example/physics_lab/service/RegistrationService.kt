package com.example.physics_lab.service

import android.content.Context
import android.content.SharedPreferences
import com.auth0.android.jwt.JWT
import com.example.physics_lab.utils.SharedPrefConstants

class RegistrationService(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        SharedPrefConstants.NAME,
        Context.MODE_PRIVATE
    )

    var email: String? = null
    var password: String? = null
    var full_name: String? = null
    var role: String? = null
        private set

    init {
        loadDataFromLocal()
    }

    fun saveEmail(newEmail: String) {
        email = newEmail
        sharedPreferences.edit()
            .putString(SharedPrefConstants.REGISTRATION_EMAIL, newEmail)
            .apply()
    }

    fun savePassword(newPassword: String) {
        password = newPassword
        sharedPreferences.edit()
            .putString(SharedPrefConstants.REGISTRATION_PASSWORD, newPassword)
            .apply()
    }

    fun saveFullName(newFullName: String) {
        full_name = newFullName
        sharedPreferences.edit()
            .putString(SharedPrefConstants.REGISTRATION_FULL_NAME, newFullName)
            .apply()
    }

    fun saveRole(newRole: String) {
        role = newRole
        sharedPreferences.edit()
            .putString(SharedPrefConstants.REGISTRATION_ROLE, newRole)
            .apply()
    }

    private fun loadDataFromLocal() {
        email =
            if (sharedPreferences.contains(SharedPrefConstants.REGISTRATION_EMAIL)) sharedPreferences.getString(
                SharedPrefConstants.REGISTRATION_EMAIL,
                null
            ) else null
        password =
            if (sharedPreferences.contains(SharedPrefConstants.REGISTRATION_PASSWORD)) sharedPreferences.getString(
                SharedPrefConstants.REGISTRATION_PASSWORD,
                null
            ) else null
        full_name =
            if (sharedPreferences.contains(SharedPrefConstants.REGISTRATION_FULL_NAME)) sharedPreferences.getString(
                SharedPrefConstants.REGISTRATION_FULL_NAME,
                null
            ) else null

        role =
            if (sharedPreferences.contains(SharedPrefConstants.REGISTRATION_ROLE)) sharedPreferences.getString(
                SharedPrefConstants.REGISTRATION_ROLE,
                null
            ) else null
    }

    fun logout() {
        email = null
        password = null
        full_name = null
        role = null
        sharedPreferences.edit()
            .clear()
            .apply()
    }
}