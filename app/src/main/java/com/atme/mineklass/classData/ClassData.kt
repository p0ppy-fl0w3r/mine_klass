package com.atme.mineklass.classData

import android.os.Parcelable
import com.atme.mineklass.database.UserClassData
import com.atme.mineklass.utils.getTime
import com.squareup.moshi.Json
import kotlinx.parcelize.IgnoredOnParcel

import kotlinx.parcelize.Parcelize

import java.util.*


private val ALL_DAYS =
    hashMapOf("SUN" to 1, "MON" to 2, "TUE" to 3, "WED" to 4, "THU" to 5, "FRI" to 6)

@Parcelize
data class ClassData(
    // Setting all properties to an empty string to prevent errors
    @Json(name = "Day") var day: String = "",
    @Json(name = "Time") var time: String = "",
    @Json(name = "Class Type") var class_type: String = "",
    @Json(name = "Module Code") var module_name: String = "",
    @Json(name = "Module Title ") var module_title: String = "",
    @Json(name = "Lecturer") var lecturer: String = "",
    @Json(name = "Group") var group: String = "",
    @Json(name = "Block") var block: String = "",
    @Json(name = "Room") var room: String = "",
    @Json(name = "id") var id: Int
) : Parcelable {
    @IgnoredOnParcel
    var isToday = ALL_DAYS[day] == Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

    fun toUserClassData(): UserClassData {
        return UserClassData(
            id, day, time, class_type, module_name, module_title, lecturer, group, block, room
        )
    }

    fun getValue():Float{
        // TODO write doc string
        val timeValue = getTime(time)[0].hour
        val dayValue = ALL_DAYS[day]

        return "$dayValue.$timeValue".toFloat()
    }


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
