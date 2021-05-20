package com.example.physics_lab.data

data class ClassRoomItem(
    val code: String,
    val id: Int,
    val labs: List<Lab>?,
    val title: String,
    val userClasses: List<UserClass>
)