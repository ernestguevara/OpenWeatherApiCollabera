package com.ernestguevara.openweatherapicollabera.util

import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AlignmentSpan
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

fun String?.isNotNullOrNotEmpty(): Boolean = !this.isNullOrEmpty()

fun createAlignedSpannableString(label: String, value: String): SpannableString {
    val spannableString = SpannableString("$label $value")

    spannableString.setSpan(
        AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL),
        0, label.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    spannableString.setSpan(
        AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE),
        label.length + 1, spannableString.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return spannableString
}

fun setLocationName(city: String?, country: String?): String {
    return "$city, $country"
}

fun convertLongToTimeString(timeInMillis: Long?, format: String): String {
    return if (timeInMillis != null) {
        val instant = Instant.ofEpochMilli(timeInMillis)
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern(format)

        dateTime.format(formatter)
    } else {
        format.replace(Regex("[a-zA-Z0-9]"), "-")
    }
}

fun getCurrentDayLong(): Long {
    val currentDate = LocalDate.now()
    val startOfDay = currentDate.atStartOfDay(ZoneId.systemDefault())
    return startOfDay.toInstant().toEpochMilli()
}