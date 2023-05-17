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

fun convertLongToTimeString(timeInMillis: Long?): String {
    return if (timeInMillis != null) {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeInMillis),
            ZoneId.systemDefault()
        )
        val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
            .withLocale(Locale.getDefault())
        localDateTime.format(formatter)
    } else {
        "00:00AM"
    }
}

fun getCurrentDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.getDefault())
    return currentDate.format(formatter)
}

fun getCurrentDay(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("EEEE", Locale.getDefault())
    return currentDate.format(formatter)
}