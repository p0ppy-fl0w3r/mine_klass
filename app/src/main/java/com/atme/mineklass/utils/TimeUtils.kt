package com.atme.mineklass.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit

fun getTime(rawTime: String): List<LocalDateTime> {

    val strippedTime = rawTime.replace(Regex("\\s"), "").split(Regex("-"))

    var startHour = strippedTime[0].substring(0, 2).toInt()
    val startMin = strippedTime[0].substring(3, 5).toInt()

    var endHour = strippedTime[1].substring(0, 2).toInt()
    val endMin = strippedTime[1].substring(3, 5).toInt()

    if (strippedTime[0].contains(Regex("[pP]"))) {
        startHour += 12
    }

    if (strippedTime[1].contains(Regex("[pP]"))) {
        endHour += 12
    }

    val startTime = LocalTime.of(startHour, startMin)
    val endTime = LocalTime.of(endHour, endMin)

    val startDateTime = LocalDateTime.of(LocalDate.now(), startTime)
    val endDateTime = LocalDateTime.of(LocalDate.now(), endTime)

    return listOf<LocalDateTime>(startDateTime, endDateTime)

}

fun formatTime(millis: Long) : String{
    return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
}