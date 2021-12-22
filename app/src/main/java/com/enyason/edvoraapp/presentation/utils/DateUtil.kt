package com.enyason.edvoraapp.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getDateObjectFromString(stringDate: String): Date {
    return runCatching {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ROOT)
        simpleDateFormat.parse(stringDate)
    }.getOrNull() ?: Date()
}

/**
 * formats date to time formatdd/MM/yyyy
 */
fun String.toFormattedDate(): String {
    val date = getDateObjectFromString(this)
    return SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(date)
}