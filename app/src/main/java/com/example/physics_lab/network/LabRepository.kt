package com.example.physics_lab.network

import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.data.SingleClassResponse
import com.example.physics_lab.data.ClassUserData

object LabRepository : FuelNetworkService() {
    suspend fun loadLabsForTeacher(token: String, classId: String): SingleClassResponse {
        return SingleClassResponse(getWithToken( "Teacher/class/${classId}",ClassRoomItem::class.java, token))
    }
    suspend fun loadLabsForStudent(token: String, classId: String): SingleClassResponse {
        return SingleClassResponse(getWithToken( "Student/class/${classId}", ClassRoomItem::class.java, token))
    }

    suspend fun loadMoreLabsForTeacher(token: String, classId: String): ClassUserData? {
        return getWithToken( "Teacher/class/${classId}",ClassUserData::class.java, token)
    }
    suspend fun loadMoreLabsForStudent(token: String, classId: String): ClassUserData? {
        return getWithToken( "Student/class/${classId}", ClassUserData::class.java, token)
    }
    suspend fun loadRawLabsForTeacher(token: String, classId: String): ClassUserData? {
        return getWithToken( "Teacher/class/${classId}",ClassUserData::class.java, token)
    }

    suspend fun deleteLabTeacher(token: String, labId: String): Boolean? {
        return deleteWithToken( "Teacher/lab/$labId",Boolean::class.java, token)
    }
}