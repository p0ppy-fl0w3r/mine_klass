package com.atme.mineklass

object Constants {
    // Time delay for splash screen
    const val DELAY_TIME: Long = 1690

    val DAYS = listOf(
        "SUN",
        "MON",
        "TUE",
        "WED",
        "THU",
        "FRI",
        "SAT"
    )

    // Edit Class bundle keys
    const val START_HOUR = "startHour"
    const val START_MIN = "startMin"
    const val END_HOUR = "endHour"
    const val END_MIN = "endMin"

    val CLASS_TYPE = listOf(
        "Workshop",
        "Lecture",
        "Discussion"
    )

    // Add class default id
    const val ID_EDIT = "Edit"

    // Read storage permission request code
    const val READ_REQUEST_CODE = 0
}