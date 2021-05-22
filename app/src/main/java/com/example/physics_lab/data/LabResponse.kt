package com.example.physics_lab.data

data class LabTaskResponse(val tasks: List<LabTask>?) : GeneralNetWorkResponse()


data class SingleLabResponse(val lab: Lab?) : GeneralNetWorkResponse()

data class Lab(
    val classRoomId: Int,
    val deadline: String,
    val id: Int,
    val maxGrade: Int,
    val solutionLabs: List<SolutionLab>,
    val taskLabId: Int,
    val title: String,
    val task : LabTask
)

data class LabTask(
    val id: Int,
    val description: String?,
    val equipment: String?,
    val linkToManual: String?,
    val recommendedClass: String?,
    val name: String,
    val theme: String
)

data class LabDescrData(
    val id: Int,
    val title: String,
    val task: Task,
    val classRoomId: Int,
    val maxGrade: Int,
    val deadline: String,
    val solution: Solution?
) {
    data class Task(
        val id: Int,
        val description: String,
        val recommendedClass: String?,
        val equipment: String,
        val name: String,
        val theme: String
    )

    data class Solution(
        val userId: Int,
        val solution: String,
        val labId: Int,
        val grade: Int,
        val videoPath: String,
        val timeSpan: String,
        val status: Int,
        val dateOfDownload: String
    )
}