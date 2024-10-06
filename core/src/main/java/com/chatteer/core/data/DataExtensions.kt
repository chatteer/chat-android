package com.chatteer.core.data

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal object DataExtensions {

    fun nowLocalDateTime(): LocalDateTime {
        return System.currentTimeMillis().toLocalDateTime()
    }

    fun Long.toLocalDateTime(): LocalDateTime {
        return Instant.fromEpochMilliseconds(this)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun toLocalDate(): LocalDate {
        return nowLocalDateTime().date
    }
}