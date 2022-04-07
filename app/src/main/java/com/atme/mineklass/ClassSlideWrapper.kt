package com.atme.mineklass

import com.atme.mineklass.database.UserClassData

data class ClassSlideWrapper(
    val day: String,
    val classes: List<UserClassData>
) {
}