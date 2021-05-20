package com.example.physics_lab.network

import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.data.SingleClassResponse

object JoinRepository : FuelNetworkService() {
    suspend fun joinClass(token: String, code: String): SingleClassResponse {
        return SingleClassResponse(
            JoinRepository.getWithToken(
                "Student/class/{code}${code}",
                ClassRoomItem::class.java,
                token
            )
        )
    }
}