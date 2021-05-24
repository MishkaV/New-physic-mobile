package com.example.physics_lab.data

data class LabDescrData2(
    val id: Int,
    val title: String,
    val task: Task,
    val classRoomId: Int,
    val maxGrade: Int,
    val deadline: String,
    val solution: Solution
) {
    data class Task(
        val id: Int,
        val description: String,
        val recommendedClass: String,
        val equipment: String,
        val linkToManual: String,
        val name: String,
        val theme: String
    )

    data class Solution(
        val userId: Int,
        val solution: Any,
        val labId: Int,
        val grade: Int,
        val videoPath: Any,
        val timeSpan: Any,
        val status: Int,
        val dateOfDownload: Any
    )
}