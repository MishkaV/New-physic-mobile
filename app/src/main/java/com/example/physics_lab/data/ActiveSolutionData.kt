package com.example.physics_lab.data

data class ActiveSolutionData(
    val id: Int,
    val title: String,
    val task: Task,
    val classRoomId: Int,
    val maxGrade: Int,
    val deadline: String,
    val solutions: List<Solution>
) {
    data class Task(
        val id: Int,
        val description: String,
        val recommendedClass: String,
        val equipment: String,
        val linkToManual: String,
        val name: String,
        val theme: String,
        val correctSolution: String
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