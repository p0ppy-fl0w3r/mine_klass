package com.atme.mineklass.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.atme.mineklass.classData.ClassData

@Entity(tableName = "schedule_table")
data class UserClassData(
    @PrimaryKey val id:String,
    val day: String,
    val time: String,
    val class_type: String,
    val module_name: String,
    val module_title: String,
    val lecturer: String,
    val group: String,
    val block: String,
    val room: String,
)

fun List<UserClassData>.asClassData(): List<ClassData> {
    return map {
        ClassData(
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