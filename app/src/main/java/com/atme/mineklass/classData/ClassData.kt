package com.atme.mineklass.classData

import android.os.Parcelable
import com.atme.mineklass.database.UserClassData
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*


private val ALL_DAYS =
    hashMapOf("SUN" to 0, "MON" to 1, "TUE" to 2, "WED" to 3, "THU" to 4, "FRI" to 5)

@Parcelize
data class ClassData(
    // Setting all properties to an empty string to prevent errors
    @Json(name = "Day") val day: String,
    @Json(name = "Time") val time: String,
    @Json(name = "Class Type") val class_type: String,
    @Json(name = "Module Code") val module_name: String,
    @Json(name = "Module Title ") val module_title: String,
    @Json(name = "Lecturer") val lecturer: String,
    @Json(name = "Group") val group: String,
    @Json(name = "Block") val block: String,
    @Json(name = "Room") val room: String,
    @Json(name = "id") val id: String
) : Parcelable {
    val isToday = ALL_DAYS[day] == Date().day
}

fun List<ClassData>.asUserClassData(): List<UserClassData> {
    return map {
        UserClassData(
            id = it.id,
            day = it.day,
            time = it.time,
            class_type = it.class_type,
            module_name = it.module_name,
            module_title = it.module_title,
            lecturer = it.lecturer,
            group = it.group,
            block = it.block,
            room = it.room
        )
    }
}
