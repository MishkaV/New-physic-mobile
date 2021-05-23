package com.example.physics_lab.data

data class SolutionItemData(
    val userId: Int,
    val solution: String,
    val labId: Int,
    val grade: Int,
    val videoPath: String,
    val timeSpan: String,
    val status: Int,
    val dateOfDownload: String
)

data class SetMarkData(
    val grade: Int,
    val status: Int
)