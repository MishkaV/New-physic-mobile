package com.example.physics_lab.network

object AuthRepository : FuelNetworkService() {
    data class Parameters(val email: String, val password: String)

    suspend fun signIn(email: String, password: String): String? {
        return postWithJsonStringResult("Auth/Login", Parameters(email, password))
    }

}