package it.dziubinski.workInIt

import kotlin.time.Duration

fun Duration.toHumanMinutesAndSeconds(): String {
    val seconds = this.inWholeSeconds
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60

    return if (minutes > 0) {
        "${minutes}min ${remainingSeconds}s"
    } else {
        "${seconds}s"
    }
}