package com.example.physics_lab.data

data class ActiveFinishData(
    val activeSolutions: List<ActiveSolution>,
    val finishedSolutions: List<FinishedSolution>
) {
    data class ActiveSolution(
        val userId: Int,
        val solution: String,
        val lab: ClassUserData.Lab,
        val grade: Int,
        val videoPath: String,
        val timeSpan: String,
        val status: Int,
        val dateOfDownload: String
    )

    data class FinishedSolution(
        val userId: Int,
        val solution: String,
        val lab: ClassUserData.Lab,
        val grade: Int,
        val videoPath: String,
        val timeSpan: String,
        val status: Int,
        val dateOfDownload: String
    )
}