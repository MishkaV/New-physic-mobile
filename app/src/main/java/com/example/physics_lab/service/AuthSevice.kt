package com.example.physics_lab.service

import android.content.Context
import android.content.SharedPreferences
import com.auth0.android.jwt.JWT
import com.example.physics_lab.utils.SharedPrefConstants

class AuthService(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        SharedPrefConstants.NAME,
        Context.MODE_PRIVATE
    )

    init {
        loadDataFromLocal()
    }

    var role: String? = null

    var token: String? = null
        private set


    fun saveToken(newToken: String) {
        token = newToken
        sharedPreferences.edit()
            .putString(SharedPrefConstants.USER_TOKEN, newToken)
            .apply()
    }

    fun saveRole(newRole: String) {
        role = newRole
        sharedPreferences.edit()
            .putString(SharedPrefConstants.USER_ROLE, newRole)
            .apply()
    }

    fun getRole() {
        role =
            if (sharedPreferences.contains(SharedPrefConstants.USER_ROLE))
                sharedPreferences.getString(
                    SharedPrefConstants.USER_ROLE,
                    null
                )
            else null
    }

    private fun loadDataFromLocal() {
        token =
            if (sharedPreferences.contains(SharedPrefConstants.USER_TOKEN)) sharedPreferences.getString(
                SharedPrefConstants.USER_TOKEN,
                null
            ) else null
        role =
            if (sharedPreferences.contains(SharedPrefConstants.USER_ROLE))
                sharedPreferences.getString(
                    SharedPrefConstants.USER_ROLE,
                    null
                )
            else null
    }

    fun extractJwt(jwt: String): Map<String, Any> {
        val token = JWT(jwt)
        val header = token.header.toString()
        val payload = token.claims
        return mapOf("header" to header, "payload" to payload)
    }

    fun logout() {
        token = null
        role = null
        sharedPreferences.edit()
            .clear()
            .apply()
    }

}