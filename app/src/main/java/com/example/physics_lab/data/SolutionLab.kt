package com.example.physics_lab.data

data class SolutionLab(
    val dateOfDownload: String,
    val grade: Int,
    val labId: Int,
    val solution: String,
    val status: Int,
    val timeSpan: String,
    val userId: Int,
    val videoPath: String
)