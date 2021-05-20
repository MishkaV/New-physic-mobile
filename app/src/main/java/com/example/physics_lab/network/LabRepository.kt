package com.example.physics_lab.network

import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.data.ClassesResponse
import com.example.physics_lab.data.SingleClassResponse
import com.example.physics_lab.utils.removeQuotes

object LabRepository : FuelNetworkService() {
    suspend fun loadLabsForTeacher(token: String, classId: String): SingleClassResponse {
        return SingleClassResponse(getWithToken( "Teacher/class/${classId}",ClassRoomItem::class.java, token))
    }
    suspend fun loadLabsForStudent(token: String, classId: String): SingleClassResponse {
        return SingleClassResponse(getWithToken( "Student/class/${classId}", ClassRoomItem::class.java, token))
    }
}