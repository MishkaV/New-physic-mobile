package com.example.physics_lab.network

import com.example.physics_lab.data.*
import com.example.physics_lab.utils.removeQuotes
import timber.log.Timber

object ClassRepository : FuelNetworkService() {

    private data class CreateClassesParams(val title: String)

    private data class AssignLabParams(
        val title: String,
        val taskLabId: Int,
        val classRoomId: Int,
        val maxGrade: Int,
        val deadline: String
    )


    //STUDENT API
    suspend fun loadClassesForStudent(token: String): ClassesResponse? {
        return getWithToken("Student/class", ClassesResponse::class.java, token)
    }

    suspend fun joinToClass(token: String, code: String): SingleClassResponse {
        return SingleClassResponse(
            putWithToken(
                "Student/class/${code}",
                ClassRoomItem::class.java,
                token
            )
        )
    }

    suspend fun loadLabDescr(token: String, labId: String): Lab? {
        return getWithToken("Student/lab/${labId}", Lab::class.java, token)
    }

    suspend fun loadActiveFinishLab(token: String): ActiveFinishData? {
        return getWithToken("Student/solution", ActiveFinishData::class.java, token)
    }
    //TEACHER API
    suspend fun createClass(token: String, name: String): SingleClassResponse {
        return SingleClassResponse(
            postWithParamsAndToken(
                "Teacher/class",
                ClassRoomItem::class.java,
                CreateClassesParams(name),
                token
            )
        )
    }

    suspend fun loadClassesForTeacher(token: String): ClassesResponse? {
        return getWithToken("Teacher/class", ClassesResponse::class.java, token)
    }

    suspend fun deleteClass(token: String, classId: String): Boolean? {
        return deleteWithToken("Teacher/class/$classId", Boolean::class.java, token)
    }

    suspend fun deleteUserFromClass(token: String, classId: String, deletedUserId: String): Boolean? {
        return deleteWithToken("Teacher/class/$classId/user/$deletedUserId", Boolean::class.java, token)
    }

    suspend fun postUserToClass(token: String, classId: String, addedUserEmail: String): Boolean? {
        return postWithToken("Teacher/class/$classId/user/$addedUserEmail", Boolean::class.java, token)
    }

    suspend fun loadLabTasks(token: String): LabTaskResponse? {
        return getWithToken("Teacher/taskLab", LabTaskResponse::class.java, token)
    }

    suspend fun assignLab(
        token: String,
        title: String,
        taskLabId: Int,
        classRoomId: Int,
        maxGrade: Int = 10,
        deadline: String
    ): SingleLabResponse {
        Timber.i("parameters ${AssignLabParams(title, taskLabId, classRoomId, maxGrade, deadline)}")
        return SingleLabResponse(
            postWithParamsAndToken(
                "Teacher/lab",
                Lab::class.java,
                AssignLabParams(title, taskLabId, classRoomId, maxGrade, deadline),
                token
            )
        )
    }

    suspend fun loadActiveSolution(token: String, labId: String): ActiveSolutionData? {
        return getWithToken("Teacher/lab/$labId/user", ActiveSolutionData::class.java, token)
    }

    suspend fun putMarkStudent(
        token : String,
        labId : String,
        grade : Int,
        checkedUserId : String,
        status : Int = 1
    ): SolutionItemData? {
        return putWithParamsAndToken(
            "Teacher/lab/$labId/user/$checkedUserId",
            SolutionItemData::class.java,
            SetMarkData(grade, status),
            token
        )
    }
}