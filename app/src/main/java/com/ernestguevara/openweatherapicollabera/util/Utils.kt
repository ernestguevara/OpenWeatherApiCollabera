package com.ernestguevara.openweatherapicollabera.util

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.ernestguevara.openweatherapicollabera.BuildConfig
import com.ernestguevara.openweatherapicollabera.R
import timber.log.Timber
import java.time.*
import java.time.format.DateTimeFormatter

fun String?.isNotNullOrNotEmpty(): Boolean = !this.isNullOrEmpty()

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

fun getIconUrl(iconString: String?): String {
    return "${BuildConfig.IMG_URL}img/wn/$iconString.png"
}

/*
Dates
 */
fun convertUtcToGmt(timeInMillis: Long?): Long? {
    return if (timeInMillis == null) {
        null
    } else {
        val instant = Instant.ofEpochMilli(timeInMillis * 1000)
        val utcDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)

        val defaultZone = ZoneId.systemDefault()
        val defaultDateTime = utcDateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(defaultZone)

        return defaultDateTime.toInstant().toEpochMilli()

    }
}

fun getCurrentDayLong(): Long {
    val offset = ZoneOffset.systemDefault().rules.getOffset(LocalDateTime.now())
    return LocalDateTime.now().toInstant(offset).toEpochMilli()
}

fun loadIndicatorImage(currTime: Long?, sunset: Long?, glide: RequestManager, view: ImageView) {
    val drawable = if (currTime != null && sunset != null && currTime > sunset) {
        R.drawable.moon
    } else {
        R.drawable.sun
    }
    glide.load(drawable)
        .into(view)
}