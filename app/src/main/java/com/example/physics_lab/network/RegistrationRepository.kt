package com.example.physics_lab.network

object RegistrationRepository : FuelNetworkService() {
    data class Parameters(val email: String, val password: String, val name : String)

    suspend fun registrationStudent(email: String, password: String, name : String): String? {
        return postWithJsonStringResult("Auth/RegisterStudent", Parameters(email, password, name))
    }


    suspend fun registrationTeacher(email: String, password: String, name : String): String? {
        return postWithJsonStringResult("Auth/RegisterTeacher", Parameters(email, password, name))
    }
}