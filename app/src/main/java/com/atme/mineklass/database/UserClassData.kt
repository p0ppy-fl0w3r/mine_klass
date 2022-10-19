package com.atme.mineklass.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.atme.mineklass.classData.ClassData

@Entity(tableName = "schedule_table")
data class UserClassData(
    @PrimaryKey(autoGenerate = true) var id:Int = 0,
    var day: String,
    var time: String,
    var class_type: String,
    var module_name: String,
    var module_title: String,
    var lecturer: String,
    var group: String,
    var block: String,
    var room: String,
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

fun UserClassData.asClassData(): ClassData {
    return ClassData(
        id = id,
        day = day,
        time = time,
        class_type = class_type,
        module_name = module_name,
        module_title = module_title,
        lecturer = lecturer,
        group = group,
        block = block,
        room = room
    )

}