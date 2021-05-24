package com.example.physics_lab.data

data class LabListData(
    val id: Int,
    val title: String,
    val code: String,
    val users: List<User>,
    val labs: List<Lab>
) {
    data class User(
        val id: Int,
        val name: String,
        val email: String,
        val isTeacher: Boolean
    )

    data class Lab(
        val id: Int,
        val title: String,
        val taskLabId: Int,
        val classRoomId: Int,
        val maxGrade: Int,
        val deadline: String,
        val solutionLabs: List<SolutionLab>
    ) {
        data class SolutionLab(
            val userId: Int,
            val solution: Any,
            val labId: Int,
            val grade: Any,
            val videoPath: Any,
            val timeSpan: Any,
            val status: Int,
            val dateOfDownload: Any
        )
    }
}