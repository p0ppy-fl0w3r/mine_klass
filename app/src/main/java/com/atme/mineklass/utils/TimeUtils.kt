package com.atme.mineklass.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit

// TODO optimize get time by removing regex
fun getTime(rawTime: String): List<LocalDateTime> {

    val strippedTime = rawTime.replace(Regex("\\s"), "").split(Regex("-"))

    var startHour = strippedTime[0].substring(0, 2).toInt()
    val startMin = strippedTime[0].substring(3, 5).toInt()

    var endHour = strippedTime[1].substring(0, 2).toInt()
    val endMin = strippedTime[1].substring(3, 5).toInt()

    if (strippedTime[0].contains(Regex("[pP]"))) {
        if (startHour != 12) {
            startHour += 12
        }
    } else {
        if (startHour == 12) {
            startHour = 0
        }
    }

    if (strippedTime[1].contains(Regex("[pP]"))) {
        if (endHour != 12) {
            endHour += 12
        }
    } else {
        if (endHour == 12) {
            endHour = 0
        }
    }

    val startTime = LocalTime.of(startHour, startMin)
    val endTime = LocalTime.of(endHour, endMin)

    val startDateTime = LocalDateTime.of(LocalDate.now(), startTime)
    val endDateTime = LocalDateTime.of(LocalDate.now(), endTime)

    return listOf<LocalDateTime>(startDateTime, endDateTime)

}

fun getTimeString(hour: Int, min: Int): String {
    val isPm = when {
        hour > 11 -> true
        else -> false
    }

    return when (isPm) {
        true -> {
            if (hour == 12) {
                String.format("%02d:%02d %s", hour, min, "PM")
            } else {
                String.format("%02d:%02d %s", hour - 12, min, "PM")
            }
        }
        else -> {
            if (hour == 0) {
                String.format("%02d:%02d %s", 12, min, "AM")
            } else {
                String.format("%02d:%02d %s", hour, min, "AM")
            }
        }
    }
}

fun formatTime(millis: Long): String {
    return String.format(
        "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MILLISECONDS.toHours(
                millis
            )
        ),
        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
            TimeUnit.MILLISECONDS.toMinutes(
                millis
            )
        )
    )
}